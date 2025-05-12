<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: Alejandro Cueto
  Date: 21/04/2025
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ranking Actores</title>
    <link rel = "stylesheet" type = "text/css" href="/css/Analista/tablasAnalista.css">
</head>
<body>

<jsp:include page ="analistaCabecera.jsp"/>

<div class="stats-container" >
    <div class="card-table">
        <h3>Género Global</h3>
        <table>
            <tr>
                <th>Actrices</th>
                <th>Actores</th>
            </tr>
            <%
                List<Object[]> lista2 = (List<Object[]>) request.getAttribute("tasaFemeninaGlobal");
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
        <table>
            <tr>
                <th>Nº Actrices</th>
                <th>Nº Actores</th>
            </tr>
            <%
                List<Object[]> listaNumerosGeneros = (List<Object[]>) request.getAttribute("numeroGeneros");
                for (Object[] actores : listaNumerosGeneros){
            %>
            <tr>
                <td><%=actores[0]%></td>
                <td><%=actores[1]%></td>
            </tr>
            <%
                }
            %>
        </table>

    </div>

    <div class="card-table">
        <h3>Género por Película</h3>
        <table>
            <tr>
                <th>Título</th>
                <th>Actrices</th>
                <th>Actores</th>
            </tr>
            <%
                List<Object[]> lista1 = (List<Object[]>) request.getAttribute("tasaFemenina");
                for (Object[] actuacion : lista1){
            %>
            <tr>
                <td><%=actuacion[0]%></td>
                <td><%=actuacion[1] + "%"%></td>
                <td><%=actuacion[2] + "%"%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

    <div class="card-table">
        <h3>Actores por País</h3>
        <table>
            <tr>
                <th>Nacionalidad</th>
                <th>Cantidad</th>
                <th>Porcentaje</th>
            </tr>
            <%
                List<Object[]> paisesLista = (List<Object[]>) request.getAttribute("tasaNacionalidad");
                Long totalActores = (Long) request.getAttribute("totalActores");
                for(Object[] pais : paisesLista){
                    Long porcentaje = ((Long)pais[1] * 100) / totalActores;
            %>
            <tr>
                <td><%=pais[0]%></td>
                <td><%=pais[1]%></td>
                <td><%=porcentaje + "%"%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

    <div class="card-table">
        <h3>Actores Mejor Película</h3>
        <%
            Object[] mejorPelicula = (Object[]) request.getAttribute("pelicula");
            PeliculaEntity pelicula = (PeliculaEntity) mejorPelicula[0];
        %>
        <table>
            <tr>
                <td><strong>Nombre:</strong></td>
                <td style = "text-align: right"><input style = "text-decoration-color: #7700ff" type="text" disabled value="<%=pelicula.getTitulooriginal()%>"></td>
            </tr>
            <tr>
                <td><strong>Nota Media:</strong></td>
                <td style = "text-align: right"><input type="text" disabled value="<%=mejorPelicula[1]%>"></td>
            </tr>
        </table>
        <br/>
        <table>
            <tr>
                <th>Nombre Actor</th>
                <th>Personaje</th>
            </tr>
            <%
                List<ActuacionEntity> listaActoresMejorPelicula = (List<ActuacionEntity>) request.getAttribute("listaActoresMejorPelicula");
                for(ActuacionEntity actor : listaActoresMejorPelicula){
            %>
            <tr>
                <td><%=actor.getPersonaid().getNombre()%></td>
                <td><%=actor.getPersonaje()%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>

</body>
</html>