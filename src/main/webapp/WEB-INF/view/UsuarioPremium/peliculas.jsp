<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page import="es.uma.taw.tarantuvi.dto.GeneroPelicula" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Productora" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Actor" %>
<%@ page import="es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Peliculas</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/choices.js/public/assets/styles/choices.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js"></script>
</head>
<%
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<GeneroPelicula> generos = (List<GeneroPelicula>) request.getAttribute("generos");
    List<Productora> productoras = (List<Productora>) request.getAttribute("productoras");
    List<Actor> actores = (List<Actor>) request.getAttribute("actores");
%>
<body class="bg-gray-100 font-sans m-0">
<jsp:include page="navBar.jsp"/>
<div class="max-w-6xl mx-auto py-10 px-2">
    <form:form method="post" action="/${usuario.rol}/pelicula/filtrar"
               cssClass="bg-white border border-gray-200 rounded-xl shadow-md p-6 mb-10 flex flex-col gap-6" modelAttribute="filtroPelicula">
        <div class="flex flex-col md:flex-row gap-6">
            <div class="flex-1">
                <form:label path="nombre" cssClass="block font-semibold text-gray-700 mb-1">Nombre de película</form:label>
                <form:input path="nombre" cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"/>
            </div>
            <div class="flex-1">
                <label class="block font-semibold text-gray-700 mb-1">Valoración mínima</label>
                <form:hidden path="valoracion" id="valoracion"/>
                <div class="flex gap-1">
                    <%
                        for (int i = 0; i <= 10; i++) {
                    %>
                        <button type="button"
                                class="valoracion-btn w-9 h-9 rounded-full border-2 border-blue-400 text-blue-700 font-bold text-base bg-white hover:bg-blue-100 transition"
                                data-value="<%= i %>"><%= i %></button>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="flex-1">
                <form:label path="anio" cssClass="block font-semibold text-gray-700 mb-1">Año posterior a</form:label>
                <form:input path="anio" type="number" min="1900" max="2100"
                            cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"/>
            </div>
        </div>
        <div class="flex flex-col md:flex-row gap-6">
            <div class="flex-1">
                <form:label path="generos" cssClass="block font-semibold text-gray-700 mb-1">Géneros</form:label>
                <form:select path="generos" multiple="true" id="generos"
                             cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition h-28">
                    <form:options items="${generos}" itemValue="id" itemLabel="generonombre"/>
                </form:select>
            </div>
            <div class="flex-1">
                <form:label path="productoras" cssClass="block font-semibold text-gray-700 mb-1">Productoras</form:label>
                <form:select path="productoras" multiple="true" id="productoras"
                             cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition h-28">
                    <form:options items="${productoras}" itemValue="id" itemLabel="productoranombre"/>
                </form:select>
            </div>
            <div class="flex-1">
                <form:label path="actores" cssClass="block font-semibold text-gray-700 mb-1">Actores</form:label>
                <form:select path="actores" multiple="true" id="actores"
                             cssClass="w-full rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition h-28">
                    <form:options items="${actores}" itemValue="id" itemLabel="nombre"/>
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
                new Choices('#generos', {
                    removeItemButton: true,
                    shouldSort: false,
                    noResultsText: 'No hay resultados',
                    noChoicesText: 'No hay más opciones',
                    itemSelectText: 'Pulsa para seleccionar'
                });
                new Choices('#productoras', {
                    removeItemButton: true,
                    shouldSort: false,
                    noResultsText: 'No hay resultados',
                    noChoicesText: 'No hay más opciones',
                    itemSelectText: 'Pulsa para seleccionar'
                });
                new Choices('#actores', {
                    removeItemButton: true,
                    shouldSort: false,
                    noResultsText: 'No hay resultados',
                    noChoicesText: 'No hay más opciones',
                    itemSelectText: 'Pulsa para seleccionar'
                });

                const valoracionBtns = document.querySelectorAll('.valoracion-btn');
                const valoracionInput = document.getElementById('valoracion');
                valoracionBtns.forEach(btn => {
                    btn.addEventListener('click', () => {
                        const val = parseInt(btn.getAttribute('data-value'));
                        valoracionInput.value = val;
                        valoracionBtns.forEach(b => {
                            const bval = parseInt(b.getAttribute('data-value'));
                            if (bval >= val) {
                                b.classList.remove('bg-white', 'text-blue-700', 'border-blue-400');
                                b.classList.add('bg-blue-500', 'text-white', 'border-blue-700');
                            } else {
                                b.classList.remove('bg-blue-500', 'text-white', 'border-blue-700');
                                b.classList.add('bg-white', 'text-blue-700', 'border-blue-400');
                            }
                        });
                    });
                });

                const valoracionActual = valoracionInput.value ? parseInt(valoracionInput.value) : null;
                if (valoracionActual !== null && !isNaN(valoracionActual)) {
                    valoracionBtns.forEach(b => {
                        const bval = parseInt(b.getAttribute('data-value'));
                        if (bval >= valoracionActual) {
                            b.classList.remove('bg-white', 'text-blue-700', 'border-blue-400');
                            b.classList.add('bg-blue-500', 'text-white', 'border-blue-700');
                        } else {
                            b.classList.remove('bg-blue-500', 'text-white', 'border-blue-700');
                            b.classList.add('bg-white', 'text-blue-700', 'border-blue-400');
                        }
                    });
                }
            });
        </script>
    </form:form>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-7">
        <%
            if (peliculas != null) {
                for (Pelicula pelicula : peliculas) {
        %>
        <div class="flex flex-row items-center bg-gradient-to-r from-white via-white to-gray-50 border border-gray-200 rounded-xl min-h-[170px] p-4 transition hover:border-blue-300 hover:bg-gradient-to-r hover:from-blue-50 hover:to-blue-100 shadow-sm">
            <img src="<%= pelicula.getUrlcaratula() %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>"
                 class="w-[90px] h-[135px] object-cover rounded-md mr-6 border border-gray-200 bg-white shadow-sm" />
            <div class="flex flex-col justify-center min-w-0 flex-1">
                <div class="text-lg font-bold mb-1 text-gray-900 truncate">
                    <a href="<%= "/" + usuario.getRol() %>/pelicula?id=<%= pelicula.getId() %>"
                       class="hover:text-blue-700 transition-colors duration-200">
                        <%= pelicula.getTitulooriginal() %>
                    </a>
                </div>
                <div class="flex items-center text-yellow-500 mb-2 text-base">
                    <span class="text-xl mr-1">&#9733;</span>
                    <span>
                        <%= pelicula.getNota() != null ? String.format("%.1f", pelicula.getNota()) : "-" %>
                    </span>
                </div>
                <div class="text-sm text-gray-500 mb-1">
                    <%= pelicula.getFechaestreno() != null ? pelicula.getFechaestreno().getYear() : "" %> &nbsp;&bull;&nbsp; <%= pelicula.getDuracion() %> min
                </div>
                <div class="mt-1 flex flex-wrap gap-1">
                    <% if (pelicula.getGeneroPeliculaList() != null) {
                        for (GeneroPeliculaEntity genero : pelicula.getGeneroPeliculaList()) { %>
                    <span class="inline-block bg-blue-50 text-blue-600 font-semibold rounded-full px-3 py-1 text-xs shadow-sm">
                        <%= genero.getGeneronombre() %>
                    </span>
                    <%  }
                    } %>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
