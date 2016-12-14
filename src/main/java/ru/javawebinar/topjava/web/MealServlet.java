package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryRepositoryMeal;
import ru.javawebinar.topjava.repository.RepositoryMealIf;
import ru.javawebinar.topjava.util.Action;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by arkan on 07.12.2016.
 */
public class MealServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (Action.DELETE.getTxt().equals(action)) {
            this.deleteMeal(request, response);
        }else if (Action.EDIT.getTxt().equals(action)){
            editMeal(request,response);
        }else if (Action.ADD.getTxt().equals(action)) {
            editMeal(request,response);
        }else {
            this.showAll(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
    private void editMeal(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("id");
        Integer id = Integer.parseInt(param);
        String message = id<0 ? "Add new meal" : "Edit meal";
        LOG.debug(message);
        Meal meal = repository.get(id);
        if (meal==null){
            meal = new Meal(null, LocalDateTime.now(), "", 0);
        }
        LOG.debug("forward to editMeal");
        HttpSession session = request.getSession();
        session.setAttribute("mealToEdit", meal);
        request.getRequestDispatcher("editMeal.jsp").forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("delete to meal");
        String param = request.getParameter("id");
        Integer id = Integer.parseInt(param);
        Meal dropped = repository.removeAndGetOld(id);
        if (dropped!=null && dropped.getId().equals(id)){
            LOG.debug("refresh mealList");
            HttpSession session = request.getSession();
            List<Meal> meals = (List<Meal>) session.getAttribute("meals");
            meals.remove(dropped);
            session.setAttribute("meals", meals);
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        }
    }
    private void showAll(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to mealList");
        final int calories = 2000;
        List<Meal> meals = repository.getMeals().entrySet().stream()
            .map(entry->entry.getValue())
            .sorted((meal1, meal2)-> meal1.getDateTime().compareTo(meal2.getDateTime()))
            .collect(Collectors.toList());
        HttpSession session = request.getSession();
        session.setAttribute("meals", meals);
        session.setAttribute("calories", calories);
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }
    public void init(){
        LOG.debug("create InMemoryRepository");
        repository = new InMemoryRepositoryMeal();
        List<Meal> meals = MealsUtil.createlistMeal();
        meals.forEach(meal->repository.putAndGetNew(meal));
    }
    private static final Logger LOG = getLogger(MealServlet.class);
    private RepositoryMealIf repository = null;
}
