<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Nacionalidad" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 21/05/2025
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    boolean esEditar = true;
    Nacionalidad nacionalidad = (Nacionalidad) request.getAttribute("nacionalidad");
    if (nacionalidad.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nueva") %> nacionalidad</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nueva") %> nacionalidad</h1>

    <form:form method="post" modelAttribute="nacionalidad" action="/administrador/nacionalidades/confirmarCambios">
        <form:hidden path="id" />
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <form:input path="nacionalidadnombre" />
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <form:button class="submit-btn">
                <%= (esEditar ? "Confirmar cambios" : "Añadir nacionalidad") %>
            </form:button>
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="nacionalidad"/>
        </jsp:include>
    </form:form>
</body>
</html>
