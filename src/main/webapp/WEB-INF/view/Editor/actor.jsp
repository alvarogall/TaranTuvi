<%--
  User: jesus
--%>

<%@ page import="es.uma.taw.tarantuvi.dto.Actor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<%
    boolean esEditar = true;
    Actor actor = (Actor) request.getAttribute("actor");
    if (actor.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar ? "Edición del" : "Nuevo/a") %> Actor/Actriz</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>

<jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

<h1><%= (esEditar ? "Editar" : "Nuevo/a") %> Actor/Actriz</h1>

<form:form method="post" modelAttribute="actor" action="/editor/actores/confirmarCambios">
    <form:hidden path="id" />

    <div class="movie-header-container">
        <div class="movie-poster">
            <img src="${actor.urlfoto != null ? actor.urlfoto : 'https://i.postimg.cc/x1GgSpbn/add-circle-svgrepo-com.png'}" alt="Foto">
        </div>

        <div class="movie-info-fields">
            <div class="movie-field-row">
                <label>URL de la foto:</label>
                <form:input path="urlfoto" />
            </div>

            <div class="movie-field-row">
                <label>Nombre:</label>
                <form:input path="nombre" />
            </div>

            <label class="select-label">Género:</label>
            <form:select path="genero" items="${generosPersona}" itemValue="id" itemLabel="generopersonanombre" multiple="false" cssClass="multi-select-one" />
            <br/>
            <label class="select-label">Nacionalidad:</label>
            <form:select path="nacionalidad" items="${nacionalidades}" itemValue="id" itemLabel="nacionalidadnombre" multiple="false" cssClass="multi-select-one" />
        </div>
    </div>

    <!-- Contenedor de 2 columnas -->
    <div class="columns-container">
        <!-- Columna 1 -->
        <div class="column">
            <label class="select-label">Películas:</label>
            <form:select path="peliculas" items="${peliculas}" itemValue="id" itemLabel="titulooriginal" multiple="true" cssClass="multi-select" />
        </div>

        <!-- Columna 2 -->
        <div class="column">
            <label class="select-label">Personajes:</label>
            <form:select path="actuaciones" items="${actuaciones}" itemValue="id" itemLabel="personaje" multiple="true" cssClass="multi-select" />

            <label class="select-label">Personaje nuevo (Si lo deseas):</label>
            <form:input path="nombrePersonaje"/>
        </div>
    </div>

    <!-- Botón de enviar -->
    <div class="submit-btn-container">
        <form:button class="submit-btn">
            <%= (esEditar ? "Confirmar Cambios" : "Añadir actor") %>
        </form:button>
    </div>

    <!-- Botón de cancelar -->
    <jsp:include page="../Componentes/botonCancelar.jsp">
        <jsp:param name="activePage" value="actor"/>
    </jsp:include>

</form:form>

</body>
</html>
