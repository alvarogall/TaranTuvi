<%--
  Created by IntelliJ IDEA.
  User: table
  Date: 03/05/2025
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NavBarAdmin</title>
    <link rel="stylesheet" type="text/css" href="/css/Componentes/navBarNormal.css">
</head>

<%
    String activePage = request.getParameter("activePage");
%>

<body>
    <nav class="navbar">
        <div class="logo">
            <span class="logo-text">TaranTuvi</span>
            <span class="logo-icon">🎬</span>
        </div>
        <div class="nav-right">
            <ul class="nav-links">
                <li><a href="/administrador" class="nav-link <%= "inicio".equals(activePage) ? "active" : "" %>">Inicio</a></li>
                <li><a href="/administrador/generos" class="nav-link <%= "generos".equals(activePage) ? "active" : "" %>">Géneros</a></li>
                <li><a href="/administrador/productoras" class="nav-link <%= "productoras".equals(activePage) ? "active" : "" %>">Productoras</a></li>
            </ul>
            <div class="profile">
                <img src="/img/imagenPerfil.png" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
            </div>
        </div>
    </nav>
</body>
</html>
