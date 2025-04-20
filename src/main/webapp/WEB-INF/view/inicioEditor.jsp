<%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 08/04/2025
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Inicio</title>
  <link rel="stylesheet" type="text/css" href="/css/inicioEditor.css">
</head>
<body>
<body>

<nav class="navbar">
  <div class="logo">
    <span class="logo-text">TaranTuvi</span>
    <span class="logo-icon">🎬</span>
  </div>
  <div class="nav-right">
    <ul class="nav-links">
      <li><a href="/" class="active">Inicio</a></li>
      <li><a href="/peliculas">Películas</a></li>
      <li><a href="/actores">Actores</a></li>
    </ul>
    <div class="profile">
      <img src="/img/imagenPerfil.png" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
    </div>
  </div>
</nav>

<div class="welcome-container">
  <h1>Bienvenido, Editor 👋</h1>
  <p>Desde esta plataforma puedes gestionar el contenido audiovisual de TaranTuvi.</p>
  <p>Como editor, tienes acceso a las siguientes funciones:</p>

  <ul class="features-list">
    <li>➕ Añadir nuevas películas y actores.</li>
    <li>✏️ Editar información existente (como títulos, géneros, personajes...).</li>
    <li>🗑️ Eliminar registros obsoletos o incorrectos.</li>
    <li>🔍 Buscar por nombre o filtrar alfabéticamente.</li>
  </ul>
</div>

</body>
</html>

