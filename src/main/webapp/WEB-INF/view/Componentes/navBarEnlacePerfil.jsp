<%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 24/04/2025
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>NavBarNormal</title>
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
      <li><a href="/" class="nav-link <%= "inicio".equals(activePage) ? "active" : "" %>">Inicio</a></li>
      <li><a href="/usuarioPremium/peliculas" class="nav-link <%= "peliculas".equals(activePage) ? "active" : "" %>">Películas</a></li>
      <li><a href="/usuarioPremium/actores" class="nav-link <%= "actores".equals(activePage) ? "active" : "" %>">Actores</a></li>
    </ul>
    <div class="profile">
      <a href="/usuarioPremium/perfil">
        <img src="/img/imagenPerfil.png" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
      </a>
    </div>
  </div>
</nav>

</body>
</html>
