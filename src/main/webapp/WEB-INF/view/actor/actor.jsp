<%@ page import="es.uma.taw.tarantuvi.entity.PersonaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PersonaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Actor</title>
  <link rel="stylesheet" type="text/css" href="/css/componentes/navBar.css">
  <link rel="stylesheet" type="text/css" href="/css/usuario/actor.css">
</head>
<%
  PersonaEntity actor = (PersonaEntity) request.getAttribute("actor");
%>
<body>
<jsp:include page="../componentes/navBar.jsp"/>
<% if (actor != null) { %>
<div class="actor-detalle-container">
  <div class="actor-detalle-card">
    <div class="actor-detalle-foto">
      <img src="<%= actor.getUrlfoto() != null ? actor.getUrlfoto() : "" %>" alt="Foto de <%= actor.getNombre() %>">
    </div>
    <div class="actor-detalle-info">
      <h1 class="actor-detalle-nombre"><%= actor.getNombre() %></h1>
      <div class="actor-detalle-meta">
        <span>
          <strong>Género:</strong>
          <%= actor.getGeneropersonaid() != null ? actor.getGeneropersonaid().getGeneropersonanombre() : "" %>
        </span>
        <span>&bull;</span>
        <span>
          <strong>Nacionalidad:</strong>
          <%= actor.getNacionalidadid() != null ? actor.getNacionalidadid().getNacionalidadnombre() : "" %>
        </span>
      </div>
      <div class="actor-detalle-extra">
        <div>
          <strong>Número de actuaciones:</strong>
          <%= actor.getActuacionList() != null ? actor.getActuacionList().size() : 0 %>
        </div>
        <div>
          <strong>Número de trabajos:</strong>
          <%= actor.getTrabajoList() != null ? actor.getTrabajoList().size() : 0 %>
        </div>
      </div>
    </div>
  </div>
</div>
<% if (actor.getActuacionList() != null && !actor.getActuacionList().isEmpty()) { %>
<div class="actor-peliculas-container">
    <h2 class="actor-peliculas-titulo">Películas</h2>
    <% for (ActuacionEntity actuacion : actor.getActuacionList()) {
      PeliculaEntity pelicula = actuacion.getPeliculaid();
      if (pelicula == null) continue;
    %>
    <div class="detalle-container">
      <div class="detalle-card detalle-card-compact">
        <div class="detalle-caratula">
          <img src="<%= pelicula.getUrlcaratula() != null ? pelicula.getUrlcaratula() : "" %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>">
        </div>
        <div class="detalle-info">
          <h3 class="detalle-titulo"><%= pelicula.getTitulooriginal() %></h3>
          <div class="detalle-meta">
            <span><%= pelicula.getFechaestreno() != null ? pelicula.getFechaestreno().toString() : "" %></span>
            <span>&bull;</span>
            <span><%= pelicula.getDuracion() != null ? pelicula.getDuracion() + " min" : "" %></span>
            <span>&bull;</span>
            <span><%= pelicula.getEstado() != null ? pelicula.getEstado() : "" %></span>
          </div>
          <div class="detalle-descripcion">
            <%= pelicula.getDescripcion() != null ? pelicula.getDescripcion() : "" %>
          </div>
          <div class="detalle-extra">
            <div>
              <strong>Personaje interpretado:</strong>
              <%= actuacion.getPersonaje() != null ? actuacion.getPersonaje() : "" %>
            </div>
          </div>
        </div>
      </div>
      <div class="pelicula-actores-container">
        <h4 class="pelicula-actores-titulo">Reparto</h4>
        <table class="pelicula-actores-tabla">
          <thead>
          <tr>
            <th>Actor</th>
            <th>Personaje</th>
          </tr>
          </thead>
          <tbody>
          <% if (pelicula.getActuacionList() != null) {
            for (ActuacionEntity act : pelicula.getActuacionList()) {
              PersonaEntity coactor = act.getPersonaid();
          %>
          <tr>
            <td>
              <div class="actor-miniatura">
                <img src="<%= coactor.getUrlfoto() != null ? coactor.getUrlfoto() : "" %>" alt="Foto de <%= coactor.getNombre() %>">
                <span><%= coactor.getNombre() %></span>
              </div>
            </td>
            <td><%= act.getPersonaje() != null ? act.getPersonaje() : "" %></td>
          </tr>
          <%   }
          }
          %>
          </tbody>
        </table>
      </div>
    </div>
    <% } %>

</div>
<% } %>
<% } %>
</body>
</html>
