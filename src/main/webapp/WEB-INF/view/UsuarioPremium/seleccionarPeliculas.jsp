<%--/*Máximo Prados Meléndez*/--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ListaPeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.dto.ListaPelicula" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seleccionar Películas</title>
    <link rel="stylesheet" href="/css/usuarioPremium/seleccionarPeliculas.css">
</head>


<%
    ListaPelicula listaPelicula = (ListaPelicula) request.getAttribute("listaPelicula");

%>
<body>

<jsp:include page="navBar.jsp">
    <jsp:param name="activePage" value=""/>
</jsp:include>

<main class="selector-contenedor">
    <h2><strong>Selecciona las películas para añadir a la lista</strong></h2>

    <form:form method="POST" action="/usuarioPremium/guardarPeliculasSeleccionadas" modelAttribute="seleccionPeliculas">
        <form:hidden path="listaId"/>

        <div class="grid-peliculas">
            <%
                List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
                if (peliculas != null) {
                    for (Pelicula pelicula : peliculas) {

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
