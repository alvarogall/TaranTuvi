<%-- @author Álvaro Gallardo --%>

<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String activePage = request.getParameter("activePage");
%>
<script src="https://cdn.tailwindcss.com"></script>
<nav class="flex justify-between items-center bg-black px-8 h-14 shadow">
    <div class="flex items-center gap-1">
        <span class="text-2xl font-bold bg-gradient-to-r from-blue-400 via-blue-600 to-cyan-400 bg-clip-text text-transparent tracking-wide mr-1 animate-gradient-move select-none">
            TaranTuvi
        </span>
        <span class="text-xl animate-bounce select-none ml-0.5">🎬</span>
    </div>
    <div class="flex items-center gap-8">
        <a href="<%= "/" + usuario.getRol() + "/" %>"
           class="text-white text-base px-3 py-2 rounded font-medium transition
           <%= "inicio".equals(activePage) ? "bg-blue-700/80" : "hover:bg-blue-700/60" %>">
            Inicio
        </a>
        <a href="<%= "/" + usuario.getRol() + "/pelicula/listar" %>"
           class="text-white text-base px-3 py-2 rounded font-medium transition
           <%= "peliculas".equals(activePage) ? "bg-blue-700/80" : "hover:bg-blue-700/60" %>">
            Películas
        </a>
        <a href="<%= "/" + usuario.getRol() + "/actor/listar" %>"
           class="text-white text-base px-3 py-2 rounded font-medium transition
           <%= "actores".equals(activePage) ? "bg-blue-700/80" : "hover:bg-blue-700/60" %>">
            Actores
        </a>
        <a href="<%= "/" + usuario.getRol() + "/perfil" %>"
           class="flex items-center bg-blue-50 text-black font-semibold rounded-full px-5 py-2 ml-6 text-base shadow-sm hover:bg-blue-100 hover:shadow-md transition"
           title="Perfil">
            <span class="uppercase tracking-wide font-sans text-sm">
                <%= usuario.getRol() %>
            </span>
        </a>
    </div>
</nav>
<style>
    @keyframes gradient-move {
        0% { background-position: 0% center; }
        50% { background-position: 100% center; }
        100% { background-position: 0% center; }
    }
    .animate-gradient-move {
        background-size: 200% auto;
        animation: gradient-move 5s ease-in-out infinite;
    }
</style>