<%@ page import="es.uma.taw.tarantuvi.entity.PersonaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actores</title>
    <link rel="stylesheet" type="text/css" href="/css/componentes/navBar.css">
    <link rel="stylesheet" type="text/css" href="/css/usuario/actores.css">
</head>
<%
    List<PersonaEntity> actores = (List<PersonaEntity>) request.getAttribute("actores");
%>
<body>
<jsp:include page="../componentes/navBar.jsp"/>
<div class="grid-container">
    <%
        if (actores != null) {
            for (PersonaEntity actor : actores) {
    %>
    <div class="actor-card">
        <img src="<%= actor.getUrlfoto() != null && !actor.getUrlfoto().isEmpty() ? actor.getUrlfoto() : "/img/default_actor.png" %>" alt="Foto de <%= actor.getNombre() %>">
        <div class="actor-info">
            <div class="actor-name">
                <a href="/actor/?id=<%= actor.getId() %>" class="actor-name-link">
                    <%= actor.getNombre() %>
                </a>
            </div>
            <div class="actor-meta">
                <% if (actor.getGeneropersonaid() != null) { %>
                <span class="badge"><%= actor.getGeneropersonaid().getGeneropersonanombre() %></span>
                <% } %>
                <% if (actor.getNacionalidadid() != null) { %>
                <span class="badge badge-nacionalidad"><%= actor.getNacionalidadid().getNacionalidadnombre() %></span>
                <% } %>
            </div>
        </div>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
