<%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 08/04/2025
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Pelicula" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Actor" %>
<%@ page import="es.uma.taw.tarantuvi.dto.Actuacion" %>
<%@ page import="es.uma.taw.tarantuvi.dto.GeneroPelicula" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actores</title>
    <link rel="stylesheet" type="text/css" href="/css/Editor/vistaPeliculasActoresEditor.css">
</head>
<body>
<jsp:include page="../Componentes/navBarNormal.jsp">
    <jsp:param name="activePage" value="actores"/>
</jsp:include>

<%
    List<Actor> personas = (List<Actor>) request.getAttribute("personas");
    List<Actuacion> actuaciones = (List<Actuacion>) request.getAttribute("actuaciones");
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
%>

<div class="container">
    <div class="search-bar">
        <input type="text" placeholder="Buscar..." onkeyup="searchByName(this.value)">
        <div class="actions">
            <form method="post" action="/editor/actores/editar">
                <input type="submit" value="➕ Añadir" class="add-btn"/>
            </form>
        </div>
    </div>

    <table>
        <thead>
        <tr>
            <th>Foto</th>
            <th>Nombre</th>
            <th>Género</th>
            <th>Nacionalidad</th>
            <th>Películas</th>
            <th>Personajes</th>
            <th>Géneros</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="actoresTableBody">
        <%
            boolean esActor = false;
            for(Actor persona : personas){
                esActor = false;
                for(Actuacion actuacion : actuaciones){
                    if(actuacion.getPersonaid().getId() == persona.getId()){
                        esActor = true;
                    }
                }
                if(esActor){
        %>
        <tr class="movie-row"> <!-- Se queda como "movie-row" para usar el mismo css y el mismo script -->
            <td>
                <div class="movie-poster"> <!-- Se queda como "movie-poster" para usar el mismo css y el mismo script -->
                    <img src="<%= persona.getUrlfoto() != null ? persona.getUrlfoto() : "https://i.postimg.cc/x1GgSpbn/add-circle-svgrepo-com.png" %>" alt="Foto">
                </div>
            </td>

            <td>
                <%= persona.getNombre() != null ? persona.getNombre() : ""%>
            </td>

            <td>
                <%= persona.getNombreGenero()!= null ? persona.getNombreGenero() : ""%>
            </td>

            <td>
                <%= persona.getNombreNacionalidad() != null ? persona.getNombreNacionalidad() : ""%>
            </td>

            <td>
                <%
                    for(Pelicula pelicula : peliculas){
                        for(Actuacion act : pelicula.getActuacionList()){
                            if(act.getPersonaid().getNombre().equals(persona.getNombre())){
                                out.print(pelicula.getTitulooriginal() + "<br/>");
                            }
                        }
                    }
                %>
            </td>

            <td>
                <%
                    for(Actuacion act : actuaciones){
                        if(act.getPersonaid().getId() == persona.getId() && act.getPersonaje() != null){
                            out.print(act.getPersonaje() + "<br/>");
                        }
                    }
                %>
            </td>

            <td>
                <%
                    Set<String> mostrados = new HashSet<>(); //Para controlar la salida de duplicados
                                                            // (En un set solo se añade al llamar a add si no está en el set el elemento a añadir)

                    for (Pelicula pelicula : peliculas) {
                        for (Actuacion act : pelicula.getActuacionList()) {
                            if (act.getPersonaid().getId() == persona.getId()) {
                                for (GeneroPelicula genero : pelicula.getGeneroPeliculaList()) {
                                    String nombreGen = genero.getGeneronombre();
                                    if (mostrados.add(nombreGen)) {
                                        out.print(nombreGen + "<br/>");
                                    }
                                }
                            }
                        }
                    }
                %>
            </td>


            <td>
                <form method="post" action="/editor/actores/editar" style="display:inline;">
                    <input type="hidden" name="id" value="<%= persona.getId() %>"/>
                    <input type="submit" value="✏️ Editar" class="edit-btn"/>
                </form>
                <form method="post" action="/editor/actores/borrar" style="display:inline;"
                      onsubmit="return confirm('¿Está seguro de que quiere borrar al actor/actriz <%= persona.getNombre() %>?');">
                    <input type="hidden" name="id" value="<%= persona.getId() %>"/>
                    <input type="submit" value="🗑️ Borrar" class="delete-btn"/>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

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
        allButton.textContent = "TODOS";
        allButton.classList.add("active-letter");
        allButton.onclick = () => selectLetter('ALL');
        lettersContainer.appendChild(allButton);

        // Crear botones de letras
        alphabet.forEach(letter => {
            const btn = document.createElement("button");
            btn.textContent = letter;
            btn.onclick = () => selectLetter(letter.toUpperCase());
            btn.classList.add("letter-btn");
            lettersContainer.appendChild(btn);
        });

        // Mostrar todos los actores al inicio
        filterActorsByLetter('ALL');
    });

    function selectLetter(letter) {
        /*console.log("Letra seleccionada:", letter);*/
        document.querySelectorAll(".letter-btn, #allButton").forEach(btn =>
            btn.classList.remove("active-letter")
        );
        const activeBtn = letter === 'ALL'
            ? document.getElementById("allButton")
            : [...document.querySelectorAll(".letter-btn")].find(b => b.textContent === letter);
        if (activeBtn) activeBtn.classList.add("active-letter");
        filterActorsByLetter(letter);
    }

    function filterActorsByLetter(letter) {
        const rows = document.querySelectorAll(".movie-row");
        const searchTerm = document.querySelector(".search-bar input[type='text']")
            .value.trim().toUpperCase();

        rows.forEach(row => {
            const name = row.querySelectorAll("td")[1].textContent.trim().toUpperCase();
            const matchesLetter = letter === 'ALL' || name.startsWith(letter.toUpperCase());
            const matchesSearch = searchTerm === '' || name.includes(searchTerm);
            row.style.display = (matchesLetter && matchesSearch) ? "" : "none";
        });
    }

    function searchByName(query) {
        // Se reutiliza filterActorsByLetter para aplicar ambos filtros
        const activeBtn = document.querySelector(".active-letter");
        const activeLetter = activeBtn?.textContent === 'TODOS' ? 'ALL' : activeBtn?.textContent;
        filterActorsByLetter(activeLetter || 'ALL');
    }
</script>

</body>
</html>