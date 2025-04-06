<%@ page import="es.uma.taw.tarantuvi.entity.PruebaEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ideapad
  Date: 6/4/25
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hola</title>
</head>
<%
    List<PruebaEntity> lista = (List<PruebaEntity>) request.getAttribute("prueba");
%>
<body>
    <table border="1">
        <th>ID</th>
        <th>TEXTO</th>
        <th>NUMERO</th>

        <%
            for(PruebaEntity prueba : lista) {
        %>
        <tr>
            <td><%= prueba.getId() %></td>
            <td><%= prueba.getTexto() %></td>
            <td><%= prueba.getNumero() %></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
