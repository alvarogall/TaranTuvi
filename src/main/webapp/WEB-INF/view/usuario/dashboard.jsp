<%-- @author Álvaro Gallardo --%>

<%@ page import="es.uma.taw.tarantuvi.dto.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Pelicula peliculaMejorValorada = (Pelicula) request.getAttribute("peliculaMejorValoradaDto");
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
%>
<body class="min-h-screen bg-gradient-to-br from-white via-blue-50 to-blue-100">
    <jsp:include page="navBar.jsp">
        <jsp:param name="activePage" value="inicio"/>
    </jsp:include>

    <div class="max-w-5xl mx-auto mt-12 px-4">
        <div class="flex flex-col md:flex-row gap-10 items-center justify-center">
            <div class="w-full md:w-[50%] flex flex-col items-center justify-center relative">
                <h2 class="text-3xl font-extrabold text-blue-900 mb-6 tracking-tight text-center">Películas</h2>
                <div class="relative w-full flex justify-center items-center" style="min-height: 420px;">
                    <button id="carousel-prev" class="carousel-btn left-0 absolute z-10 bg-white hover:bg-blue-100 text-blue-700 rounded-full shadow p-2 transition top-1/2 -translate-y-1/2" aria-label="Anterior">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                        </svg>
                    </button>
                    <div id="carousel-track" class="flex flex-col items-center transition-all duration-300 w-full">
                        <% if (peliculas != null && !peliculas.isEmpty()) { 
                            Pelicula peli = peliculas.get(0); %>
                            <a id="carousel-link" href="/<%= usuario.getRol() %>/pelicula?id=<%= peli.getId() %>" 
                               class="relative w-full h-[420px] max-w-[420px] rounded-2xl shadow-lg border border-blue-100 overflow-hidden group transition-all duration-200 flex">
                                <img id="carousel-img" src="<%= peli.getUrlcaratula() %>" alt="Carátula"
                                     class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-200">
                                <div class="absolute bottom-0 left-0 w-full bg-gradient-to-t from-black/80 via-black/60 to-transparent px-5 py-4">
                                    <div class="flex items-center gap-2 mb-1">
                                        <span class="text-yellow-400 text-lg font-bold">★</span>
                                        <span id="carousel-nota" class="text-lg font-bold text-white"><%= peli.getNota() != null ? String.format("%.1f", peli.getNota()) : "-" %></span>
                                        <% if (peli.getGeneroPeliculaList() != null && !peli.getGeneroPeliculaList().isEmpty()) { %>
                                            <span id="carousel-genero" class="ml-3 text-xs bg-blue-700/80 text-white rounded-full px-3 py-1 font-medium"><%= peli.getGeneroPeliculaList().get(0).getGeneronombre() %></span>
                                        <% } %>
                                    </div>
                                    <span id="carousel-title" class="block text-xl font-bold text-white drop-shadow-sm truncate">
                                        <%= peli.getTitulooriginal() %>
                                    </span>
                                </div>
                            </a>
                        <% } else { %>
                            <span class="text-gray-400">No hay películas</span>
                        <% } %>
                    </div>
                    <button id="carousel-next" class="carousel-btn right-0 absolute z-10 bg-white hover:bg-blue-100 text-blue-700 rounded-full shadow p-2 transition top-1/2 -translate-y-1/2" aria-label="Siguiente">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                        </svg>
                    </button>
                </div>
            </div>
            <div class="w-full md:w-[50%] flex flex-col items-center justify-center">
                <h2 class="text-3xl font-extrabold text-blue-900 mb-6 tracking-tight text-center">Mejor valorada</h2>
                <% if (peliculaMejorValorada != null) { %>
                <section class="bg-white rounded-3xl shadow-2xl border border-blue-100 p-8 flex flex-col md:flex-row gap-10 items-center hover:shadow-blue-200 transition-shadow duration-300 w-full" style="min-height: 420px;">
                    <img src="<%= peliculaMejorValorada.getUrlcaratula() %>" alt="Carátula"
                         class="w-44 h-64 object-cover rounded-2xl border-2 border-blue-200 shadow-lg">
                    <div class="flex-1 flex flex-col gap-3">
                        <h3 class="text-4xl font-extrabold text-gray-900 mb-1 leading-tight">
                            <a href="/<%= usuario.getRol() %>/pelicula?id=<%= peliculaMejorValorada.getId() %>" class="hover:underline transition text-gray-900">
                                <%= peliculaMejorValorada.getTitulooriginal() %>
                            </a>
                        </h3>
                        <p class="italic text-blue-700 mb-2 text-lg"><%= peliculaMejorValorada.getEslogan() != null ? peliculaMejorValorada.getEslogan() : "" %></p>
                        <div class="flex flex-wrap gap-4 text-gray-600 text-lg mb-2 font-medium">
                            <span><%= peliculaMejorValorada.getFechaestreno() != null ? peliculaMejorValorada.getFechaestreno().toString() : "" %></span>
                            <span class="text-gray-300">&bull;</span>
                            <span><%= peliculaMejorValorada.getDuracion() != null ? peliculaMejorValorada.getDuracion() + " min" : "" %></span>
                            <span class="text-gray-300">&bull;</span>
                            <span><%= peliculaMejorValorada.getEstado() != null ? peliculaMejorValorada.getEstado() : "" %></span>
                        </div>
                        <div class="flex items-center gap-2 mb-2">
                            <span class="text-blue-500 text-2xl font-bold">★</span>
                            <span class="text-2xl font-bold text-blue-800"><%= peliculaMejorValorada.getNota() != null ? String.format("%.1f", peliculaMejorValorada.getNota()) : "-" %></span>
                            <span class="text-gray-500 text-base">(<%= peliculaMejorValorada.getVotos() != null ? peliculaMejorValorada.getVotos() : "0" %> votos)</span>
                        </div>
                        <p class="text-gray-700 text-base mb-2 line-clamp-4"><%= peliculaMejorValorada.getDescripcion() != null ? peliculaMejorValorada.getDescripcion() : "" %></p>
                        <% if (peliculaMejorValorada.getGeneroPeliculaList() != null) { %>
                        <div class="flex flex-wrap gap-2 mt-2">
                            <% for (GeneroPelicula genero : peliculaMejorValorada.getGeneroPeliculaList()) { %>
                                <span class="bg-blue-100 text-blue-800 font-semibold rounded-full px-3 py-1 text-xs shadow-sm"><%= genero.getGeneronombre() %></span>
                            <% } %>
                        </div>
                        <% } %>
                    </div>
                </section>
                <% } else { %>
                <div class="text-center text-gray-400 mt-20 text-xl">No hay película mejor valorada disponible.</div>
                <% } %>
            </div>
        </div>
    </div>
    <script>
        const peliculas = [
            <% if (peliculas != null && !peliculas.isEmpty()) {
                for (int i = 0; i < peliculas.size(); i++) {
                    Pelicula peli = peliculas.get(i);
            %>
            {
                id: "<%= peli.getId() %>",
                urlcaratula: "<%= peli.getUrlcaratula() %>",
                titulooriginal: "<%= peli.getTitulooriginal().replace("\"", "\\\"") %>",
                nota: "<%= peli.getNota() != null ? String.format("%.1f", peli.getNota()) : "-" %>",
                genero: "<%= (peli.getGeneroPeliculaList() != null && !peli.getGeneroPeliculaList().isEmpty()) ? peli.getGeneroPeliculaList().get(0).getGeneronombre() : "-" %>"
            }<%= (i < peliculas.size() - 1) ? "," : "" %>
            <% }} %>
        ];
        let current = 0;
        const img = document.getElementById('carousel-img');
        const title = document.getElementById('carousel-title');
        const nota = document.getElementById('carousel-nota');
        const genero = document.getElementById('carousel-genero');
        const link = document.getElementById('carousel-link');
        document.getElementById('carousel-prev').onclick = function() {
            if (peliculas.length === 0) return;
            current = (current - 1 + peliculas.length) % peliculas.length;
            updateCarousel();
        };
        document.getElementById('carousel-next').onclick = function() {
            if (peliculas.length === 0) return;
            current = (current + 1) % peliculas.length;
            updateCarousel();
        };
        function updateCarousel() {
            if (peliculas.length === 0) return;
            img.src = peliculas[current].urlcaratula;
            title.textContent = peliculas[current].titulooriginal;
            nota.textContent = peliculas[current].nota;
            genero.textContent = peliculas[current].genero;
            link.href = "/<%= usuario.getRol() %>/pelicula?id=" + peliculas[current].id;
        }
    </script>
</body>
</html>