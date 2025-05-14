<%--
  User: jesus
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
  boolean esEditar = true;
  Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
  if (pelicula.getId() == null) esEditar = false;
%>
<head>
  <title><%= (esEditar ? "Edición de la" : "Nueva") %> Película</title>
  <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
<jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

<h1><%= (esEditar ? "Editar" : "Nueva") %> Película</h1>

<form:form method="post" action="/editor/peliculas/confirmarCambios" modelAttribute="pelicula">
  <form:hidden path="id" />
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
        <form:input path="urlcaratula" />
      </div>

      <!-- Nombre -->
      <div class="movie-field-row">
        <label>Película:</label>
        <form:input path="titulooriginal" />
      </div>

      <!-- Fecha -->
      <div class="movie-field-row">
        <label>Fecha de Estreno:</label>
        <form:input path="fecha" />
      </div>

      <!-- Duración -->
      <div class="movie-field-row">
        <label>Duración (min):</label>
        <form:input path="duracion" />
      </div>

      <!-- Sinopsis -->
      <div class="movie-field-row">
        <label>Sinópsis:</label>
        <form:textarea path="descripcion" />
      </div>
    </div>
  </div>

  <!-- Contenedor de 3 columnas -->
  <div class="columns-container">
    <!-- Columna 1 (Crew, Cast, Productoras) -->
    <div class="column">

      <label class="select-label">Cast (Actor/Actriz - Personaje):</label>
      <form:select path="cast" items="${actuaciones}" itemValue="id" itemLabel="label" multiple="true" cssClass="multi-select" />

      <label class="select-label">Crew (Persona - Trabajo):</label>
      <form:select path="crew" items="${crewList}" itemValue="id" itemLabel="label" multiple="true" cssClass="multi-select" />

      <label class="select-label">Productoras:</label>
      <form:select path="productoras" items="${productoras}" itemValue="id" itemLabel="productoranombre" multiple="true" cssClass="multi-select" />

      <label>Página Web:</label>
      <form:input path="paginaweb" />

      <label>Presupuesto (€):</label>
      <form:input path="presupuesto" />

      <label>Recaudación (€):</label>
      <form:input path="recaudacion" />
    </div>

    <!-- Columna 2 (Países, Idiomas, Géneros) -->
    <div class="column">
      <label class="select-label">Países de rodaje:</label>
      <form:select path="paisesRodaje" items="${paisesRodaje}" itemValue="id" itemLabel="paisrodajenombre" multiple="true" cssClass="multi-select" />

      <label class="select-label">Idiomas:</label>
      <form:select path="idiomas" items="${idiomas}" itemValue="id" itemLabel="idiomahabladonombre" multiple="true" cssClass="multi-select" />

      <label class="select-label">Géneros:</label>
      <form:select path="generos" items="${generos}" itemValue="id" itemLabel="generonombre" multiple="true" cssClass="multi-select" />

      <label style="display: inline-block; vertical-align: top; margin-right: 5px;">Eslogan:</label>
      <form:textarea path="eslogan" rows="5" cols="50" style="display: inline-block;" />

      <label>Estado:</label>
      <form:input path="estado" />
    </div>
  </div>

  <!-- Botón de enviar -->
  <div class="submit-btn-container">
    <form:button class="submit-btn">
      <%= (esEditar ? "Confirmar Cambios" : "Añadir película") %>
    </form:button>
  </div>

  <!-- Botón de cancelar -->
  <jsp:include page="../Componentes/botonCancelar.jsp">
    <jsp:param name="activePage" value="pelicula" />
  </jsp:include>

</form:form>

</body>
</html>
