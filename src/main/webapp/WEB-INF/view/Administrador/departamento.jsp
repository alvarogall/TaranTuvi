<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Departamento" %><%--
  Created by IntelliJ IDEA.
  User: table
  Date: 24/05/2025
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%
    boolean esEditar = true;
    Departamento departamento = (Departamento) request.getAttribute("departamento");
    if (departamento.getId() == null) esEditar = false;
%>

  <head>
    <title><%= (esEditar? "Edición del" : "Nuevo") %> departamento</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
  </head>
<body>
  <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

  <h1><%= (esEditar? "Editar" : "Nuevo") %> departamento</h1>

  <form:form method="post" modelAttribute="departamento" action="/administrador/departamentos/confirmarCambios">
    <form:hidden path="id" />
    <!-- Contenedor flex -->
    <div class="movie-header-container">
      <!-- Campos a la derecha -->
      <div class="movie-info-fields">
        <!-- Nombre -->
        <div class="movie-field-row">
          <label>Nombre:</label>
          <form:input path="departamentonombre" />
        </div>
      </div>
    </div>

    <!-- Botón de enviar -->
    <div class="submit-btn-container">
      <form:button class="submit-btn">
        <%= (esEditar ? "Confirmar cambios" : "Añadir departamento") %>
      </form:button>
    </div>

    <!-- Botón de cancelar -->
    <jsp:include page="../Componentes/botonCancelar.jsp">
      <jsp:param name="activePage" value="departamento"/>
    </jsp:include>
  </form:form>
</body>
</html>
