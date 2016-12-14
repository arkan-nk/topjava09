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
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by arkan on 07.12.2016.
 */
public class MealServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals(Action.DELETE.getTxt())) {
            this.deleteMeal(request, response);
        }else if (action.equals(Action.EDIT.getTxt())){

        }else if (action.equals(Action.ADD.getTxt())) {

        }else {
            this.showAll(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    private void deleteMeal(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("delete to meal");
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
        repository = new InMemoryRepositoryMeal();
        List<Meal> meals = MealsUtil.createlistMeal();
        meals.forEach(meal->repository.putAndGetNew(meal));
    }
    private static final Logger LOG = getLogger(MealServlet.class);
    private RepositoryMealIf repository = null;
}
