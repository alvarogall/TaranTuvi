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
  List<ProductoraEntity> productoras = (List<ProductoraEntity>) request.getAttribute(("productores"));
  List<IdiomaHabladoEntity> idiomas = (List<IdiomaHabladoEntity>) request.getAttribute("idiomas");
  List<GeneroPeliculaEntity> generos = (List<GeneroPeliculaEntity>) request.getAttribute("generos");
%>

<head>
    <title><%= (esEditar? "Edición de la" : "Nuevo") %> Película</title>
    <link rel="stylesheet" type="text/css" href="/css/editarPelicula.css">
</head>
<body>
<jsp:include page="navBarEditarAñadir.jsp" />

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
      <label for="crew">Crew:</label>
      <textarea id="crew" name="crew"><% if(esEditar) { for (TrabajoEntity trabajo : pelicula.getTrabajoList()) { out.println(trabajo.getPersonaid().getNombre() + " - " + trabajo.getTrabajonombre()); } } %></textarea>

      <label for="cast">Cast:</label>
      <textarea id="cast" name="cast"><% if(esEditar) { for (ActuacionEntity actuacion : pelicula.getActuacionList()) { out.println(actuacion.getPersonaid().getNombre() + " - " + actuacion.getPersonaje()); } } %></textarea>

      <label for="productoras">Productoras:</label>
      <textarea id="productoras" name="productoras"><% if(esEditar) { for(ProductoraEntity productora : pelicula.getProductoraList()) { out.println(productora.getProductoranombre()); } } %></textarea>

      <label>Página Web:</label>
      <input type="text" name="paginaweb" value="<%= pelicula.getPaginaweb()!=null?pelicula.getPaginaweb():""%>">

      <label>Presupuesto (€):</label>
      <input type="text" name="presupuesto" value="<%= pelicula.getPresupuesto()!=null?pelicula.getPresupuesto():""%>">

      <label>Recaudación (€):</label>
      <input type="text" name="recaudacion" value="<%= pelicula.getRecaudacion()!=null?pelicula.getRecaudacion():""%>">
    </div>

    <!-- Columna 2 (Países, Idiomas, Géneros) -->
    <div class="column">
      <label for="paisesRodaje">Países de rodaje:</label>
      <textarea id="paisesRodaje" name="paisesRodaje"><% if(esEditar) { for(PaisRodajeEntity pais : pelicula.getPaisRodajeList()) { out.println(pais.getPaisrodajenombre()); } } %></textarea>

      <label for="idiomas">Idiomas:</label>
      <textarea id="idiomas" name="idiomas"><% if(esEditar) { for(IdiomaHabladoEntity idioma : pelicula.getIdiomaHabladoList()) { out.println(idioma.getIdiomahabladonombre()); } } %></textarea>

      <label for="generos">Géneros:</label>
      <textarea id="generos" name="generos"><% if(esEditar) { for(GeneroPeliculaEntity genero : pelicula.getGeneroPeliculaList()) { out.println(genero.getGeneronombre()); } } %></textarea>

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
</form>
</body>
</html>
