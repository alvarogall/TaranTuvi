<%@ page import="es.uma.taw.tarantuvi.entity.ValoracionEntity" %>
<%@ page import="java.util.List" %><%--
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
    <link rel="stylesheet" type="text/css" href="/css/inicioAnalista.css">
</head>
<body>
<nav class="navbar">
    <div class="logo">
        <span class="logo-text">TaranTuvi</span>
        <span class="logo-icon">🎬</span>
    </div>
    <div class="nav-right">
        <ul class="nav-links">
            <li><a href="/analista">Inicio</a></li>
            <li><a href="/analista/ranking" class="active">Películas</a></li>
            <li><a href="/analista/actores">Actores</a></li>
        </ul>
        <div class="profile">
            <img src="/img/imagenPerfilAnalista.jpg" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
        </div>
    </div>
</nav>
<h2>Ranking de Peliculas</h2>
<table border = "1">
    <tr>
        <th>#</th>
        <th>Título</th>
        <th>Nota Media</th>
    </tr>
    <%
        List<Object[]> ranking = (List<Object[]>) request.getAttribute("ranking");
        int index = 1;
        for (Object[] fila : ranking) {
    %>
    <tr>
        <td><%= index++ %></td>
        <td><%= fila[0] %></td>
        <td><%= fila[1] %></td>
    </tr>
    <%
        }
    %>

</table>

</body>
</html>
