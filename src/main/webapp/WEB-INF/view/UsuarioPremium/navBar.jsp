<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<script src="https://cdn.tailwindcss.com"></script>

<nav class="flex justify-between items-center bg-blue-600 px-8 h-14">
    <div class="flex items-center gap-4">
        <span class="text-white text-xl font-bold mr-6 tracking-wide">TaranTuvi</span>
        <a href="<%= "/" + usuario.getRol() + "/" %>"
           class="text-white text-base px-3 py-2 rounded hover:bg-white/20 transition">
            Inicio
        </a>
        <a href="<%= "/" + usuario.getRol() + "/pelicula/listar" %>"
           class="text-white text-base px-3 py-2 rounded hover:bg-white/20 transition">
            Películas
        </a>
        <a href="<%= "/" + usuario.getRol() + "/actor/listar" %>"
           class="text-white text-base px-3 py-2 rounded hover:bg-white/20 transition">
            Actores
        </a>
    </div>
    <div class="flex items-center">
        <a href="<%= "/" + usuario.getRol() + "/perfil" %>"
           class="flex items-center bg-blue-50 text-blue-600 font-semibold rounded-full px-5 py-2 ml-5 text-base shadow-sm hover:bg-blue-100 hover:shadow-md transition"
           title="Perfil">
            <span class="uppercase tracking-wide font-sans text-sm">
                <%= usuario.getRol() %>
            </span>
        </a>
    </div>
</nav>
