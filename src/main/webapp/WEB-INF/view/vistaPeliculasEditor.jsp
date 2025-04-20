<%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 07/04/2025
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Películas</title>
    <link rel="stylesheet" type="text/css" href="/css/vistaPeliculasEditor.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">
        <span class="logo-text">TaranTuvi</span>
        <span class="logo-icon">🎬</span>
    </div>
    <div class="nav-right">
        <ul class="nav-links">
            <li><a href="/">Inicio</a></li>
            <li><a href="/peliculas" class="active">Películas</a></li>
            <li><a href="/actores">Actores</a></li>
        </ul>
        <div class="profile">
            <img src="/img/imagenPerfil.png" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
        </div>
    </div>
</nav>

<div class="container">
    <div class="search-bar">
        <input type="text" placeholder="Buscar...">
        <button>🔍</button>
        <div class="actions">
            <button class="edit-btn">✏️ Editar</button>
            <button class="delete-btn">🗑️ Borrar</button>
            <button class="add-btn">➕ Añadir</button>
        </div>
    </div>

    <table>
        <thead>
        <tr>
            <th>Título</th>
            <th>Año de Producción</th>
            <th>Actores</th>
            <th>Compañías</th>
            <th>Idiomas</th>
            <th>Géneros</th>
        </tr>
        </thead>
        <tbody>
        <!-- Los datos se llenarán dinámicamente en el futuro -->
        </tbody>
    </table>

    <div class="footer">
        <div class="letters">
            <!-- Letras A-Z generadas con JS -->
        </div>
    </div>

</div>

<script>
    // Generar letras A-Z
    const lettersContainer = document.querySelector(".letters");
    const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

    // Crear los botones de letras
    alphabet.forEach(letter => {
        const btn = document.createElement("button");
        btn.textContent = letter;
        btn.onclick = () => selectLetter(letter); // Añadir el evento para el clic
        btn.classList.add("letter-btn"); // Añadir la clase para el estilo
        lettersContainer.appendChild(btn); // Agregar el botón al contenedor
    });

    // Botón "TODAS"
    const allButton = document.createElement("button");
    allButton.id = "allButton";
    allButton.textContent = "TODAS";
    allButton.onclick = () => selectLetter('ALL'); // Acción para seleccionar "TODAS"
    lettersContainer.insertBefore(allButton, lettersContainer.firstChild); // Insertar al principio

    // Función para seleccionar letra
    function selectLetter(letter) {
        // Eliminar la clase 'active-letter' de todos los botones
        document.querySelectorAll(".letter-btn, #allButton").forEach(btn => {
            btn.classList.remove("active-letter");
        });

        // Si no es "ALL", añadimos la clase 'active-letter' al botón de la letra seleccionada
        if (letter !== "ALL") {
            const active = [...document.querySelectorAll(".letter-btn")].find(b => b.textContent === letter);
            if (active) active.classList.add("active-letter");
        } else {
            // Si selecciona "TODAS", añadir la clase 'active-letter' al botón "TODAS"
            allButton.classList.add("active-letter");
        }

        // Aquí puedes usar fetch/ajax más adelante para traer datos filtrados
        console.log("Filtrando por:", letter);
    }
</script>

</body>
</html>

