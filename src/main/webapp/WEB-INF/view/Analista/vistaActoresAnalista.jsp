<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Alejandro Cueto
  Date: 21/04/2025
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel = "stylesheet" type = "text/css" href="/css/inicioAnalista.css">
</head>
<body>
<nav class="navbar">
    <div class="logo">
        <span class="logo-text">TaranTuvi</span>
        <span class="logo-icon">🎬</span>
    </div>
    <div class="nav-right">
        <ul class="nav-links">
            <li><a href="/analista">Inicio</a></li>
            <li><a href="/analista/ranking" >Películas</a></li>
            <li><a href="/analista/actores" class="active">Actores</a></li>
        </ul>
        <div class="profile">
            <img src="/img/imagenPerfilAnalista.jpg" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
        </div>
    </div>
</nav>

<h2>Estadísticas Género Por Película</h2>
<table border = 1>
    <tr>
        <th>#</th>
        <th>Título</th>
        <th>Actrices</th>
        <th>Actores</th>
    </tr>
    <%
        List<Object[]> lista1 = (List<Object[]>) request.getAttribute("tasaFemenina");
        int index = 1;
        for (Object[] actuacion : lista1){
    %>
        <tr>
            <td><%=index++%></td>
            <td><%=actuacion[0]%></td>
            <td><%=actuacion[1] + "%"%></td>
            <td><%=actuacion[2] + "%"%></td>
        </tr>
    <%
        }
    %>
</table>

<h2>Estadísticas Género Global</h2>
<table border = 1>
    <tr>
        <th>Actrices</th>
        <th>Actores</th>
    </tr>
    <%
        List<Object[]> lista2 = (List<Object[]>) request.getAttribute("tasaFemeninaGlobal");
        index = 1;
        for (Object[] actuacion : lista2){
    %>
        <tr>
            <td><%=actuacion[0] + "%"%></td>
            <td><%=actuacion[1] + "%"%></td>
        </tr>
    <%
        }
    %>
</table>

<h2>Porcentaje de Actores por País</h2>
<table border="1">
    <tr>
        <th>País</th>
        <th>Cantidad</th>
        <th>Porcentaje</th>
    </tr>
    <c:forEach var="fila" items="${tasaNacionalidad}">
        <tr>
            <td>${fila[0]}</td>
            <td>${fila[1]}</td>
            <td>
                <fmt:formatNumber value="${(fila[1] * 100.0) / totalActores}" maxFractionDigits="2"/>%
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>