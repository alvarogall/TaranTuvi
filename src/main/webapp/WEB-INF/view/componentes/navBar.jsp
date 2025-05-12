<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<nav class="navbar">
    <div class="navbar-left">
        <span class="navbar-brand">TaranTuvi</span>
        <a href="/inicio" class="navbar-link">Inicio</a>
        <a href="/pelicula/listar" class="navbar-link">Películas</a>
        <a href="/actor/listar" class="navbar-link">Actores</a>
    </div>
    <div class="navbar-right">
        <a href="/perfil" class="user-badge" title="Perfil">
            <span class="user-role">
                <%= usuario.getRol() %>
            </span>
        </a>
    </div>
</nav>