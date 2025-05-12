<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PersonaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PalabraClaveEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Película</title>
    <link rel="stylesheet" type="text/css" href="/css/componentes/navBar.css">
    <link rel="stylesheet" type="text/css" href="/css/usuario/pelicula.css">
</head>
<%
    PeliculaEntity pelicula = (PeliculaEntity) request.getAttribute("pelicula");
%>
<body>
<jsp:include page="../componentes/navBar.jsp"/>
<% if (pelicula != null) { %>
<div class="detalle-container">
    <div class="detalle-card">
        <div class="detalle-caratula">
            <img src="<%= pelicula.getUrlcaratula() %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>">
        </div>
        <div class="detalle-info">
            <h1 class="detalle-titulo"><%= pelicula.getTitulooriginal() %></h1>
            <div class="detalle-eslogan"><%= pelicula.getEslogan() != null ? pelicula.getEslogan() : "" %></div>
            <div class="detalle-meta">
                <span><%= pelicula.getFechaestreno() != null ? pelicula.getFechaestreno().toString() : "" %></span>
                <span>&bull;</span>
                <span><%= pelicula.getDuracion() != null ? pelicula.getDuracion() + " min" : "" %></span>
                <span>&bull;</span>
                <span><%= pelicula.getEstado() != null ? pelicula.getEstado() : "" %></span>
            </div>
            <div class="detalle-badges">
                <% if (pelicula.getGeneroPeliculaList() != null) {
                    for (GeneroPeliculaEntity generoPelicula : pelicula.getGeneroPeliculaList()) { %>
                <span class="badge"><%= generoPelicula.getGeneronombre() %></span>
                <% }} %>
            </div>
            <div class="detalle-descripcion"><%= pelicula.getDescripcion() != null ? pelicula.getDescripcion() : "" %></div>
            <div class="detalle-extra">
                <div><strong>Idioma original:</strong> <%= pelicula.getIdiomaoriginalhabladoid() != null ? pelicula.getIdiomaoriginalhabladoid().getIdiomahabladonombre() : "" %></div>
                <div><strong>Idiomas hablados:</strong>
                    <%= (pelicula.getIdiomaHabladoList() != null) ? pelicula.toStringIdiomaHablado() : "" %>
                </div>
                <div><strong>Países de rodaje:</strong>
                    <%= (pelicula.getPaisRodajeList() != null) ? pelicula.toStringPaisRodaje() : "" %>
                </div>
                <div><strong>Productoras:</strong>
                    <%= (pelicula.getProductoraList() != null) ? pelicula.toStringProductora() : "" %>
                </div>
                <div><strong>Palabras clave:</strong>
                    <% if (pelicula.getPalabraClaveList() != null) {
                        for (PalabraClaveEntity palabraClave : pelicula.getPalabraClaveList()) { %>
                    <span class="badge badge-keyword"><%= palabraClave.getPalabraclavenombre() %></span>
                    <% }} %>
                </div>
                <div><strong>Presupuesto:</strong> <%= pelicula.getPresupuesto() != null ? pelicula.getPresupuesto() + " €" : "" %></div>
                <div><strong>Recaudación:</strong> <%= pelicula.getRecaudacion() != null ? pelicula.getRecaudacion() + " €" : "" %></div>
                <div><strong>Popularidad:</strong> <%= pelicula.getPopularidad() != null ? pelicula.getPopularidad() : "" %></div>
                <div><strong>Nota:</strong> <%= pelicula.getNota() != null ? pelicula.getNota() + " / 10" : "" %> (<%= pelicula.getVotos() != null ? pelicula.getVotos() : "0" %> votos)</div>
                <div><strong>Página web:</strong>
                    <% if (pelicula.getPaginaweb() != null && !pelicula.getPaginaweb().isEmpty()) { %>
                    <a href="<%= pelicula.getPaginaweb() %>"><%= pelicula.getPaginaweb() %></a>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Reparto de la película --%>
<% if (pelicula.getActuacionList() != null && !pelicula.getActuacionList().isEmpty()) { %>
<div class="pelicula-actores-container">
    <h2 class="pelicula-actores-titulo">Reparto</h2>
    <table class="pelicula-actores-tabla">
        <thead>
        <tr>
            <th>Actor</th>
            <th>Personaje</th>
        </tr>
        </thead>
        <tbody>
        <% for (ActuacionEntity actuacion : pelicula.getActuacionList()) {
            PersonaEntity actor = actuacion.getPersonaid();
        %>
        <tr>
            <td>
                <div class="actor-miniatura">
                    <img src="<%= actor.getUrlfoto() != null ? actor.getUrlfoto() : "" %>" alt="Foto de <%= actor.getNombre() %>">
                    <span><%= actor.getNombre() %></span>
                </div>
            </td>
            <td><%= actuacion.getPersonaje() != null ? actuacion.getPersonaje() : "" %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<% } %>
<% } %>
</body>
</html>
