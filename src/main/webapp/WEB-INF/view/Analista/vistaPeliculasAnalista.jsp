<%--
  AUTOR: ALEJANDRO CUETO DÍAZ
  Created by IntelliJ IDEA.
  User: Alejandro Cueto
  Date: 21/04/2025
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="es.uma.taw.tarantuvi.dto.GeneroPelicula" %>
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

        <form:form method="get" modelAttribute="filtro" action="/analista/peliculas" cssClass="filtro-formulario">
            <label for="filtro-idioma">Idioma</label>
            <form:select path="idioma" id="filtro-idioma">
                <form:option value="">Todos los idiomas</form:option>
                <form:options items="${idiomas}" itemValue="id" itemLabel="idiomahabladonombre" />
            </form:select>

            <label for="filtro-genero">Género</label>
            <form:select path="genero" id="filtro-genero">
                <form:option value="">Todos los géneros</form:option>
                <form:options items="${generos}" itemValue="id" itemLabel="generonombre" />
            </form:select>

            <label for="orden-campo">Ordenar por</label>
            <form:select path="ordenCampo" id="orden-campo">
                <form:option value="">-- Seleccionar --</form:option>
                <form:option value="fecha">Fecha de Estreno</form:option>
                <form:option value="duracion">Duración</form:option>
                <form:option value="nota">Nota Media</form:option>
            </form:select>

            <label for="orden-tipo">Tipo de orden</label>
            <form:select path="ordenTipo" id="orden-tipo">
                <form:option value="ASC">Ascendente</form:option>
                <form:option value="DESC">Descendente</form:option>
            </form:select>

            <button type="submit">Aplicar</button>
        </form:form>
        <table>
            <thead>
            <tr>
                <th>Título</th>
                <th>Idioma</th>
                <th>Fecha</th>
                <th>Duración</th>
                <th>Nota Media</th>
                <th>Géneros</th>
                <th>Web</th>

            </tr>
            </thead>
            <tbody>
            <%
                List<Pelicula> peliculasListaFiltradas =(List<Pelicula>) request.getAttribute("peliculasFiltradas");
                List<Pelicula> peliculasLista = (List<Pelicula>) request.getAttribute("peliculas");
                for (Pelicula pelicula : peliculasListaFiltradas) {

            %>
            <tr>
                <td><%=pelicula.getTitulooriginal()%></td>
                <td><%=pelicula.getIdiomaoriginalhablado().getIdiomahabladonombre()%></td>
                <td><%=pelicula.getFechaestreno()%></td>
                <td><%=pelicula.getDuracion()%></td>
                <td><%= (pelicula.getNota() == null) ? "No Valorada" : String.format("%.2f", pelicula.getNota()) %></td>

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
                <td><a href="<%=pelicula.getPaginaweb()%>">Link</a></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- DERECHA: FORMULARIO Y GRÁFICAS -->
    <div class="right-panel">
        <!-- Gráfico 1: Películas por Año -->
        <div class="card-table" style="text-align: center" >
        <form:form method="get" modelAttribute="filtro" action="/analista/peliculas" class = "card-table h3 " cssStyle="background-color: rgba(250,250,250,0.92) " >
            Mostrar los últimos
            <form:input path="anios" type="number" min="1" max="50" />
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
        <div class="card-table">
            <canvas id="graficoGeneros"></canvas>
        </div>
    </div>
</div>

<!-- TABLAS OCULTAS PARA JS -->
<table id="tablaPeliculas" style="display:none;">
    <% for (Pelicula pelicula : peliculasLista) {
        int anio = LocalDate.parse(pelicula.getFecha()).getYear();
    %>
    <tr><td><%=anio%></td></tr>
    <% } %>
</table>

<table id="tablaDuraciones" style="display:none;">
    <% for (Pelicula pelicula: peliculasLista) {
    %>
    <tr><td><%=pelicula.getDuracion()%></td></tr>
    <% } %>
</table>

<table id="tablaGeneros" style="display:none;">
    <% for (Pelicula pelicula : peliculasLista) {
        for (GeneroPelicula genero : pelicula.getGeneroPeliculaList()) {
    %>
    <tr data-genero-id="<%=genero.getId()%>">
        <td><%=genero.getGeneronombre()%></td>
    </tr>
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

    const aniosPeliculaFiltro =  ${anios};

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
    const contadorGeneros = {};   // { id: count }
    const nombresGeneros = {};    // { id: nombre }

    filasGenero.forEach(fila => {
        const id = fila.dataset.generoId;
        const nombre = fila.children[0].innerText.trim();

        if (id) {
            contadorGeneros[id] = (contadorGeneros[id] || 0) + 1;
            nombresGeneros[id] = nombre;
        }
    });

    const ids = Object.keys(contadorGeneros);
    const dataGenero = ids.map(id => contadorGeneros[id]);
    const labelsGenero = ids.map(id => nombresGeneros[id]);
    const backgroundColors = ids.map(id => generoIdToColor(id));

    const ctxGenero = document.getElementById('graficoGeneros').getContext('2d');
    new Chart(ctxGenero, {
        type: 'pie',
        data: {
            labels: labelsGenero,
            datasets: [{
                label: 'Películas por Género',
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

    function generoIdToColor(id) {
        const hue = (parseInt(id, 10) * 80) % 360;
        return `hsl(` + hue + `, 70%, 60%)`;
    }
</script>
</body>
</html>