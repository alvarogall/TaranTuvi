<%@ page import="es.uma.taw.tarantuvi.dto.Productora" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Gámez
  Date: 04/05/2025
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Productoras</title>
    <link rel="stylesheet" type="text/css" href="/css/Administrador/vistaAdministrador.css">
</head>
<body>
    <jsp:include page="navBarAdministrador.jsp">
        <jsp:param name="activePage" value="productoras"/>
    </jsp:include>

    <div class="container">
        <h1>Productoras</h1>

        <div class="search-bar">
            <input type="text" placeholder="Buscar..." onkeyup="searchByTitle(this.value)">
            <div class="actions">
                <form method="post" action="/administrador/productoras/editar">
                    <input type="submit" value="➕ Añadir" class="add-btn"/>
                </form>
            </div>
        </div>

        <%
            List<Productora> lista = (List<Productora>) request.getAttribute("productoras");
        %>

        <div class="container">
            <table border="1" class="tabla-productoras">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Nacionalidad</th>
                    <th>CEO</th>
                    <th>Sede</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody id="moviesTableBody">
                <%
                    for (Productora productora : lista) {
                %>
                <tr class="movie-row">
                    <td><%= (productora.getProductoranombre() != null) ? productora.getProductoranombre() : ""%></td>
                    <td><%= (productora.getNacionalidad() != null) ? productora.getNacionalidad().getNacionalidadnombre() : ""%></td>
                    <td><%= (productora.getCeo() != null) ? productora.getCeo() : ""%></td>
                    <td><%= (productora.getSede() != null) ? productora.getSede() : ""%></td>
                    <td>
                        <form method="post" action="/administrador/productoras/editar" style="display:inline;">
                            <input type="hidden" name="id" value="<%= productora.getId() %>"/>
                            <input type="submit" value="✏️ Editar" class="edit-btn"/>
                        </form>
                        <form method="post" action="/administrador/productoras/borrar" style="display:inline;"
                              onsubmit="return confirm('¿Está seguro de que quiere borrar la película <%= productora.getProductoranombre() %>?');">
                            <input type="hidden" name="id" value="<%= productora.getId() %>"/>
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
                const title = cells[0].textContent.toUpperCase(); // Segunda celda (nombre)

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
