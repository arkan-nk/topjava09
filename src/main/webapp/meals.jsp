<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
    <meta charset="UTF-8"/>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>

    <form method="post" action="meals">
    <jsp:useBean id="mealDate" type="ru.javawebinar.topjava.web.MealDate" scope="session" />
    <table style="border:none;">
        <tr>
            <td/>
            <td>
                <c:out value="Дата начала"/>
            </td>
            <td>
                <c:out value="Время начала"/>
            </td>
            <td>
                <c:out value="Дата конца"/>
            </td>
            <td>
                <c:out value="Время конца"/>
            </td>
        </tr>
        <tr>
            <td>
                <c:out value="Дата/время"/>
            </td>
            <td>
                <input type="date" value="${mealDate.startDate}" name="date1">
            </td>
            <td>
                <input type="time" value="${mealDate.startTime}" name="time1">
            </td>
            <td>
                <input type="date" value="${mealDate.endDate}" name="date2">
            </td>
            <td>
                <input type="time" value="${mealDate.endTime}" name="time2">
            </td>
        </tr>
    </table>
    <input type="submit" value="Применить фильтр"/>
    </form>
    <hr>
    <a href="meals?action=create">Add Meal</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

</section>
</body>
</html>