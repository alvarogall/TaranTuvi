<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 03/05/2025
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio Administrador</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/inicioEditor.css">
</head>
<body>
  <jsp:include page="navBarAdministrador.jsp">
    <jsp:param name="activePage" value="inicio"/>
  </jsp:include>

  <div class="welcome-container">
    <h1>Bienvenido, Administrador 👋</h1>
    <p>Como admistrador, tienes acceso a las siguientes funciones:</p>

    <ul class="features-list">
      <li>➕ Añadir nuevos géneros y productoras.</li>
      <li>✏️ Editar información existente.</li>
      <li>🗑️ Eliminar registros obsoletos o incorrectos.</li>
      <li>🔍 Buscar por nombre o filtrar alfabéticamente.</li>
    </ul>
  </div>

</body>
</html>
