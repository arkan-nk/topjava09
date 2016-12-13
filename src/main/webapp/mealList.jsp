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
            background-color: greenyellow;
            color: darkblue;
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
<table style="border: 1px; width: 450px;">
    <tr>
        <th><c:out value="Дата"/></th>
        <th><c:out value="Время"/></th>
        <th><c:out value="Описание"/></th>
        <th><c:out value="Калории"/></th>
    </tr>
    <c:forEach items="${mealUtil.getWithExceeded(meals, calories)}" var="mealItem" varStatus="st">
    <jsp:useBean id="mealItem" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
    <tr class="${mealItem.exceed ? 'style_exceed' : 'style_regular'}">
        <td><c:out value="${mealItem.date}"/></td>
        <td><c:out value="${mealItem.time}"/></td>
        <td><c:out value="${mealItem.description}"/></td>
        <td><c:out value="${mealItem.calories}"/></td>
    </tr>
    </c:forEach>

</table>
</body>
</html>
