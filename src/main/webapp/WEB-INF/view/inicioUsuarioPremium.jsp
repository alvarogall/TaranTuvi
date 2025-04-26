<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%

List<PeliculaEntity> peliculas = (List<PeliculaEntity>) request.getAttribute("peliculas");
Collections.shuffle(peliculas);

String img1 = peliculas.get(0).getUrlcaratula();
String img2 = peliculas.get(1).getUrlcaratula();
String img3 = peliculas.get(2).getUrlcaratula();



%>



<head>
    <title>Usuario Recomendador</title>
    <link rel="stylesheet" type="text/css" href="css/inicioUsuarioPremium.css">
</head>
<body>

<jsp:include page="navBarNormal.jsp">
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
                <div class="novedad-img"></div>
                <div class="novedad-info">
                    <strong>Película uno</strong>
                    <p>Descripción uno</p>
                    <button>▶️</button>
                </div>
            </div>
            <div class="novedad">
                <div class="novedad-img"></div>
                <div class="novedad-info">
                    <strong>Película dos</strong>
                    <p>Descripción dos</p>
                    <button>▶️</button>
                </div>
            </div>
        </aside>
    </section>

    <!-- Actores destacados -->
    <section class="actores-destacados">
        <h2>Actores destacados</h2>
        <div class="actores">
            <div class="actor">
                <div class="actor-img">👤</div>
                <p>Actor</p>
            </div>
            <div class="actor">
                <div class="actor-img">👤</div>
                <p>Actor</p>
            </div>
            <div class="actor">
                <div class="actor-img">👤</div>
                <p>Actor</p>
            </div>
            <div class="actor">
                <div class="actor-img">👤</div>
                <p>Actor</p>
            </div>
            <div class="actor">
                <div class="actor-img">👤</div>
                <p>Actor</p>
            </div>
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
