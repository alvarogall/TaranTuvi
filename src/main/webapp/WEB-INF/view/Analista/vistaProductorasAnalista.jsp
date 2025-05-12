<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ProductoraEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PaisRodajeEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.DepartamentoEntity" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: ALEJANDRO
  Date: 11/05/2025
  Time: 1:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ranking Productoras</title>
    <link rel="stylesheet" href="/css/Analista/tablasAnalista.css">
</head>
<body>

<jsp:include page="analistaCabecera.jsp"/>

<div class="stats-container">

<div class="card-table">
<h3>Películas por Productoras</h3>
<table>
    <tr>
        <th>Productora</th>
        <th>Nº Películas</th>
        <th>Porcentaje Películas</th>
        <th>Nota Media</th>
        <th>Presupuesto Medio</th>
        <th>Recaudación Media</th>
    </tr>
    <%
        List<Object[]> productorasListaNotaMedia = (List<Object[]>) request.getAttribute("productorasNotaMedia");
        int totalPeliculas = (int) request.getAttribute("totalPeliculas");
        for(Object[] objectoProductora : productorasListaNotaMedia){
            ProductoraEntity productora = (ProductoraEntity) objectoProductora[0];
            Long porcentaje = (long) ((productora.getPeliculaList().size()*100)/totalPeliculas);
    %>
    <tr>
        <td><%=productora.getProductoranombre()%></td>
        <td><%=productora.getPeliculaList().size()%></td>
        <td><%=porcentaje + "%"%></td>
        <td><%= (objectoProductora[1] == null) ? "No Valorada" : String.format("%.2f", objectoProductora[1]) %></td>
        <td><%= (objectoProductora[2] == null) ? "No Presupuesto" : String.format("%.0f", objectoProductora[2]) %></td>
        <td><%= (objectoProductora[3] == null) ? "No Recaudación" : String.format("%.0f", objectoProductora[3]) %></td>
    </tr>
    <%
        }
    %>
</table>
</div>

<div class="card-table">
<h3>Películas por País</h3>
<table>
    <tr>
        <th>País Rodaje</th>
        <th>Cantidad</th>
        <th>Porcentaje</th>
    </tr>
    <%
        List<PaisRodajeEntity> paisRodajeLista = (List<PaisRodajeEntity>) request.getAttribute("paisesRodaje");
        int totalPaisesRodaje = (int) request.getAttribute("totalPaisesRodaje");
        for(PaisRodajeEntity paisRodaje: paisRodajeLista){
            Long porcentaje = (long) ((paisRodaje.getPeliculaList().size()*100)/totalPaisesRodaje);
    %>
    <tr>
        <td><%=paisRodaje.getPaisrodajenombre()%></td>
        <td><%=paisRodaje.getPeliculaList().size()%></td>
        <td><%=porcentaje + "%"%></td>
    </tr>
    <%
        }
    %>
</table>
</div>

<div class="card-table">
<h3>Trabajadores por Departamento</h3>
<table>
    <tr>
        <th>Departamento</th>
        <th>Cantidad</th>
        <th>Porcentaje</th>
    </tr>
    <%
        List<DepartamentoEntity> departamentoLista = (List<DepartamentoEntity>) request.getAttribute("departamentos");
        int totalTrabajadores = (int) request.getAttribute("totalTrabajadores");
        for(DepartamentoEntity departamento: departamentoLista){
            Long porcentaje = (long) ((departamento.getTrabajoList().size()*100)/totalTrabajadores);
    %>
    <tr>
        <td><%=departamento.getDepartamentonombre()%></td>
        <td><%=departamento.getTrabajoList().size()%></td>
        <td><%=porcentaje + "%"%></td>
    </tr>
    <%
        }
    %>
</table>
</div>

</div>

</body>
</html>
