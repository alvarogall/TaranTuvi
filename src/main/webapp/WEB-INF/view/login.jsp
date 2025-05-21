<%-- @author Álvaro Gallardo --%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    String error = (String) request.getAttribute("error");
%>
<body class="min-h-screen flex items-center justify-center bg-gray-100">
    <div class="bg-white shadow-lg rounded-lg px-8 py-10 w-full max-w-md">
        <h1 class="text-3xl font-bold text-blue-600 mb-6 text-center">Hola, bienvenido</h1>
        <form:form method="post" modelAttribute="usuario" action="/autenticar" class="space-y-5">
            <form:input path="usuario"
                        class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400 text-lg"
                        placeholder="Usuario" />
            <form:password path="password"
                           class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400 text-lg"
                           placeholder="Contraseña" />
            <form:button type="submit"
                         class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 rounded transition duration-200">
                Entrar
            </form:button>
        </form:form>
        <p class="text-red-600 mt-4 text-center text-sm min-h-[1.5em]">
            <%= (error != null) ? error : "" %>
        </p>
    </div>
</body>
</html>
