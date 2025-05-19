<%@ page import="es.uma.taw.tarantuvi.dto.ActorResumen" %>
<%@ page import="es.uma.taw.tarantuvi.dto.ActuacionResumen" %>
<%@ page import="es.uma.taw.tarantuvi.dto.PeliculaResumen" %>
<%@ page import="es.uma.taw.tarantuvi.dto.TrabajoResumen" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Actor</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
  ActorResumen actor = (ActorResumen) request.getAttribute("actor");
%>
<body class="bg-[#f4f5f7] font-sans m-0">
<jsp:include page="navBar.jsp"/>
<% if (actor != null) { %>
<div class="flex justify-center items-start py-12 pb-6">
  <div class="flex flex-row bg-gradient-to-r from-white to-gray-50 border border-[#e0e3e8] rounded-[14px] max-w-[700px] w-full min-h-[260px] p-8 pl-6 shadow-md gap-9">
    <div class="flex-shrink-0 flex justify-center items-start">
      <img src="<%= actor.getUrlfoto() != null ? actor.getUrlfoto() : "" %>" alt="Foto de <%= actor.getNombre() %>"
           class="w-[170px] h-[220px] object-cover rounded-lg border border-[#e0e3e8] bg-white shadow-md block" />
    </div>
    <div class="flex-1 flex flex-col min-w-0">
      <h1 class="text-3xl font-bold mb-3 text-[#1a1a1a] leading-tight truncate"><%= actor.getNombre() %></h1>
      <div class="flex gap-3 items-center text-[#5a5a5a] text-[1.05em] mb-4">
        <span>
          <strong>Género:</strong>
          <%= actor.getNombreGenero() != null ? actor.getNombreGenero() : "" %>
        </span>
        <span class="text-gray-400">&bull;</span>
        <span>
          <strong>Nacionalidad:</strong>
          <%= actor.getNombreNacionalidad() != null ? actor.getNombreNacionalidad() : "" %>
        </span>
      </div>
      <div class="mb-2 space-y-1">
        <div class="text-[#444] text-base"><strong>Número de actuaciones:</strong> <%= actor.getActuaciones() != null ? actor.getActuaciones().size() : 0 %></div>
        <div class="text-[#444] text-base"><strong>Número de trabajos:</strong> <%= actor.getTrabajos() != null ? actor.getTrabajos().size() : 0 %></div>
      </div>
    </div>
  </div>
</div>

<% if (actor.getActuaciones() != null && !actor.getActuaciones().isEmpty()) { %>
<div class="max-w-[700px] mx-auto mb-8 px-6">
  <h2 class="text-2xl font-bold text-[#1a1a1a] mb-5 mt-10 text-left">Películas</h2>
  <% for (ActuacionResumen actuacion : actor.getActuaciones()) {
       PeliculaResumen pelicula = actuacion.getPelicula();
       if (pelicula == null) continue;
  %>
  <div class="bg-white border border-[#e0e3e8] rounded-xl mb-8 shadow-md p-6 flex flex-col gap-5">
    <div class="flex flex-row gap-6">
      <div>
        <img src="<%= pelicula.getUrlcaratula() != null ? pelicula.getUrlcaratula() : "" %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>"
             class="w-[110px] h-[150px] object-cover rounded-md border border-[#e0e3e8] bg-white shadow" />
      </div>
      <div class="flex-1 flex flex-col min-w-0">
        <h3 class="text-lg font-bold text-[#1a1a1a] mb-2"><%= pelicula.getTitulooriginal() %></h3>
        <div class="flex gap-3 items-center text-[#5a5a5a] text-base mb-2">
          <span><%= pelicula.getFechaestreno() != null ? pelicula.getFechaestreno().toString() : "" %></span>
          <span class="text-gray-400">&bull;</span>
          <span><%= pelicula.getDuracion() != null ? pelicula.getDuracion() + " min" : "" %></span>
          <span class="text-gray-400">&bull;</span>
          <span><%= pelicula.getEstado() != null ? pelicula.getEstado() : "" %></span>
        </div>
        <div class="text-[#444] text-[0.98em] mb-2 leading-snug">
          <%= pelicula.getDescripcion() != null ? pelicula.getDescripcion() : "" %>
        </div>
        <div class="text-[0.98em] text-[#444]">
          <strong class="text-[#1a1a1a]">Personaje interpretado:</strong>
          <%= actuacion.getPersonaje() != null ? actuacion.getPersonaje() : "" %>
        </div>
      </div>
    </div>
  </div>
  <% } %>
</div>
<% } %>

<% if (actor.getTrabajos() != null && !actor.getTrabajos().isEmpty()) { %>
<div class="max-w-[700px] mx-auto mb-8 px-6">
  <h2 class="text-2xl font-bold text-[#1a1a1a] mb-5 mt-10 text-left">Trabajos</h2>
  <div class="space-y-6">
    <% for (TrabajoResumen trabajo : actor.getTrabajos()) { %>
    <div class="bg-white border border-[#e0e3e8] rounded-xl shadow-md p-6 flex flex-col gap-2">
      <div class="flex flex-col sm:flex-row sm:items-center gap-2 sm:gap-6">
        <div class="flex-1">
          <div class="flex flex-wrap gap-2 mb-2">
            <span class="inline-block bg-[#f3e9ff] text-[#8e44ad] font-semibold rounded-full px-3 py-1 text-xs shadow-sm">
              <%= trabajo.getDepartamento() != null ? trabajo.getDepartamento() : "" %>
            </span>
            <span class="inline-block bg-[#e9f3ff] text-[#007bff] font-semibold rounded-full px-3 py-1 text-xs shadow-sm">
              <%= trabajo.getTrabajo() != null ? trabajo.getTrabajo() : "" %>
            </span>
          </div>
          <div class="text-lg font-bold text-[#1a1a1a] mb-1">
            <%= trabajo.getPelicula() != null ? trabajo.getPelicula() : "" %>
          </div>
        </div>
      </div>
    </div>
    <% } %>
  </div>
</div>
<% } %>
<% } %>
</body>
</html>
