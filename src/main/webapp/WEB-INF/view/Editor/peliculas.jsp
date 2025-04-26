<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Películas</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/vistaPeliculasActoresEditor.css">
</head>
<body>
<jsp:include page="../Componentes/navBarNormal.jsp">
    <jsp:param name="activePage" value="peliculas"/>
</jsp:include>

<div class="container">
    <div class="search-bar">
        <input type="text" placeholder="Buscar..." onkeyup="searchByTitle(this.value)">
        <div class="actions">
            <form method="post" action="/peliculas/editar">
                <input type="submit" value="➕ Añadir" class="add-btn"/>
            </form>
        </div>
    </div>

    <%
        List<PeliculaEntity> lista = (List<PeliculaEntity>) request.getAttribute("peliculas");
    %>

    <div class="container">
        <table border="1">
            <thead>
            <tr>
                <th>Portada</th>
                <th>Título</th>
                <th>Duración (min)</th>
                <th>Fecha de Estreno</th>
                <th>Cast (Actor/actriz - Personaje)</th>
                <th>Crew (Persona - Trabajo)</th>
                <th>Compañías</th>
                <th>Idiomas</th>
                <th>Géneros</th>
                <th>Presupuesto (€)</th>
                <th>Recaudación (€)</th>
                <th>Eslogan</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody id="moviesTableBody">
            <%
                for (PeliculaEntity pelicula : lista) {
            %>
            <tr class="movie-row">
                <td>
                    <div class="movie-poster">
                        <img src="<%= pelicula.getUrlcaratula() != null ? pelicula.getUrlcaratula() : "https://i.postimg.cc/Ghm0s21d/add-photo-svgrepo-com.png" %>" alt="Portada">
                    </div>
                </td>
                <td><%= (pelicula.getTitulooriginal() != null) ? pelicula.getTitulooriginal() : ""%></td>
                <td><%= pelicula.getDuracion() != null ? pelicula.getDuracion() : ""%></td>
                <td><%= (pelicula.getFechaestreno() != null) ? pelicula.getFechaestreno() : "" %></td>
                <td>
                    <%
                        if (pelicula.getActuacionList() != null) {
                            for (ActuacionEntity actuacion : pelicula.getActuacionList()) {
                                PersonaEntity persona = actuacion.getPersonaid();
                                if (persona != null) {
                                    out.print(persona.getNombre() + " - " + actuacion.getPersonaje() + "<br/>");
                                }
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        if (pelicula.getTrabajoList() != null) {
                            for (TrabajoEntity trabajo : pelicula.getTrabajoList()) {
                                PersonaEntity persona = trabajo.getPersonaid();
                                if (persona != null) {
                                    out.print(persona.getNombre() + " - " + trabajo.getTrabajonombre() + "<br/>");
                                }
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        if (pelicula.getProductoraList() != null) {
                            for (ProductoraEntity prod : pelicula.getProductoraList()) {
                                out.print(prod.getProductoranombre() + "<br/>");
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        if (pelicula.getIdiomaHabladoList() != null) {
                            for (IdiomaHabladoEntity idioma : pelicula.getIdiomaHabladoList()) {
                                out.print(idioma.getIdiomahabladonombre() + "<br/>");
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        if (pelicula.getGeneroPeliculaList() != null) {
                            for (GeneroPeliculaEntity genero : pelicula.getGeneroPeliculaList()) {
                                out.print(genero.getGeneronombre() + "<br/>");
                            }
                        }
                    %>
                </td>
                <td><%= pelicula.getPresupuesto() != null ? pelicula.getPresupuesto() : ""%></td>
                <td><%= pelicula.getRecaudacion() != null ? pelicula.getRecaudacion() : ""%></td>
                <td><%= pelicula.getEslogan() != null ? pelicula.getEslogan() : ""%></td>
                <td>
                    <form method="post" action="/peliculas/editar" style="display:inline;">
                        <input type="hidden" name="id" value="<%= pelicula.getId() %>"/>
                        <input type="submit" value="✏️ Editar" class="edit-btn"/>
                    </form>
                    <form method="post" action="/peliculas/borrar" style="display:inline;"
                          onsubmit="return confirm('¿Está seguro de que quiere borrar la película <%= pelicula.getTitulooriginal() %>?');">
                        <input type="hidden" name="id" value="<%= pelicula.getId() %>"/>
                        <input type="submit" value="🗑️ Borrar" class="delete-btn"/>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="footer">
        <div class="letters">
            <!-- Letras A-Z generadas con JS -->
        </div>
    </div>
</div>

<script>
    // Al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
        const lettersContainer = document.querySelector(".letters");
        const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

        // Crear botón TODAS primero y activarlo
        const allButton = document.createElement("button");
        allButton.id = "allButton";
        allButton.textContent = "TODAS";
        allButton.classList.add("active-letter");
        allButton.onclick = () => selectLetter('ALL');
        lettersContainer.appendChild(allButton);

        // Crear botones de letras
        alphabet.forEach(letter => {
            const btn = document.createElement("button");
            btn.textContent = letter;
            btn.onclick = () => selectLetter(letter);
            btn.classList.add("letter-btn");
            lettersContainer.appendChild(btn);
        });

        // Mostrar todas las películas al inicio
        filterActorsByLetter('ALL');
    });

    function selectLetter(letter) {
        document.querySelectorAll(".letter-btn, #allButton").forEach(btn => {
            btn.classList.remove("active-letter");
        });

        const activeBtn = letter === 'ALL'
            ? document.getElementById("allButton")
            : [...document.querySelectorAll(".letter-btn")].find(b => b.textContent === letter);

        if (activeBtn) activeBtn.classList.add("active-letter");

        filterMoviesByLetter(letter);
    }

    function filterMoviesByLetter(letter) {
        const rows = document.querySelectorAll(".movie-row");
        const searchTerm = document.querySelector(".search-bar input[type='text']").value.trim().toUpperCase();

        rows.forEach(row => {
            const cells = row.querySelectorAll("td");
            const title = cells[1].textContent.toUpperCase(); // Segunda celda (título)

            const matchesLetter = letter === 'ALL' || title.startsWith(letter);
            const matchesSearch = searchTerm === '' || title.includes(searchTerm);

            row.style.display = matchesLetter && matchesSearch ? "" : "none";
        });
    }

    function searchByTitle(query) {
        // Reutilizamos filterMoviesByLetter para aplicar ambos filtros
        const activeLetterBtn = document.querySelector(".letter-btn.active-letter, #allButton.active-letter");
        const activeLetter = activeLetterBtn?.textContent === 'TODAS' ? 'ALL' : activeLetterBtn?.textContent;
        filterMoviesByLetter(activeLetter || 'ALL');
    }
</script>

</body>
</html>