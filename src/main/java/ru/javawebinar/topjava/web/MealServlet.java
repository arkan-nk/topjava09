package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.annotation.PreDestroy;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    }
    @PreDestroy
    public void destroy(){
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        MealDate mealDate = (MealDate) request.getSession().getAttribute("mealDate");
        if (mealDate==null) {
            mealDate = new MealDate();
            request.getSession().setAttribute("mealDate", mealDate);
        }
        MealRestController mealRestController = appCtx.getBean(MealRestController.class);
        User user = (User) request.getSession().getAttribute("authorizedUser");
        if (action!=null &&(action.equals("create") || action.equals("update"))){
            String id = request.getParameter("id");
            Meal meal = new Meal((id == null || id.isEmpty()) ? null : Integer.valueOf(id),
                    AuthorizedUser.id(),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
        }
        forwardToMealList(mealRestController,user, request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User user = new User();
        user.setId(AuthorizedUser.id());
        user.setCaloriesPerDay(AuthorizedUser.getCaloriesPerDay());
        request.getSession().setAttribute("authorizedUser" , user);
        MealRestController mealRestController = appCtx.getBean(MealRestController.class);
        if (action == null) {
            LOG.info("getAll");
            forwardToMealList(mealRestController,user, request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            forwardToMealList(mealRestController,user, request, response);
        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(user.getId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", user.getCaloriesPerDay()) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private void forwardToMealList(MealRestController mealRestController, User user,
                          HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        MealDate mealDate = (MealDate) request.getSession().getAttribute("mealDate");
        if (mealDate==null) {
            mealDate = new MealDate();
            request.getSession().setAttribute("mealDate", mealDate);
        }
        List<MealWithExceed> mealsWE = mealRestController.getAll(user, mealDate.getStartDateTime(), mealDate.getEndDateTime());
        request.setAttribute("meals", mealsWE);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}