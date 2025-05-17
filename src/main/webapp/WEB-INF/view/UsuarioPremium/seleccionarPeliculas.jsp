<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ListaPeliculaEntity" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seleccionar Películas</title>
    <link rel="stylesheet" href="/css/usuarioPremium/seleccionarPeliculas.css">
</head>


<%
    ListaPeliculaEntity listaPelicula = (ListaPeliculaEntity) request.getAttribute("listaPelicula");

%>
<body>

<jsp:include page="../Componentes/navBarEnlacePerfil.jsp">
    <jsp:param name="activePage" value="inicio"/>
</jsp:include>

<main class="selector-contenedor">
    <h2>Selecciona las películas para añadir a la lista</h2>

    <form:form method="POST" action="/usuarioPremium/guardarPeliculasSeleccionadas" modelAttribute="seleccionPeliculas">
        <form:hidden path="listaId"/>

        <div class="grid-peliculas">
            <%
                List<PeliculaEntity> peliculas = (List<PeliculaEntity>) request.getAttribute("peliculas");
                if (peliculas != null) {
                    for (PeliculaEntity pelicula : peliculas) {
            %>
            <div class="pelicula-card">
                <label>
                    <img src="<%=pelicula.getUrlcaratula()%>" alt="Carátula">
                    <p><%=pelicula.getTitulooriginal()%></p>
                    <form:checkbox path="peliculaIds" value="<%=pelicula.getId()%>"/>

                </label>
            </div>
            <%
                    }
                }
            %>
        </div>

        <div class="boton-guardar">
            <button type="submit">Añadir a la lista</button>
        </div>
    </form:form>
</main>

</body>
</html>
