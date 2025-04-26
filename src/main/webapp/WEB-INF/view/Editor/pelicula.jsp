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
  PeliculaEntity pelicula = (PeliculaEntity) request.getAttribute("pelicula");
  if(pelicula.getId() == null) esEditar = false;
  List<ActuacionEntity> actuaciones = (List<ActuacionEntity>) request.getAttribute("actuaciones");
  List<PersonaEntity> personas = (List<PersonaEntity>) request.getAttribute("personas");
  List<ProductoraEntity> productoras = (List<ProductoraEntity>) request.getAttribute(("productoras"));
  List<IdiomaHabladoEntity> idiomas = (List<IdiomaHabladoEntity>) request.getAttribute("idiomas");
  List<GeneroPeliculaEntity> generos = (List<GeneroPeliculaEntity>) request.getAttribute("generos");
  List<PaisRodajeEntity> paisesRodaje = (List<PaisRodajeEntity>) request.getAttribute("paisesRodaje");
  List<DepartamentoEntity> departamentos = (List<DepartamentoEntity>) request.getAttribute("departamentos");
  List<TrabajoEntity> trabajos = (List<TrabajoEntity>) request.getAttribute("trabajos");
%>

<head>
  <title><%= (esEditar? "Edición de la" : "Nueva") %> Película</title>
  <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
<jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

<h1><%= (esEditar? "Editar" : "Nueva") %> Película</h1>

<form method="post" action="/peliculas/confirmarCambios">
  <input type="hidden" name="id" value="<%= pelicula.getId() != null ? pelicula.getId() : -1 %>"/>
  <!-- Contenedor flex (imagen + campos) -->
  <div class="movie-header-container">
    <!-- Imagen -->
    <div class="movie-poster">
      <img src="<%= pelicula.getUrlcaratula() != null ? pelicula.getUrlcaratula() : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Portada">
    </div>

    <!-- Campos a la derecha -->
    <div class="movie-info-fields">
      <!-- URL -->
      <div class="movie-field-row">
        <label>URL de la portada:</label>
        <input type="text" name="url" value="<%= pelicula.getUrlcaratula()!=null?pelicula.getUrlcaratula():""%>">
      </div>

      <!-- Nombre -->
      <div class="movie-field-row">
        <label>Película:</label>
        <input type="text" name="nombre" value="<%= pelicula.getTitulooriginal()!=null?pelicula.getTitulooriginal():""%>">
      </div>

      <!-- Fecha -->
      <div class="movie-field-row">
        <label>Fecha de Estreno:</label>
        <input type="text" name="fecha" value="<%= pelicula.getFechaestreno()!=null?pelicula.getFechaestreno():""%>">
      </div>

      <!-- Duración -->
      <div class="movie-field-row">
        <label>Duración (min):</label>
        <input type="text" name="duracion" value="<%= pelicula.getDuracion()!=null?pelicula.getDuracion():""%>">
      </div>

      <!-- Sinopsis -->
      <div class="movie-field-row">
        <label>Sinópsis:</label>
        <textarea name="descripcion"><%= pelicula.getDescripcion()!=null?pelicula.getDescripcion():""%></textarea>
      </div>
    </div>
  </div>

  <!-- Contenedor de 3 columnas -->
  <div class="columns-container">
    <!-- Columna 1 (Crew, Cast, Productoras) -->
    <div class="column">

      <label for="cast" class="select-label">Cast (Actor/Actriz - Personaje):</label>
      <select id="cast" name="cast" multiple class="multi-select">
        <% for (ActuacionEntity actuacion : actuaciones) {
          PersonaEntity actor = actuacion.getPersonaid();
          String personaje = actuacion.getPersonaje();
        %>
        <option value="<%= actuacion.getId() %>"
                <% if (esEditar) {
                  for (ActuacionEntity act : pelicula.getActuacionList()) {
                    if (act.getPersonaid().getId().equals(actor.getId()) && act.getPersonaje().equals(personaje)) {
                %> selected <% } } } %>>
          <%= actor.getNombre() %> - <%= personaje %>
        </option>
        <% } %>
      </select>

      <label for="crew" class="select-label">Crew (Persona - Trabajo):</label>
      <select id="crew" name="crew" multiple class="multi-select">
        <% for (TrabajoEntity trabajo : trabajos) { %>
        <option value="<%= trabajo.getId() %>"
                <% if(esEditar) {
                  List<TrabajoEntity> trabajosPelicula = pelicula.getTrabajoList();
                  for (TrabajoEntity t : trabajosPelicula) {
                    if(trabajo.getDepartamentoid().getId() == t.getDepartamentoid().getId() && trabajo.getPersonaid().getId() == t.getPersonaid().getId()) {
                %>selected<% } } } %>>
          <%= trabajo.getPersonaid().getNombre() %> - <%= trabajo.getDepartamentoid().getDepartamentonombre() %>
        </option>
        <% } %>
      </select>

      <label for="productoras" class="select-label">Productoras:</label>
      <select id="productoras" name="productoras" multiple class="multi-select">
        <% for (ProductoraEntity productora : productoras) { %>
        <option value="<%= productora.getId() %>"
                <% if(esEditar) {
                  for(ProductoraEntity peliculaProd : pelicula.getProductoraList()) {
                    if(peliculaProd.getId() == productora.getId()) {
                %> selected <% } } } %>>
          <%= productora.getProductoranombre() %>
        </option>
        <% } %>
      </select>

      <label>Página Web:</label>
      <input type="text" name="paginaweb" value="<%= pelicula.getPaginaweb()!=null?pelicula.getPaginaweb():""%>">

      <label>Presupuesto (€):</label>
      <input type="text" name="presupuesto" value="<%= pelicula.getPresupuesto()!=null?pelicula.getPresupuesto():""%>">

      <label>Recaudación (€):</label>
      <input type="text" name="recaudacion" value="<%= pelicula.getRecaudacion()!=null?pelicula.getRecaudacion():""%>">
    </div>

    <!-- Columna 2 (Países, Idiomas, Géneros) -->
    <div class="column">
      <label for="paisesRodaje" class="select-label">Países de rodaje:</label>
      <select id="paisesRodaje" name="paisesRodaje" multiple class="multi-select">
        <% for (PaisRodajeEntity pais : paisesRodaje) { %>
        <option value="<%= pais.getId() %>"
                <% if(esEditar) {
                  for(PaisRodajeEntity peliculaPais : pelicula.getPaisRodajeList()) {
                    if(peliculaPais.getId() == pais.getId()) {
                %> selected <% } } } %>
        >
          <%= pais.getPaisrodajenombre() %>
        </option>
        <% } %>
      </select>

      <label for="idiomas" class="select-label">Idiomas:</label>
      <select id="idiomas" name="idiomas" multiple class="multi-select">
        <% for (IdiomaHabladoEntity idioma : idiomas) { %>
        <option value="<%= idioma.getId() %>"
                <% if(esEditar) {
                  for(IdiomaHabladoEntity peliculaIdioma : pelicula.getIdiomaHabladoList()) {
                    if(peliculaIdioma.getId() == idioma.getId()) {
                %> selected <% } } } %>
        >
          <%= idioma.getIdiomahabladonombre() %>
        </option>
        <% } %>
      </select>

      <label for="generos" class="select-label">Géneros:</label>
      <select id="generos" name="generos" multiple class="multi-select">
        <% for (GeneroPeliculaEntity genero : generos) { %>
        <option value="<%= genero.getId() %>"
                <% if(esEditar) {
                  for(GeneroPeliculaEntity peliculaGenero : pelicula.getGeneroPeliculaList()) {
                    if(peliculaGenero.getId() == genero.getId()) {
                %> selected <% } } } %>
        >
          <%= genero.getGeneronombre() %>
        </option>
        <% } %>
      </select>

      <label for="eslogan" style="display: inline-block; vertical-align: top; margin-right: 5px;">Eslogan:</label>
      <textarea id="eslogan" name="eslogan" rows="5" cols="50" style="display: inline-block;"><%out.println(pelicula.getEslogan()!=null?pelicula.getEslogan():"");%></textarea>

      <label>Estado:</label>
      <input type="text" name="estado" value="<%= pelicula.getEstado()!=null?pelicula.getEstado():""%>">
    </div>
  </div>

  <!-- Botón de enviar -->
  <div class="submit-btn-container">
    <input type="submit" class="submit-btn" value="<%= esEditar ? "Confirmar Cambios" : "Añadir película"%>">
  </div>
  <jsp:include page="../Componentes/botonCancelar.jsp" />
</form>
</body>
</html>