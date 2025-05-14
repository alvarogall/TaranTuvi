<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>

<%--
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
    <div class="tabla-pequena-Actor">
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

        <form:form method="get" modelAttribute="orden" action="/analista/actores" cssClass="filtro-formulario">
            <label for="ordenCampo">Ordenar por:</label>
            <form:select path="ordenCampo" id="ordenCampo">
                <form:option value="">-- Seleccionar --</form:option>
                <form:option value="titulo">Título</form:option>
                <form:option value="actrices">% Actrices</form:option>
                <form:option value="actores">% Actores</form:option>
            </form:select>

            <label for="ordenTipo"></label>
            <form:select path="ordenTipo" id="ordenTipo">
                <form:option value="ASC">Ascendente</form:option>
                <form:option value="DESC">Descendente</form:option>
            </form:select>
            <button type="submit">Aplicar</button>
        </form:form>

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

        <form:form method="get" modelAttribute="orden" action="/analista/actores" cssClass="filtro-formulario">
            <label for="ordenCampoAuxiliar">Ordenar por</label>
            <form:select path="ordenCampoAuxiliar" id="ordenCampoAuxiliar">
                <form:option value="">-- Seleccionar --</form:option>
                <form:option value="nacionalidad">Nacionalidad</form:option>
                <form:option value="cantidad">Cantidad</form:option>
            </form:select>

            <label for="ordenTipoAuxiliar"></label>
            <form:select path="ordenTipoAuxiliar" id="ordenTipoAuxiliar">
                <form:option value="ASC">Ascendente</form:option>
                <form:option value="DESC">Descendente</form:option>
            </form:select>
            <button type="submit">Aplicar</button>
        </form:form>

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
            List<Object[]> mejorPeliculaLista = (List<Object[]>) request.getAttribute("pelicula");
            Object[] peliculaObjeto = (Object[]) mejorPeliculaLista.get(0);
            PeliculaEntity pelicula = (PeliculaEntity) peliculaObjeto[0];
        %>
        <table>
            <tr>
                <td><strong>Nombre:</strong></td>
                <td style = "text-align: right"><input style = "text-decoration-color: #7700ff" type="text" disabled value="<%=pelicula.getTitulooriginal()%>"></td>
            </tr>
            <tr>
                <td><strong>Nota Media:</strong></td>
                <td style = "text-align: right"><input type="text" disabled value="<%=peliculaObjeto[1]%>"></td>
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