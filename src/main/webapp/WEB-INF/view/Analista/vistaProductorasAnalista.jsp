<%--
  AUTOR: ALEJANDRO CUETO DÍAZ
  Created by IntelliJ IDEA.
  User: ALEJANDRO
  Date: 11/05/2025
  Time: 1:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Departamento" %>
<%@ page import="es.uma.taw.tarantuvi.dto.PaisRodaje" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Productora" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ranking Productoras</title>
    <link rel="stylesheet" href="/css/Analista/tablasAnalista.css">
</head>
<body>

<jsp:include page="analistaCabecera.jsp"/>

<div class="stats-container">

    <div class="left-panelProductoras">
        <div class="card-table">
        <h3>Películas por Productoras</h3>

        <form:form method="get" modelAttribute="orden" action="/analista/productoras" cssClass="filtro-formulario-Productora">
            <div class="divisionSelect">
            <label for="ordenCampo">Ordenar por:</label>
            <form:select path="ordenCampo" id="ordenCampo">
                <form:option value="">-- Seleccionar --</form:option>
                <form:option value="productora">Productoras</form:option>
                <form:option value="peliculas">Nº Películas</form:option>
                <form:option value="nota">Nota Media</form:option>
                <form:option value="presupuesto">Presupuesto Medio</form:option>
                <form:option value="recaudacion">Recaudación Media</form:option>
            </form:select>

            <label for="ordenTipo">Tipo de Orden:</label>
            <form:select path="ordenTipo">
                <form:option value="ASC">Ascendente</form:option>
                <form:option value="DESC">Descendente</form:option>
            </form:select>

                <label for="cantidadMinima">Cantidad Mínima</label>
                <form:input path="cantidadMinima" id="cantidadMinima" type="number" min="0" placeholder="€" cssClass="input-pequeno-Productora" disabled="true"/>

                <label for="cantidadMaxima">Cantidad Máxima</label>
                <form:input path="cantidadMaxima" id="cantidadMaxima" type="number" min="0" placeholder="€" cssClass="input-pequeno-Productora" disabled="true"/>
            </div>
            <button type="submit">Aplicar</button>
        </form:form>

        <table>
        <tr>
        <th>Productora</th>
        <th>Nº Películas</th>
        <th>% Películas</th>
        <th>Nota Media</th>
        <th>Presupuesto Medio</th>
        <th>Recaudación Media</th>
        </tr>
        <%
        List<Productora> productorasListaNotaMedia = (List<Productora>) request.getAttribute("productorasNotaMedia");
        int totalPeliculas = (int) request.getAttribute("totalPeliculas");
        for(Productora productora : productorasListaNotaMedia){
            Long porcentaje = (long) ((productora.getNumeroPeliculas()*100)/totalPeliculas);
        %>
        <tr>
        <td><%=productora.getProductoranombre()%></td>
        <td><%=productora.getNumeroPeliculas()%></td>
        <td><%=porcentaje + "%"%></td>
        <td><%= (productora.getNotaMedia() == 0.0) ? "No Valorada" : String.format("%.2f", productora.getNotaMedia()) %></td>
        <td><%= (productora.getPresupuestoMedio() == 0.0) ? "No Presupuesto" : String.format("%.0f", productora.getPresupuestoMedio() ) %></td>
        <td><%= (productora.getRecaudacionMedia() == 0.0) ? "No Recaudación" : String.format("%.0f", productora.getRecaudacionMedia()) %></td>
        </tr>
        <%
        }
        %>
        </table>
        </div>
    </div>

    <div class="right-panelProductoras">
        <div class="card-table">
            <h3>Trabajadores por Departamento</h3>
            <table>
                <tr>
                    <th>Departamento</th>
                    <th>Cantidad</th>
                    <th>Porcentaje</th>
                </tr>
                <%
                    List<Departamento> departamentoLista = (List<Departamento>) request.getAttribute("departamentos");
                    int totalTrabajadores = (int) request.getAttribute("totalTrabajadores");
                    for(Departamento departamento: departamentoLista){
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

        <div class="card-table">
            <h3>Películas por País</h3>

            <form:form method="get" modelAttribute="orden" action="/analista/productoras" cssClass="filtro-formulario">
                <label for="ordenCampo">Ordenar por:</label>
                <form:select path="ordenCampoAuxiliar">
                    <form:option value="pais">País Rodaje</form:option>
                    <form:option value="peliculas">Cantidad</form:option>
                </form:select>

                <label for="ordenTipo">Tipo de Orden:</label>
                <form:select path="ordenTipoAuxiliar">
                    <form:option value="ASC">Ascendente</form:option>
                    <form:option value="DESC">Descendente</form:option>
                </form:select>

                <button type="submit">Aplicar</button>
            </form:form>

            <table>
                <tr>
                    <th>País Rodaje</th>
                    <th>Cantidad</th>
                    <th>Porcentaje</th>
                </tr>
                <%
                    List<PaisRodaje> paisRodajeLista = (List<PaisRodaje>) request.getAttribute("paisesRodaje");
                    int totalPaisesRodaje = (int) request.getAttribute("totalPaisesRodaje");
                    for(PaisRodaje paisRodaje: paisRodajeLista){
                        Long porcentaje = (long) ((paisRodaje.getNumeroPeliculas()*100)/totalPaisesRodaje);
                %>
                <tr>
                    <td><%=paisRodaje.getPaisrodajenombre()%></td>
                    <td><%=paisRodaje.getNumeroPeliculas()%></td>
                    <td><%=porcentaje + "%"%></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>

<%//SCRIPT PARA INHABILITAR INPUT CANTIDAD SIN SELECCIONAR PRESUPUESTO / RECAUDACIÓN%>
<script>
    window.addEventListener('DOMContentLoaded', () => {
        const select = document.getElementById('ordenCampo');
        const minInput = document.getElementById('cantidadMinima');
        const maxInput = document.getElementById('cantidadMaxima');

        function toggleInputs() {
            const ordenCampo = select.value;
            const activar = ordenCampo === 'presupuesto' || ordenCampo === 'recaudacion';
            minInput.disabled = !activar;
            maxInput.disabled = !activar;
        }

        select.addEventListener('change', toggleInputs);

        toggleInputs();
    });
</script>


</body>
</html>
