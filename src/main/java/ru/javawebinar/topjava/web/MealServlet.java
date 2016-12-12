package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by arkan on 07.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to mealList");
        request.setCharacterEncoding("UTF-8");
        List<Meal> meals = MealsUtil.createlistMeal();
        LocalTime startTime = LocalTime.of(7,0);
        LocalTime endTime = LocalTime.of(10,0);
        final int calories = 1000;
        HttpSession session = request.getSession();
        session.setAttribute("meals", meals);
        session.setAttribute("startTime", startTime);
        session.setAttribute("endTime", endTime);
        session.setAttribute("calories", calories);
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
}
