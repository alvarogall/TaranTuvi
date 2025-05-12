<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Peliculas</title>
    <link rel="stylesheet" type="text/css" href="/css/componentes/navBar.css">
    <link rel="stylesheet" type="text/css" href="/css/usuario/peliculas.css">
</head>
<%
    List<PeliculaEntity> peliculas = (List<PeliculaEntity>) request.getAttribute("peliculas");
%>
<body>
<jsp:include page="../componentes/navBar.jsp"/>
<div class="grid-container">
    <%
        if (peliculas != null) {
            for (PeliculaEntity pelicula : peliculas) {
    %>
    <div class="movie-card">
        <img src="<%= pelicula.getUrlcaratula() %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>">
        <div class="movie-info">
            <div class="movie-title">
                <a href="/pelicula/?id=<%= pelicula.getId() %>" class="movie-title-link">
                    <%= pelicula.getTitulooriginal() %>
                </a>
            </div>
            <div class="movie-note">
                <span class="star">&#9733;</span>
                <%= pelicula.getNota() != null ? pelicula.getNota() : "-" %>
            </div>
            <div class="movie-meta">
                <%= pelicula.getFechaestreno().getYear() %> &nbsp;&bull;&nbsp; <%= pelicula.getDuracion() %> min
            </div>
            <div class="movie-genres">
                <% if (pelicula.getGeneroPeliculaList() != null) {
                    for (GeneroPeliculaEntity genero : pelicula.getGeneroPeliculaList()) { %>
                <span class="badge"><%= genero.getGeneronombre() %></span>
                <%  }
                } %>
            </div>
        </div>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
