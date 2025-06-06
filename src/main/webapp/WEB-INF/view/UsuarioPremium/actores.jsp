<%-- @author Álvaro Gallardo --%>

<%@ page import="es.uma.taw.tarantuvi.dto.Actor" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page import="es.uma.taw.tarantuvi.dto.GeneroPersona" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Nacionalidad" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
  <title>Actores</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/choices.js/public/assets/styles/choices.min.css" />
  <script src="https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js"></script>
</head>
<%
  List<Actor> actores = (List<Actor>) request.getAttribute("actores");
  List<GeneroPersona> generos = (List<GeneroPersona>) request.getAttribute("generos");
  List<Nacionalidad> nacionalidades = (List<Nacionalidad>) request.getAttribute("nacionalidades");
  List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
  Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<body class="bg-[#f4f5f7] font-sans m-0">
<jsp:include page="navBar.jsp">
  <jsp:param name="activePage" value="actores"/>
</jsp:include>
<div class="max-w-6xl mx-auto py-10 px-2">
  <form:form method="post" action="/${usuario.rol}/actor/filtrar"
             cssClass="bg-white border border-gray-200 rounded-xl shadow-md p-6 mb-10 flex flex-col gap-6"
             modelAttribute="filtroActor">
    <div class="flex flex-col md:flex-row gap-6">
      <div class="flex-1 flex flex-col gap-2">
        <form:label path="nombre" cssClass="block font-semibold text-gray-700 mb-1">Nombre</form:label>
        <form:input path="nombre" cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"/>
      </div>
      <div class="flex-1 flex flex-col gap-2">
        <form:label path="genero" cssClass="block font-semibold text-gray-700 mb-1">Género</form:label>
        <div class="relative">
          <form:select path="genero" cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 pr-8 focus:outline-none focus:ring-2 focus:ring-blue-400 transition appearance-none bg-white">
            <form:option value="" label="Todos"/>
            <form:options items="${generos}" itemValue="id" itemLabel="generopersonanombre"/>
          </form:select>
          <span class="absolute inset-y-0 right-3 flex items-center pointer-events-none text-gray-400">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"/>
            </svg>
          </span>
        </div>
      </div>
      <div class="flex-1 flex flex-col gap-2">
        <form:label path="nacionalidad" cssClass="block font-semibold text-gray-700 mb-1">Nacionalidad</form:label>
        <div class="relative">
          <form:select path="nacionalidad" cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 pr-8 focus:outline-none focus:ring-2 focus:ring-blue-400 transition appearance-none bg-white">
            <form:option value="" label="Todas"/>
            <form:options items="${nacionalidades}" itemValue="id" itemLabel="nacionalidadnombre"/>
          </form:select>
          <span class="absolute inset-y-0 right-3 flex items-center pointer-events-none text-gray-400">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"/>
            </svg>
          </span>
        </div>
      </div>
      <div class="flex-1 flex flex-col gap-2">
        <form:label path="peliculas" cssClass="block font-semibold text-gray-700 mb-1">Películas</form:label>
        <form:select path="peliculas" multiple="true" id="peliculas"
                     cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition min-h-[48px] max-h-[112px] bg-white">
          <form:options items="${peliculas}" itemValue="id" itemLabel="titulooriginal"/>
        </form:select>
      </div>
    </div>
    <div class="flex justify-end">
      <button type="submit"
              class="px-6 py-2 bg-blue-600 text-white rounded-lg font-semibold hover:bg-blue-700 transition">
        Filtrar
      </button>
    </div>
    <script>
      document.addEventListener('DOMContentLoaded', function () {
        new Choices('#peliculas', {
          removeItemButton: true,
          shouldSort: false,
          noResultsText: 'No hay resultados',
          noChoicesText: 'No hay más opciones',
          itemSelectText: 'Pulsa para seleccionar'
        });
      });
    </script>
  </form:form>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-7">
    <% if (actores != null) {
      for (Actor actor : actores) { %>
    <a href="<%= "/" + usuario.getRol() %>/actor?id=<%= actor.getId() %>"
       class="flex flex-row items-center bg-gradient-to-r from-white via-white to-gray-50 border border-[#e0e3e8] rounded-xl min-h-[170px] p-5 transition hover:border-[#b6b9c3] hover:bg-gradient-to-r hover:from-[#f7faff] hover:to-[#eaf2fb] shadow-sm no-underline">
      <img src="<%= actor.getUrlfoto() != null ? actor.getUrlfoto() : "" %>"
           alt="Foto de <%= actor.getNombre() %>"
           class="w-[90px] h-[135px] object-cover rounded-md mr-5 border border-[#e0e3e8] bg-white shadow-sm" />
      <div class="flex flex-col justify-center min-w-0 flex-1">
        <div class="text-[1.18em] font-bold mb-1 text-[#1a1a1a] truncate">
          <%= actor.getNombre() %>
        </div>
        <div class="flex flex-wrap gap-2 text-[0.98em] text-[#5a5a5a] mb-2">
            <span class="badge badge-nacionalidad bg-[#f3e9ff] text-[#8e44ad] font-semibold rounded-full px-3 py-1 text-xs shadow-sm">
              <%= actor.getNombreNacionalidad() != null ? actor.getNombreNacionalidad() : "" %>
            </span>
        </div>
      </div>
    </a>
    <%   }
    } %>
  </div>
</div>
</body>
</html>
