<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
                <img src="/img/img1.png" alt="Película 1">
                <div class="caption">Texto de la película 1</div>
            </div>
            <div class="carousel-slide">
                <img src="/img/img2.png" alt="Película 2">
                <div class="caption">Texto de la película 2</div>
            </div>
            <div class="carousel-slide">
                <img src="/img/img3.png" alt="Película 3">
                <div class="caption">Texto de la película 3</div>
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
