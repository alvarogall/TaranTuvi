<%--
  Created by IntelliJ IDEA.
  User: Alejandro Cueto
  Date: 20/04/2025
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analista</title>
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
            <li><a href="/analista" class="active">Inicio</a></li>
            <li><a href="/analista/ranking">Películas</a></li>
            <li><a href="/analista/actores">Actores</a></li>
        </ul>
        <div class="profile">
            <img src="/img/imagenPerfilAnalista.jpg" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
        </div>
    </div>
</nav>

<div class="welcome-container">
    <h1>Bienvenido, Analista 👋</h1>
    <p>Desde esta plataforma puedes gestionar los datos de TaranTuvi.</p>
    <p>Como analista, tienes acceso a las siguientes funciones:</p>

    <ul class="features-list">
        <li>🎥 Acceder a un ranking de películas.</li>
        <li>🧑‍🤝‍🧑 Analizar los datos de los actores.</li>
        <li>🎬 Comparar la producción de las películas.</li>
        <li>🌐 Visionar las valoraciones de los usuarios.</li>
    </ul>
</div>



</body>
</html>
