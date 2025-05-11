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
    <title>Ranking Valoraciones</title>
    <link rel="stylesheet" href="/css/Analista/graficasValoracionesAnalista.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="analistaCabecera.jsp"/>



<div class="card-table">
    <canvas id="chartGenero"></canvas>
</div>

<table id="tablaGenero" style="display:none;">
    <% for (Object[] fila : (List<Object[]>) request.getAttribute("generosNotaMedia")) { %>
    <tr>
        <td><%= fila[0] %></td>
        <td><%= fila[1] %></td>
    </tr>
    <% } %>
</table>

<script>
    //ESTO ES PARA QUE NO SE DESCUADREN LAS GRÁFICAS AL MAXIMIZAR PESTAÑA
    window.addEventListener('resize', () => {
        Chart.helpers.each(Chart.instances, function(chart) {
            if (chart) chart.resize();
        });
    });

    const labels = [
        <% for (Object[] fila : (List<Object[]>) request.getAttribute("generosNotaMedia")) { %>
        "<%= fila[0] %>",
        <% } %>
    ];

    const data = [
        <% for (Object[] fila : (List<Object[]>) request.getAttribute("generosNotaMedia")) { %>
        <%= fila[1] %>,
        <% } %>
    ];

    //ESTÉTICA BARRAS
    new Chart(document.getElementById('chartGenero'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Notas Media por Género',
                data: data,
                backgroundColor: 'rgb(213,0,52)',
                borderColor: 'rgb(138,0,41)',
                borderWidth: 1
            }]
        },

        //TAMAÑO BARRAS Y ETIQUETAS
        options: {
            responsive: true,
            plugins: {
                legend: {
                    labels: {
                        color: 'rgb(0,0,0)',
                        font: {
                            size: 16
                        }
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        font: {
                            size: 9
                        },
                        color: 'rgb(0,0,0)',
                        maxRotation: 90,
                        minRotation: 0
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1,
                        precision: 0
                    }
                }
            },
            barThickness: 6,
        }
    });
</script>
</body>
</html>
