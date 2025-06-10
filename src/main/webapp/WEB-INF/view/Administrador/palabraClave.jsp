<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.PalabraClave" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 24/05/2025
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
  boolean esEditar = true;
  PalabraClave palabraClave = (PalabraClave) request.getAttribute("palabraClave");
  if (palabraClave.getId() == null) esEditar = false;
%>

<head>
  <title><%= (esEditar? "Edición de la" : "Nueva") %> palabra clave</title>
  <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
  <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

  <h1><%= (esEditar? "Editar" : "Nueva") %> palabra clave</h1>

  <form:form method="post" modelAttribute="palabraClave" action="/administrador/palabrasClave/confirmarCambios">
    <form:hidden path="id" />
    <!-- Contenedor flex -->
    <div class="movie-header-container">
      <!-- Campos a la derecha -->
      <div class="movie-info-fields">
        <!-- Nombre -->
        <div class="movie-field-row">
          <label>Nombre:</label>
          <form:input path="palabraclavenombre" />
        </div>
      </div>
    </div>

    <!-- Botón de enviar -->
    <div class="submit-btn-container">
      <form:button class="submit-btn">
        <%= (esEditar ? "Confirmar cambios" : "Añadir palabra clave") %>
      </form:button>
    </div>

    <!-- Botón de cancelar -->
    <jsp:include page="../Componentes/botonCancelar.jsp">
      <jsp:param name="activePage" value="palabraClave"/>
    </jsp:include>
  </form:form>
</body>
</html>
