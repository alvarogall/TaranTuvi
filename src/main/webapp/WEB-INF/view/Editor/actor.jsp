<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.*" %><%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 20/04/2025
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
    List<PeliculaEntity> peliculas = (List<PeliculaEntity>) request.getAttribute("peliculas");
    List<ActuacionEntity> actuaciones = (List<ActuacionEntity>) request.getAttribute("actuaciones");
    List<GeneroPeliculaEntity> generos = (List<GeneroPeliculaEntity>) request.getAttribute("generos");
    if(persona.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nuevo/a") %> Actor/Actriz</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
<jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

<h1><%= (esEditar? "Editar" : "Nuevo/a") %> Actor/Actriz</h1>

<form method="post" action="/actores/confirmarCambios">
    <input type="hidden" name="id" value="<%= persona.getId() != null ? persona.getId() : -1 %>"/>
    <!-- Contenedor flex (imagen + campos) -->
    <div class="movie-header-container">
        <!-- Imagen -->
        <div class="movie-poster">
            <img src="<%= persona.getUrlfoto() != null ? persona.getUrlfoto() : "https://i.postimg.cc/x1GgSpbn/add-circle-svgrepo-com.png" %>" alt="Foto">
        </div>

        <!-- Campos a la derecha -->
        <div class="movie-info-fields">
            <!-- URL -->
            <div class="movie-field-row">
                <label>URL de la foto:</label>
                <input type="text" name="url" value="<%= persona.getUrlfoto()!=null?persona.getUrlfoto():""%>">
            </div>

            <!-- Nombre -->
            <div class="movie-field-row">
                <label>Nombre:</label>
                <input type="text" name="nombre" value="<%= persona.getNombre()!=null?persona.getNombre():""%>">
            </div>

            <!-- Género -->
            <div class="movie-field-row">
                <label>Género:</label>
                <input type="text" name="genero" value="<%= persona.getGeneropersonaid().getGeneropersonanombre()!=null?persona.getGeneropersonaid().getGeneropersonanombre():""%>">
            </div>

            <!-- Nacionalidad -->
            <div class="movie-field-row">
                <label>Nacionalidad:</label>
                <input type="text" name="nacionalidad" value="<%= persona.getNacionalidadid().getNacionalidadnombre()!=null?persona.getNacionalidadid().getNacionalidadnombre():""%>">
            </div>
        </div>
    </div>

    <!-- Contenedor de 3 columnas -->
    <div class="columns-container">
        <!-- Columna 1 (Peliculas, Personajes) -->
        <div class="column">

            <label for="peliculas" class="select-label">Películas:</label>
            <select id="peliculas" name="peliculas" multiple class="multi-select">
                <% for (PeliculaEntity pelicula : peliculas) {%>
                <option value="<%= pelicula.getId() %>"
                        <% if (esEditar) {
                            for(ActuacionEntity act : pelicula.getActuacionList()){
                                if(act.getPersonaid().getId() == persona.getId()){
                        %> selected <% } } }%>>
                    <%= pelicula.getTitulooriginal() %>
                </option>
                <% } %>
            </select>

            <label for="personajes" class="select-label">Personajes:</label>
            <select id="personajes" name="personajes" multiple class="multi-select">
                <% for (ActuacionEntity act : actuaciones) { %>
                <option value="<%= act.getId() %>"
                        <% if(esEditar) {
                            if (act.getPersonaid().getId() == persona.getId()) {
                        %> selected <% } } %>>
                    <%= act.getPersonaje() %>
                </option>
                <% } %>
            </select>
        </div>

        <!-- Columna 2 (Géneros) -->
        <div class="column">

            <label for="generos" class="select-label">Géneros:</label>
            <select id="generos" name="generos" multiple class="multi-select">
                <% for (GeneroPeliculaEntity genero : generos) { %>
                <option value="<%= genero.getId() %>"
                        <% if(esEditar) {
                            for(PeliculaEntity pelicula : genero.getPeliculaList()) {
                                for (ActuacionEntity act : pelicula.getActuacionList()) {
                                    if (act.getPersonaid().getId() == persona.getId()) {
                        %> selected <% } } } }%>>
                    <%= genero.getGeneronombre() %>
                </option>
                <% } %>
            </select>
        </div>
    </div>

    <!-- Botón de enviar -->
    <div class="submit-btn-container">
        <input type="submit" class="submit-btn" value="<%= esEditar ? "Confirmar Cambios" : "Añadir actor/actriz"%>">
    </div>

    <!-- Botón de cancelar -->
    <jsp:include page="../Componentes/botonCancelar.jsp">
        <jsp:param name="activePage" value="actor"/>
    </jsp:include>

</form>

</body>
</html>