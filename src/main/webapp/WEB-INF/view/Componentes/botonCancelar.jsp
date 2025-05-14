<%--
  User: jesus
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String activePage = request.getParameter("activePage");
    String cancelarUrl = "";
    if ("actor".equals(activePage)) cancelarUrl = "/editor/actores";
    if ("pelicula".equals(activePage)) cancelarUrl = "/editor/peliculas";
    if ("genero".equals(activePage)) cancelarUrl = "/administrador/generos";
    if ("productora".equals(activePage)) cancelarUrl = "/administrador/productoras";
%>
<html>
<head>
    <title>botonCancelar</title>
    <link rel="stylesheet" type="text/css" href="/css/Componentes/botonCancelar.css">
</head>
<body>
<div class="cancel-btn-container">
    <button type="button" class="cancel-btn" onclick="location.href='<%= cancelarUrl %>'">
        Cancelar
    </button>
</div>
</body>
</html>
