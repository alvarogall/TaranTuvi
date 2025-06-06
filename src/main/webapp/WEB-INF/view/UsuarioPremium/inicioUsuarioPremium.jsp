<%--/*Máximo Prados Meléndez*/--%>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.Collections" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%

List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
List<Pelicula> novedades = (List<Pelicula>) request.getAttribute("novedades");
Collections.shuffle(peliculas);

String img1 = peliculas.get(0).getUrlcaratula();
String img2 = peliculas.get(1).getUrlcaratula();
String img3 = peliculas.get(2).getUrlcaratula();

List<Pelicula> peliculasQueMeGustan = (List<Pelicula>) request.getAttribute("peliculasQueMeGustan");


%>



<head>
    <title>Usuario Recomendador</title>
    <link rel="stylesheet" type="text/css" href="/css/usuarioPremium/inicioUsuarioPremium.css">
</head>
<body>

<jsp:include page="navBar.jsp">
    <jsp:param name="activePage" value="inicio"/>
</jsp:include>

<main>
    <section class="layout-superior">
        <!-- Carrusel -->
        <div class="carousel-container">
            <div class="carousel-slide active">
                <img src="<%= img1 != null ? img1 : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Película 1">
                <div class="caption"><%=peliculas.get(0).getTitulooriginal() + " (" + peliculas.get(0).getFechaestreno().getYear() + ")"%></div>
            </div>
            <div class="carousel-slide">
                <img src="<%= img2 != null ? img2 : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Película 2">
                <div class="caption"><%=peliculas.get(1).getTitulooriginal() + " (" + peliculas.get(1).getFechaestreno().getYear() + ")"%></div>
            </div>
            <div class="carousel-slide">
                <img src="<%= img3 != null ? img3 : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Película 3">
                <div class="caption"><%=peliculas.get(2).getTitulooriginal() + " (" + peliculas.get(2).getFechaestreno().getYear() + ")"%></div>
            </div>

            <button class="carousel-btn prev" onclick="moveSlide(-1)">&#10094;</button>
            <button class="carousel-btn next" onclick="moveSlide(1)">&#10095;</button>
        </div>

        <!-- Novedades -->
        <aside class="novedades">
            <h2>Novedades</h2>
            <div class="novedad">
                <img class="novedad-img" src="<%= novedades.get(0).getUrlcaratula() != null ? novedades.get(0).getUrlcaratula() : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Carátula">
                <div class="novedad-info">
                    <strong><%=novedades.get(0) != null ? novedades.get(0).getTitulooriginal() : "Fallo"%></strong>
                    <p><%=novedades.get(0) != null ? novedades.get(0).getFechaestreno().getYear() : "Fallo"%></p>
                    <button>▶️</button>
                </div>
            </div>
            <div class="novedad">
                <img class="novedad-img" src="<%= novedades.get(1).getUrlcaratula() != null ? novedades.get(1).getUrlcaratula() : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Carátula">
                <div class="novedad-info">
                    <strong><%=novedades.get(1) != null ? novedades.get(1).getTitulooriginal() : "Fallo"%></strong>
                    <p><%=novedades.get(1) != null ? novedades.get(1).getFechaestreno().getYear() : "Fallo"%></p>
                    <button>▶️</button>
                </div>
            </div>
        </aside>
    </section>

    <!-- Peliculas recomendadas -->
    <section class="peliculas-destacados">
        <div class="titulo-recomendadas-container">
            <h2 class="titulo-recomendadas">
                <span class="emoji-bounce">🍿</span>
                <span class="titulo-texto"> Películas que te encantarán </span>
                <span class="emoji-bounce">🍿</span>
            </h2>
        </div>


        <div class="peliculas-recomendadas">


            <%
            if(peliculasQueMeGustan!=null && !peliculasQueMeGustan.isEmpty()){

                for (Pelicula peli : peliculasQueMeGustan) { %>
            <div class="pelicula-recomendada">
                <img src="<%= peli.getUrlcaratula() != null ? peli.getUrlcaratula() : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Carátula">
                <h4><%= peli.getTitulooriginal() %></h4>
                <p><%= peli.getFechaestreno().getYear() %></p>
            </div>
            <%  }
            }else{%>

                <h4>¡Ya te has visto todas nuestras peliculas!</h4>


            <%

            }
            %>
        </div>
    </section>

</main>

<script>
    let currentSlide = 0;
    const slides = document.querySelectorAll('.carousel-slide');

    function showSlide(index) {
        slides.forEach(slide => slide.classList.remove('active'));
        currentSlide = (index + slides.length) % slides.length;
        slides[currentSlide].classList.add('active');
    }

    function moveSlide(step) {
        showSlide(currentSlide + step);
    }

    showSlide(currentSlide);
</script>

</body>
</html>
