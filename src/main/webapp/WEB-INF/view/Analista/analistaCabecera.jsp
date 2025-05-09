<%--
  Created by IntelliJ IDEA.
  User: ALEJANDRO
  Date: 06/05/2025
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cabecera</title>
    <link rel="stylesheet" type="text/css" href="/css/inicioAnalista.css">
</head>
<body>
<%
  String paginaActual = (String) request.getAttribute("paginaActual");
%>
<nav class="navbar">
  <div class="logo">
    <span class="logo-text">TaranTuvi</span>
    <span class="logo-icon">🎬</span>
  </div>
  <div class="nav-right">
    <ul class="nav-links">
      <li><a href="/analista" class="<%= "inicio".equals(paginaActual) ? "active" : "" %>">Inicio</a></li>
      <li><a href="/analista/ranking" class="<%= "peliculas".equals(paginaActual) ? "active" : "" %>">Películas</a></li>
      <li><a href="/analista/actores" class="<%= "actores".equals(paginaActual) ? "active" : "" %>">Actores</a></li>
    </ul>
    <div class="profile">
      <img src="/img/imagenPerfilAnalista.jpg" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
    </div>
  </div>
</nav>

</body>
</html>
