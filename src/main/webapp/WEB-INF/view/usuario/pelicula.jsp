<%-- @author Álvaro Gallardo --%>

<%@ page import="es.uma.taw.tarantuvi.dto.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Película</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
    Valoracion valoracion = (Valoracion) request.getAttribute("valoracion");
%>
<body class="bg-gray-100 font-sans m-0 min-h-screen flex flex-col">
<jsp:include page="navBar.jsp"/>
<% if (pelicula != null) { %>
<main class="flex-1 w-full flex flex-col items-center px-4 sm:px-6 lg:px-8">
    <!-- Detalles de la película -->
    <div class="flex justify-center items-start py-12 w-full max-w-4xl">
        <div class="flex flex-col md:flex-row bg-white border border-gray-200 rounded-2xl w-full min-h-[340px] p-8 shadow-lg gap-10">
            <div class="flex-shrink-0 flex justify-center items-start">
                <img src="<%= pelicula.getUrlcaratula() %>" alt="Carátula de <%= pelicula.getTitulooriginal() %>"
                     class="w-[180px] h-[270px] object-cover rounded-lg border border-gray-200 bg-white shadow-md" />
            </div>
            <div class="flex-1 flex flex-col min-w-0">
                <h1 class="text-4xl font-extrabold mb-2 text-gray-900 leading-tight truncate"><%= pelicula.getTitulooriginal() %></h1>
                <p class="text-blue-600 italic mb-3 text-xl max-w-prose"><%= pelicula.getEslogan() != null ? pelicula.getEslogan() : "" %></p>
                <div class="flex flex-wrap items-center gap-4 text-gray-600 text-lg mb-6 font-medium">
                    <span><%= pelicula.getFechaestreno() != null ? pelicula.getFechaestreno().toString() : "" %></span>
                    <span class="text-gray-400">&bull;</span>
                    <span><%= pelicula.getDuracion() != null ? pelicula.getDuracion() + " min" : "" %></span>
                    <span class="text-gray-400">&bull;</span>
                    <span><%= pelicula.getEstado() != null ? pelicula.getEstado() : "" %></span>
                </div>
                <div class="mb-6 flex flex-wrap gap-3">
                    <% if (pelicula.getGeneroPeliculaList() != null) {
                        for (GeneroPelicula generoPelicula : pelicula.getGeneroPeliculaList()) { %>
                    <span class="bg-blue-100 text-blue-800 font-semibold rounded-full px-4 py-1 text-sm shadow-sm whitespace-nowrap">
                        <%= generoPelicula.getGeneronombre() %>
                    </span>
                    <% }} %>
                </div>
                <p class="mb-8 text-gray-700 text-lg leading-relaxed max-w-prose"><%= pelicula.getDescripcion() != null ? pelicula.getDescripcion() : "" %></p>
                <dl class="grid grid-cols-1 sm:grid-cols-2 gap-x-8 gap-y-6 text-gray-800 text-base">
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Idioma original</dt>
                        <dd><%= pelicula.getIdiomaoriginalhablado() != null ? pelicula.getIdiomaoriginalhablado().getIdiomahabladonombre() : "-" %></dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Idiomas hablados</dt>
                        <dd>
                            <% if (pelicula.getIdiomaHabladoList() != null && !pelicula.getIdiomaHabladoList().isEmpty()) {
                                for (int i = 0; i < pelicula.getIdiomaHabladoList().size(); i++) { %>
                                    <%= pelicula.getIdiomaHabladoList().get(i).getIdiomahabladonombre() %><%= (i < pelicula.getIdiomaHabladoList().size() - 1) ? ", " : "" %>
                            <%  }
                               } else { %>
                                -
                            <% } %>
                        </dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Países de rodaje</dt>
                        <dd>
                            <% if (pelicula.getPaisRodajeList() != null && !pelicula.getPaisRodajeList().isEmpty()) {
                                for (int i = 0; i < pelicula.getPaisRodajeList().size(); i++) { %>
                                    <%= pelicula.getPaisRodajeList().get(i).getPaisrodajenombre() %><%= (i < pelicula.getPaisRodajeList().size() - 1) ? ", " : "" %>
                            <%  }
                               } else { %>
                                -
                            <% } %>
                        </dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Productoras</dt>
                        <dd>
                            <% if (pelicula.getProductoraList() != null && !pelicula.getProductoraList().isEmpty()) {
                                for (int i = 0; i < pelicula.getProductoraList().size(); i++) { %>
                                    <%= pelicula.getProductoraList().get(i).getProductoranombre() %><%= (i < pelicula.getProductoraList().size() - 1) ? ", " : "" %>
                            <%  }
                               } else { %>
                                -
                            <% } %>
                        </dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Presupuesto</dt>
                        <dd><%= pelicula.getPresupuesto() != null ? pelicula.getPresupuesto() + " €" : "-" %></dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Recaudación</dt>
                        <dd><%= pelicula.getRecaudacion() != null ? pelicula.getRecaudacion() + " €" : "-" %></dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Popularidad</dt>
                        <dd><%= pelicula.getPopularidad() != null ? pelicula.getPopularidad() : "-" %></dd>
                    </div>
                    <div>
                        <dt class="font-semibold text-gray-900 mb-1">Nota</dt>
                        <dd>
                            <%= pelicula.getNota() != null ? String.format("%.1f", pelicula.getNota()) + " / 10" : "-" %>
                            <span class="text-gray-500 text-sm">(<%= pelicula.getVotos() != null ? pelicula.getVotos() : "0" %> votos)</span>
                        </dd>
                    </div>
                    <div class="sm:col-span-2">
                        <dt class="font-semibold text-gray-900 mb-1">Página web</dt>
                        <dd>
                            <% if (pelicula.getPaginaweb() != null && !pelicula.getPaginaweb().isEmpty()) { %>
                            <a href="<%= pelicula.getPaginaweb() %>" class="text-blue-600 underline hover:text-blue-800 transition break-all">
                                <%= pelicula.getPaginaweb() %>
                            </a>
                            <% } else { %>
                            <span class="text-gray-400">-</span>
                            <% } %>
                        </dd>
                    </div>
                </dl>
                <div class="mt-8">
                    <form method="post" action="/usuario/pelicula/valorar" class="flex flex-col items-start gap-3 w-full max-w-xs">
                        <input type="hidden" name="id" value="<%= pelicula.getId() %>"/>
                        <label class="font-semibold text-gray-800 mb-1">Valora esta película:</label>
                        <input type="hidden" name="nota" id="nota" value="<%= (valoracion != null && valoracion.getNota() != null) ? valoracion.getNota() : "" %>"/>
                        <div class="flex gap-2">
                            <% for (int i = 0; i <= 10; i++) { %>
                                <button type="button"
                                        class="nota-btn w-10 h-10 rounded-full border-2 border-blue-400 text-blue-700 font-bold text-lg bg-white hover:bg-blue-100 transition<%= (valoracion != null && valoracion.getNota() != null && valoracion.getNota().intValue() == i) ? " bg-blue-500 text-white border-blue-700" : "" %>"
                                        data-value="<%= i %>"><%= i %></button>
                            <% } %>
                        </div>
                        <button type="submit" class="mt-2 px-5 py-2 bg-blue-600 text-white rounded-lg font-semibold hover:bg-blue-700 transition">
                            Enviar valoración
                        </button>
                    </form>
                </div>
                <script>
                    const notaBtns = document.querySelectorAll('.nota-btn');
                    const notaInput = document.getElementById('nota');
                    let selectedBtn = null;
                    const notaInicial = notaInput.value;
                    if (notaInicial !== "") {
                        notaBtns.forEach(btn => {
                            if (btn.getAttribute('data-value') === String(notaInicial)) {
                                btn.classList.remove('bg-white', 'text-blue-700', 'border-blue-400');
                                btn.classList.add('bg-blue-500', 'text-white', 'border-blue-700');
                                selectedBtn = btn;
                            }
                        });
                    }
                    notaBtns.forEach(btn => {
                        btn.addEventListener('click', () => {
                            if (selectedBtn) {
                                selectedBtn.classList.remove('bg-blue-500', 'text-white', 'border-blue-700');
                                selectedBtn.classList.add('bg-white', 'text-blue-700', 'border-blue-400');
                            }
                            btn.classList.remove('bg-white', 'text-blue-700', 'border-blue-400');
                            btn.classList.add('bg-blue-500', 'text-white', 'border-blue-700');
                            selectedBtn = btn;
                            notaInput.value = btn.getAttribute('data-value');
                        });
                    });
                </script>
            </div>
        </div>
    </div>
    <% if (pelicula.getActuacionList() != null && !pelicula.getActuacionList().isEmpty()) { %>
    <!-- Reparto -->
    <section class="max-w-4xl w-full mx-auto mt-8 mb-16 px-2">
        <h2 class="text-2xl font-bold text-gray-900 mb-4">Reparto</h2>
        <div class="overflow-x-auto rounded-lg shadow">
            <table class="min-w-full bg-white text-base rounded-lg overflow-hidden">
                <thead>
                <tr>
                    <th class="bg-gray-100 text-blue-600 font-bold px-4 py-3 text-left">Actor</th>
                    <th class="bg-gray-100 text-blue-600 font-bold px-4 py-3 text-left">Personaje</th>
                </tr>
                </thead>
                <tbody>
                <% for (Actuacion actuacion : pelicula.getActuacionList()) {
                    Persona actor = actuacion.getPersonaid();
                %>
                <tr class="border-b last:border-b-0 hover:bg-blue-50 transition">
                    <td class="px-4 py-2">
                        <div class="flex items-center gap-3">
                            <img src="<%= actor.getUrlfoto() != null ? actor.getUrlfoto() : "" %>"
                                 alt="Foto de <%= actor.getNombre() %>"
                                 class="w-8 h-11 object-cover rounded border border-gray-200 bg-white" />
                            <a href="/<%= usuario.getRol() %>/actor?id=<%= actor.getId() %>"
                               class="text-blue-700 font-semibold underline underline-offset-2 decoration-blue-400 hover:text-blue-900 hover:decoration-2 transition-colors duration-150 rounded px-1 py-0.5 focus:outline-none focus:ring-2 focus:ring-blue-300">
                                <%= actor.getNombre() %>
                            </a>
                        </div>
                    </td>
                    <td class="px-4 py-2"><%= actuacion.getPersonaje() != null ? actuacion.getPersonaje() : "" %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
    <% } %>
</main>
<% } %>
</body>
</html>
