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
  <link rel="stylesheet" type="text/css" href="/css/Analista/inicioAnalista.css">
</head>
<body>
<div class="navbar-container">
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
        <li><a href="/analista/" class="<%= "inicio".equals(paginaActual) ? "active" : "" %>">Inicio</a></li>
        <li><a href="/analista/peliculas" class="<%= "peliculas".equals(paginaActual) ? "active" : "" %>">Películas</a></li>
        <li><a href="/analista/actores" class="<%= "actores".equals(paginaActual) ? "active" : "" %>">Actores</a></li>
        <li><a href="/analista/productoras" class="<%="productoras".equals(paginaActual) ? "active" : ""%>">Productoras</a></li>
        <li><a href="/analista/valoraciones" class="<%="valoraciones".equals(paginaActual) ? "active" : ""%>">Valoraciones</a></li>
      </ul>
      <div class="profile">
        <img src="/img/imagenPerfilAnalista.jpg" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
      </div>

      <!-- Tres puntos -->
      <div class="menu-toggle" onclick="toggleDropdown(event)">⋮</div>

      <!-- Menú oculto por defecto -->
      <div id="profile-dropdown" class="dropdown-menu">
        <a href="#" class="dropdown-item" onclick="confirmLogout(event)">Cerrar sesión</a>
      </div>

    </div>
  </nav>
</div>

<script>
  function toggleDropdown(event) {
    event.stopPropagation();
    document
            .getElementById('profile-dropdown')
            .classList.toggle('show');
  }

  function confirmLogout(event) {
    event.preventDefault();
    if (confirm("¿Estás seguro de que quieres cerrar sesión?")) {
      window.location.href = "/logout";
    }
  }

  // Cerrar si clicas fuera
  document.addEventListener('click', function(e) {
    const dropdown = document.getElementById('profile-dropdown');
    if (!dropdown.contains(e.target) &&
            !e.target.matches('.menu-toggle')) {
      dropdown.classList.remove('show');
    }
  });
</script>
</body>
</html>
