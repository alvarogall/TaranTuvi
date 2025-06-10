<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.IdiomaHablado" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 19/05/2025
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    IdiomaHablado idiomaHablado = (IdiomaHablado) request.getAttribute("idiomaHablado");
    if (idiomaHablado.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nuevo") %> idioma hablado</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nuevo") %> idioma hablado</h1>

    <form:form method="post" modelAttribute="idiomaHablado" action="/administrador/idiomasHablados/confirmarCambios">
        <form:hidden path="id" />

        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <form:input path="idiomahabladonombre" />
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <form:button class="submit-btn">
                <%= (esEditar ? "Confirmar cambios" : "Añadir idioma hablado") %>
            </form:button>
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="idiomaHablado"/>
        </jsp:include>

    </form:form>
</body>
</html>
