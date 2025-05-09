<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ideapad
  Date: 8/5/25
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/Login/login.css">
</head>
<%
    String error = (String) request.getAttribute("error");
%>
<body>
    <div class="login-box">
        <h1>Hola, bienvenido</h1>
        <form:form method="post" modelAttribute="usuario" action="/autenticar">
            <form:input path="usuario" placeholder="Usuario" />
            <form:password path="password" placeholder="Contraseña" />
            <form:button type="submit">Entrar</form:button>
        </form:form>
        <p class="error-message">
            <%= (error != null) ? error : "" %>
        </p>
    </div>

</body>
</html>
