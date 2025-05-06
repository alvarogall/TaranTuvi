<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Alejandro Cueto
  Date: 20/04/2025
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Ranking Películas</title>
</head>
<body>

<jsp:include page ="analistaCabecera.jsp"/>

<form:form method="get" modelAttribute="filtro">
    <table border = 1>
        <tr>
            <th>Pelicula</th>
            <th>Nota</th>
        </tr>
    </table>
</form:form>

</body>
</html>
