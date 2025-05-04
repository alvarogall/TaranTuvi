<%@ page import="es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity" %><%--
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
    GeneroPeliculaEntity genero = (GeneroPeliculaEntity) request.getAttribute("genero");
    if (genero.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición del" : "Nuevo") %> género</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nuevo") %> género</h1>

    <form method="post" action="/administrador/generos/confirmarCambios">
        <input type="hidden" name="id" value="<%= genero.getId() != null ? genero.getId() : -1 %>"/>
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value="<%= genero.getGeneronombre() !=null ? genero.getGeneronombre() :""%>">
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <input type="submit" class="submit-btn" value="<%= esEditar ? "Confirmar Cambios" : "Añadir género"%>">
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="genero"/>
        </jsp:include>
    </form>
</body>
</html>
