<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.PaisRodaje" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 24/05/2025
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    PaisRodaje paisRodaje = (PaisRodaje) request.getAttribute("paisRodaje");
    if (paisRodaje.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nuevo") %> país de rodaje</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nuevo") %> país de rodaje</h1>

    <form:form method="post" modelAttribute="paisRodaje" action="/administrador/paisesRodaje/confirmarCambios">
        <form:hidden path="id" />
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <form:input path="paisrodajenombre" />
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <form:button class="submit-btn">
                <%= (esEditar ? "Confirmar cambios" : "Añadir país de rodaje") %>
            </form:button>
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="paisRodaje"/>
        </jsp:include>
    </form:form>
</body>
</html>
