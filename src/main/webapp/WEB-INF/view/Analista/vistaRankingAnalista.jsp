<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Ranking Películas</title>
    <link rel="stylesheet" href="/css/Analista/graficasAnalista.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="analistaCabecera.jsp"/>

<div class="main-container">
    <!-- IZQUIERDA: TABLA DE PELÍCULAS -->
    <div class="left-panel card-table">
        <h3>Datos Generales</h3>
        <table>
            <thead>
            <tr>
                <th>Título</th>
                <th>Idioma</th>
                <th>Fecha</th>
                <th>Duración</th>
                <th>Nota</th>
                <th>Web</th>
                <th>Géneros</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Object[]> peliculasLista = (List<Object[]>) request.getAttribute("peliculas");
                for (Object[] peliculaObjeto : peliculasLista) {
                    PeliculaEntity pelicula = (PeliculaEntity) peliculaObjeto[0];
            %>
            <tr>
                <td><%=pelicula.getTitulooriginal()%></td>
                <td><%=pelicula.getIdiomaoriginalhabladoid().getIdiomahabladonombre()%></td>
                <td><%=pelicula.getFechaestreno()%></td>
                <td><%=pelicula.getDuracion()%></td>
                <td><%=peliculaObjeto[1]%></td>
                <td><a href="<%=pelicula.getPaginaweb()%>">Link</a></td>
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
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- DERECHA: FORMULARIO Y GRÁFICAS -->
    <div class="right-panel">
        <!-- Gráfico 1: Películas por Año -->
        <div class="card-table">
        <form:form method="get" modelAttribute="filtro" action="/analista/ranking" class = "card-table h3 " >
            Mostrar los últimos
            <form:input path="anios" type="number" min="1" max="50" style="width: 40px;"/>
            años
            <button type="submit">Actualizar</button>
        </form:form>
            <canvas id="graficoPeliculas"></canvas>
        </div>

        <!-- Gráfico 2: Por Duración -->
        <div class="card-table">
            <canvas id="graficoDuraciones"></canvas>
        </div>

        <!-- Gráfico 3: Por Género -->
        <div class="card-table" style="min-width: 300px">
            <canvas id="graficoGeneros"></canvas>
        </div>
    </div>
</div>

<!-- TABLAS OCULTAS PARA JS -->
<table id="tablaPeliculas" style="display:none;">
    <% for (Object[] peliculaObjeto : peliculasLista) {
        PeliculaEntity pelicula = (PeliculaEntity) peliculaObjeto[0];
        int anio = pelicula.getFechaestreno().getYear();
    %>
    <tr><td><%=anio%></td></tr>
    <% } %>
</table>

<table id="tablaDuraciones" style="display:none;">
    <% for (Object[] peliculaObjeto : peliculasLista) {
        PeliculaEntity pelicula = (PeliculaEntity) peliculaObjeto[0];
    %>
    <tr><td><%=pelicula.getDuracion()%></td></tr>
    <% } %>
</table>

<table id="tablaGeneros" style="display:none;">
    <% for (Object[] peliculaObjeto : peliculasLista) {
        PeliculaEntity pelicula = (PeliculaEntity) peliculaObjeto[0];
        for (int i = 0; i < pelicula.getGeneroPeliculaList().size(); i++) {
            String genero = pelicula.getGeneroPeliculaList().get(i).getGeneronombre();
    %>
    <tr><td><%=genero%></td></tr>
    <% } } %>
</table>

<!-- SCRIPTS PARA LOS GRÁFICOS -->
<script>
    //ESTO ES PARA QUE NO SE DESCUADREN LAS GRÁFICAS AL MAXIMIZAR PESTAÑA
    window.addEventListener('resize', () => {
        Chart.helpers.each(Chart.instances, function(chart) {
            if (chart) chart.resize();
        });
    });

    const aniosPeliculaFiltro = "${anios}";

    // === Gráfico 1: Películas por Año ===
    const filasAnio = document.querySelectorAll("#tablaPeliculas tr");
    const contadorAnio = {};

    filasAnio.forEach(fila => {
        const anioTexto = fila.children[0].innerText.trim();
        const anio = parseInt(anioTexto);

        if (!isNaN(anio)) {
            contadorAnio[anio] = (contadorAnio[anio] || 0) + 1;
        } else {
            console.warn("Año inválido:", anioTexto);
        }
    });

    //FILTRAR POR EL NUMERO DADO
    const currentYear = new Date().getFullYear();
    const startYear = currentYear - aniosPeliculaFiltro;

    const labels = [];
    const data = [];

    //CREAR ETIQUETAS Y DATOS
    for (let i = startYear; i <= currentYear; i++) {
        labels.push(i);
        data.push(contadorAnio[i]);
    }

    //ESTÉTICA BARRAS
    new Chart(document.getElementById('graficoPeliculas'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Películas por Año (Últimos ' + aniosPeliculaFiltro + ' años)',
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

    // === Gráfico 2: Por Duración ===
    const filasDuracion = document.querySelectorAll("#tablaDuraciones tr");
    const contadorDuracion = {};

    const intervalo = 15; //CAMBIAR INTERVALO SI ES NECESARIO

    filasDuracion.forEach(fila => {
        const duracionTexto = fila.children[0].innerText.trim();
        const duracion = parseInt(duracionTexto);

        if (!isNaN(duracion)) {
            const rangoInicio = Math.floor(duracion / intervalo) * intervalo;
            const rangoFinal = rangoInicio + intervalo - 1;
            const rango = rangoInicio.toString() +  " - " + rangoFinal.toString();
            contadorDuracion[rango] = (contadorDuracion[rango] || 0) + 1;
        }
    });

    const labelsDuracion = Object.keys(contadorDuracion).sort((a, b) => {
        const aStart = parseInt(a);
        const bStart = parseInt(b);
        return aStart - bStart;
    });
    const dataDuracion = labelsDuracion.map(label => contadorDuracion[label]);

    new Chart(document.getElementById('graficoDuraciones'), {
        type: 'bar',
        data: {
            labels: labelsDuracion,
            datasets: [{
                label: 'Películas por Rango de Duración',
                data: dataDuracion,
                backgroundColor: 'rgb(0, 123, 255)',
                borderColor: 'rgb(0, 92, 191)',
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

    // === Gráfico 3: Por Género ===
    const filasGenero = document.querySelectorAll("#tablaGeneros tr");
    const contadorGeneros = {};


    filasGenero.forEach(fila => {
        const genero = fila.children[0].innerText.trim();
        if (genero) {
            contadorGeneros[genero] = (contadorGeneros[genero] || 0) + 1;
        }
    });

    const labelsGenero = Object.keys(contadorGeneros);
    const dataGenero = Object.values(contadorGeneros);

    const backgroundColors = [];
    for (let i = 0; i < labelsGenero.length; i++) {
        const hue = (i * 40) % 360;
        backgroundColors.push(`hsl(` + hue + `, 70%, 60%)`);
    }

    const ctxGenero = document.getElementById('graficoGeneros').getContext('2d');
    new Chart(ctxGenero, {
        type: 'pie',
        data: {
            labels: labelsGenero,
            datasets: [{
                label: 'Distribución por Género',
                data: dataGenero,
                backgroundColor: backgroundColors
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
</script>
</body>
</html>