<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Productora" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 04/05/2025
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    Productora productora = (Productora) request.getAttribute("productora");
    if (productora.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición de la" : "Nueva") %> productora</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nueva") %> productora</h1>

    <form:form method="post" modelAttribute="productora" action="/administrador/productoras/confirmarCambios">
        <form:hidden path="id" />
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <form:input path="productoranombre" />
                </div>
                <div class="movie-field-row">
                    <label class="select-label">Nacionalidad:</label>
                    <form:select path="nacionalidad.id" multiple="false" cssClass="multi-select-one">
                        <form:options items="${nacionalidades}" itemValue="id" itemLabel="nacionalidadnombre"/>
                    </form:select>
                </div>
                <div class="movie-field-row">
                    <label>CEO:</label>
                    <form:input path="ceo" />
                </div>
                <div class="movie-field-row">
                    <label>Sede:</label>
                    <form:input path="sede" />
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <form:button class="submit-btn">
                <%= (esEditar ? "Confirmar cambios" : "Añadir productora") %>
            </form:button>
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="productora"/>
        </jsp:include>
    </form:form>
</body>
</html>
