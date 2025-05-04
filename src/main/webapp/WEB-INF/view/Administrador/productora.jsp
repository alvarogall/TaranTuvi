<%@ page import="es.uma.taw.tarantuvi.entity.ProductoraEntity" %><%--
  Created by IntelliJ IDEA.
  User: table
  Date: 04/05/2025
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    boolean esEditar = true;
    ProductoraEntity productora = (ProductoraEntity) request.getAttribute("productora");
    if (productora.getId() == null) esEditar = false;
%>

<head>
    <title><%= (esEditar? "Edición de la" : "Nueva") %> productora</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/editarAñadir.css">
</head>
<body>
    <jsp:include page="../Componentes/navBarEditarAñadir.jsp" />

    <h1><%= (esEditar? "Editar" : "Nueva") %> productora</h1>

    <form method="post" action="/administrador/productoras/confirmarCambios">
        <input type="hidden" name="id" value="<%= productora.getId() != null ? productora.getId() : -1 %>"/>
        <!-- Contenedor flex -->
        <div class="movie-header-container">
            <!-- Campos a la derecha -->
            <div class="movie-info-fields">
                <!-- Nombre -->
                <div class="movie-field-row">
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value="<%= productora.getProductoranombre() !=null ? productora.getProductoranombre() :""%>">
                </div>
            </div>
        </div>

        <!-- Botón de enviar -->
        <div class="submit-btn-container">
            <input type="submit" class="submit-btn" value="<%= esEditar ? "Confirmar Cambios" : "Añadir productora"%>">
        </div>

        <!-- Botón de cancelar -->
        <jsp:include page="../Componentes/botonCancelar.jsp">
            <jsp:param name="activePage" value="productora"/>
        </jsp:include>
    </form>
</body>
</html>
