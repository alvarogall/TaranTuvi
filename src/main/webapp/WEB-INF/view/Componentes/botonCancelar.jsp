<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String activePage = request.getParameter("activePage");
    String cancelarUrl = "actor".equals(activePage) ? "/actores" : "/peliculas";
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
