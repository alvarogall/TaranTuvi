<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.GeneroPelicula" %><%--
  Created by IntelliJ IDEA.
  User: table
  Date: 04/05/2025
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    GeneroPelicula genero = (GeneroPelicula) request.getAttribute("genero");
    if (genero.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nuevo") %> género</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nuevo") %> género</h1>

    <form:form method="post" modelAttribute="genero" action="/administrador/generos/confirmarCambios">
        <form:hidden path="id" />
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <form:input path="generonombre" />
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <form:button class="submit-btn">
                <%= (esEditar ? "Confirmar cambios" : "Añadir género") %>
            </form:button>
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="genero"/>
        </jsp:include>
    </form:form>
</body>
</html>
