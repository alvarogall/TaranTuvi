<%-- @author Álvaro Gallardo --%>

<%@ page import="es.uma.taw.tarantuvi.dto.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Perfil de Usuario</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    long creationTime = session.getCreationTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String fechaAcceso = sdf.format(new java.util.Date(creationTime));
%>
<body class="bg-gray-100 font-sans m-0 min-h-screen">
    <jsp:include page="navBar.jsp"/>
    <div class="max-w-xl mx-auto mt-12">
        <div class="bg-white rounded-xl shadow-lg p-8 border border-gray-200">
            <div class="flex items-center mb-6">
                <div class="w-16 h-16 rounded-full bg-blue-100 flex items-center justify-center text-3xl text-blue-600 font-bold mr-5 shadow">
                    <%= usuario != null && usuario.getUsuario() != null && !usuario.getUsuario().isEmpty() ? usuario.getUsuario().substring(0,1).toUpperCase() : "U" %>
                </div>
                <div>
                    <div class="text-2xl font-bold text-gray-800 mb-1"><%= usuario != null ? usuario.getUsuario() : "Usuario" %></div>
                    <div class="text-sm text-gray-500">Rol: <span class="font-semibold text-blue-600"><%= usuario != null ? usuario.getRol() : "" %></span></div>
                </div>
            </div>
            <div class="mb-4">
                <div class="flex items-center mb-2">
                    <span class="material-icons text-blue-400 mr-2">schedule</span>
                    <span class="text-gray-700 font-semibold">Sesión iniciada:</span>
                    <span class="ml-2 text-gray-600"><%= fechaAcceso %></span>
                </div>
            </div>
            <form action="/logout" method="get" class="mt-8">
                <button type="submit"
                        class="w-full py-3 bg-red-600 hover:bg-red-700 text-white font-bold rounded-lg shadow transition text-lg">
                    Cerrar sesión
                </button>
            </form>
        </div>
    </div>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</body>
</html>