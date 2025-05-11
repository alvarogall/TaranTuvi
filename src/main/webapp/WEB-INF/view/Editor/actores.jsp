<%@ page import="es.uma.taw.tarantuvi.entity.ActuacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PersonaEntity" %><%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 08/04/2025
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
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
    List<PersonaEntity> personas = (List<PersonaEntity>) request.getAttribute("personas");
    List<ActuacionEntity> actuaciones = (List<ActuacionEntity>) request.getAttribute("actuaciones");
    List<PeliculaEntity> peliculas = (List<PeliculaEntity>) request.getAttribute("peliculas");
    List<GeneroPeliculaEntity> generos = (List<GeneroPeliculaEntity>) request.getAttribute("generos");
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
            for(PersonaEntity persona : personas){
                esActor = false;
                for(ActuacionEntity actuacion : actuaciones){
                    if(actuacion.getPersonaid().getId() == persona.getId()){
                        esActor = true;
                    }
                }
                if(esActor){
        %>
        <tr class="movie-row">
            <td>
                <div class="movie-poster">
                    <img src="<%= persona.getUrlfoto() != null ? persona.getUrlfoto() : "https://i.postimg.cc/x1GgSpbn/add-circle-svgrepo-com.png" %>" alt="Foto">
                </div>
            </td>

            <td>
                <%= persona.getNombre() != null ? persona.getNombre() : ""%>
            </td>

            <td>
                <%= persona.getGeneropersonaid().getGeneropersonanombre() != null ? persona.getGeneropersonaid().getGeneropersonanombre() : ""%>
            </td>

            <td>
                <%= persona.getNacionalidadid().getNacionalidadnombre() != null ? persona.getNacionalidadid().getNacionalidadnombre() : ""%>
            </td>

            <td>
                <%
                    for(PeliculaEntity pelicula : peliculas){
                        for(ActuacionEntity act : pelicula.getActuacionList()){
                            if(act.getPersonaid().getNombre().equals(persona.getNombre())){
                                out.print(pelicula.getTitulooriginal() + "<br/>");
                            }
                        }
                    }
                %>
            </td>

            <td>
                <%
                    for(ActuacionEntity act : actuaciones){
                        if(act.getPersonaid().getId() == persona.getId()){
                            out.print(act.getPersonaje() + "<br/>");
                        }
                    }
                %>
            </td>

            <td>
                <%
                    for(GeneroPeliculaEntity genero : generos){
                        for(PeliculaEntity pelicula : genero.getPeliculaList()){
                            for(ActuacionEntity act : pelicula.getActuacionList()){
                                if(act.getPersonaid().getId() == persona.getId()){
                                    out.print(genero.getGeneronombre() + "<br/>");
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

        filterActorsByLetter(letter);
    }

    function filterActorsByLetter(letter) {
        const rows = document.querySelectorAll(".movie-row");
        const searchTerm = document.querySelector(".search-bar input[type='text']").value.trim().toUpperCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");

        rows.forEach(row => {
            const cells = row.querySelectorAll("td");
            const name = cells[1].textContent.trim().toUpperCase().normalize("NFD").replace(/[\u0300-\u036f]/g, ""); // LIMPIO

            const matchesLetter = letter === 'ALL' || name.startsWith(letter);
            const matchesSearch = searchTerm === '' || name.includes(searchTerm);

            row.style.display = matchesLetter && matchesSearch ? "" : "none";
        });
    }


    function searchByName(query) {
        // Reutilizamos filterActorsByLetter para aplicar ambos filtros
        const activeLetterBtn = document.querySelector(".active-letter");
        const activeLetter = activeLetterBtn?.textContent === 'TODAS' ? 'ALL' : activeLetterBtn?.textContent;
        filterActorsByLetter(activeLetter || 'ALL');
    }

</script>

</body>
</html>