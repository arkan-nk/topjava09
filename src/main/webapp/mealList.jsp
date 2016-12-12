<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meal List</title>
    <style>
        .style_regular{
            background-color: white;
            color: black;
        }
        .style_exceed{
            background-color: red;
            color: white;
        }
    </style>
</head>
<body>
<jsp:useBean id="mealUtil" class="ru.javawebinar.topjava.util.MealsUtil"/>
<h2><a href="index.html">Home</a></h2>
<table width="450px;">
    <tr>
        <th>Дата</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach items="${mealUtil.getFilteredWithExceeded(meals, startTime, endTime, caloriesPerDay)}" var="mealItem" varStatus="st">
    <jsp:useBean id="mealItem" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
    <tr class="${mealItem.exceed ? 'style_exceed' : 'style_regular'}">
        <td>${mealItem.date}</td>
        <td>${mealItem.time}</td>
        <td>${mealItem.description}</td>
        <td>${mealItem.calories}</td>
    </tr>
    </c:forEach>

</table>
</body>
</html>
