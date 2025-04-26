<%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 08/04/2025
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio</title>
    <link rel="stylesheet" type="text/css" href="/css/inicioEditor.css">
</head>
<body>
<body>
<jsp:include page="../navBarNormal.jsp">
    <jsp:param name="activePage" value="inicio"/>
</jsp:include>

<div class="welcome-container">
    <h1>Bienvenido, Editor 👋</h1>
    <p>Desde esta plataforma puedes gestionar el contenido audiovisual de TaranTuvi.</p>
    <p>Como editor, tienes acceso a las siguientes funciones:</p>

    <ul class="features-list">
        <li>➕ Añadir nuevas películas y actores.</li>
        <li>✏️ Editar información existente (como títulos, géneros, personajes...).</li>
        <li>🗑️ Eliminar registros obsoletos o incorrectos.</li>
        <li>🔍 Buscar por nombre o filtrar alfabéticamente.</li>
    </ul>
</div>

</body>
</html>

