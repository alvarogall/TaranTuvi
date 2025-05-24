<%--
  AUTOR: ALEJANDRO CUETO DÍAZ
  Created by IntelliJ IDEA.
  User: ALEJANDRO
  Date: 11/05/2025
  Time: 1:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="es.uma.taw.tarantuvi.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ranking Valoraciones</title>
    <link rel="stylesheet" href="/css/Analista/graficasAnalista.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="analistaCabecera.jsp"/>

<div class="main-container">
    <div class="left-panel card-table">
        <h3>Top 10 Mejores Películas Valoradas</h3>
        <table>
            <thead>
            <tr>
                <th colspan="2"></th>
                <th>Título</th>
                <th>Nota Media</th>
                <th>Nº Valoraciones</th>
                <th>Géneros</th>
                <th>Eslogan</th>
                <th>Web</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Object[]> topPeliculas = (List<Object[]>) request.getAttribute("topPeliculas");
                if (topPeliculas != null) {
                    for (Object[] fila : topPeliculas) {
                        PeliculaEntity pelicula = (PeliculaEntity) fila[0];
                        Double notaMedia = (Double) fila[1];
                        Long numValoraciones = (Long) fila[2];
                        String urlImagen = (pelicula.getUrlcaratula() != null) ? pelicula.getUrlcaratula() : null;
            %>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <% if (urlImagen != null) { %>
                    <img src="<%= urlImagen %>" alt="Carátula" style="width: 60px; height: 80px;">
                    <% } else { %>
                    <span>Sin imagen</span>
                    <% } %>
                </td>
                <td><%= pelicula.getTitulooriginal() %></td>
                <td><%= notaMedia != null ? String.format("%.2f", notaMedia) : "N/A" %></td>
                <td><%= numValoraciones %></td>
                <td>
                    <%
                        for (int i = 0; i < pelicula.getGeneroPeliculaList().size(); i++) {
                            out.print(pelicula.getGeneroPeliculaList().get(i).getGeneronombre());
                            if (i < pelicula.getGeneroPeliculaList().size() - 1) {
                                out.print(" / ");
                            }
                        }
                    %>
                </td>
                <td><%= pelicula.getEslogan() %></td>
                <td><a href="<%=pelicula.getPaginaweb()%>">Link</a></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="right-panel">

        <div class="card-table">
            <canvas id="chartGenero"></canvas>
        </div>

        <div class="card-table">
            <canvas id="graficaCircularGenero"></canvas>
        </div>

    </div>

</div>






<table id="tablaNotaMediaGenero" style="display:none;">
    <% for (Object[] fila : (List<Object[]>) request.getAttribute("generosNotaMedia")) { %>
    <tr>
        <td><%= fila[0] %></td> <!-- generoId -->
        <td><%= fila[1] %></td> <!-- Nombre -->
        <td><%= fila[2] %></td> <!-- notaMedia -->
    </tr>
    <% } %>
</table>

<table id="tablaNumeroValoracionesGenero" style="display:none;">
    <% for (Object[] fila : (List<Object[]>) request.getAttribute("generosNumValoraciones")) { %>
    <tr>
        <td><%= fila[0] %></td> <!-- generoId -->
        <td><%= fila[1] %></td> <!-- Nombre -->
        <td><%= fila[2] %></td> <!-- numeroValoraciones -->
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


    //Nota Media Por Géneros
    const filasNota = document.querySelectorAll("#tablaNotaMediaGenero tr");
    const labelsNota = [];
    const dataNota = [];
    const colorsNota = [];

    filasNota.forEach(fila => {
        const generoId = parseInt(fila.children[0].innerText.trim());
        const generoNombre = (fila.children[1].innerText.trim());
        const notaMedia = parseFloat(fila.children[2].innerText.trim());
        labelsNota.push(generoNombre);
        dataNota.push(notaMedia);
        colorsNota.push(generoIdToColor(generoId));
    });

    new Chart(document.getElementById('chartGenero'), {
        type: 'bar',
        data: {
            labels: labelsNota,
            datasets: [{
                label: 'Nota Media por Género',
                data: dataNota,
                backgroundColor: colorsNota
            }]
        },
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
            barThickness: 10,
        }
    });



    //Número de Valoraciones por Géneros
    const filasValoraciones = document.querySelectorAll("#tablaNumeroValoracionesGenero tr");
    const labelsValoraciones = [];
    const dataValoraciones = [];
    const colorsValoraciones = [];

    filasValoraciones.forEach(fila => {
        const generoId = parseInt(fila.children[0].innerText.trim());
        const generoNombre = (fila.children[1].innerText.trim());
        const numeroValoraciones = parseInt(fila.children[2].innerText.trim());
        labelsValoraciones.push(generoNombre);
        dataValoraciones.push(numeroValoraciones);
        colorsValoraciones.push(generoIdToColor(generoId));
    });

    new Chart(document.getElementById('graficaCircularGenero'), {
        type: 'pie',
        data: {
            labels: labelsValoraciones,
            datasets: [{
                label: 'Valoraciones por Género',
                data: dataValoraciones,
                backgroundColor: colorsValoraciones
            }]
        },
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
        }
    });

    function generoIdToColor(id) {
        const hue = (id * 80) % 360;
        return `hsl(` + hue + `, 70%, 60%)`;
    }
</script>
</body>
</html>
