<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.*" %><%--
  Created by IntelliJ IDEA.
  User: jesus
  Date: 20/04/2025
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
  boolean esEditar = true;
  PeliculaEntity pelicula = (PeliculaEntity) request.getAttribute("pelicula");
  if(pelicula.getId() == null) esEditar = false;
  List<ActuacionEntity> actuaciones = (List<ActuacionEntity>) request.getAttribute("actuaciones");
  List<PersonaEntity> personas = (List<PersonaEntity>) request.getAttribute("personas");
  List<ProductoraEntity> productoras = (List<ProductoraEntity>) request.getAttribute(("productores"));
  List<IdiomaHabladoEntity> idiomas = (List<IdiomaHabladoEntity>) request.getAttribute("idiomas");
  List<GeneroPeliculaEntity> generos = (List<GeneroPeliculaEntity>) request.getAttribute("generos");
%>

<head>
    <title><%= (esEditar? "Edición de la" : "Nuevo") %> Película</title>
  <link rel="stylesheet" type="text/css" href="/css/editarPelicula.css">
</head>
<body>

<nav class="navbar">
  <div class="logo">
    <span class="logo-text">TaranTuvi</span>
    <span class="logo-icon">🎬</span>
  </div>
  <div class="nav-right">
    <div class="profile">
      <img src="/img/imagenPerfil.png" alt="Perfil" onerror="this.onerror=null; this.src='https://via.placeholder.com/40';">
    </div>
  </div>
</nav>

<h1><%= (esEditar? "Editar" : "Nuevo") %> Película</h1>

<form method="post" action="/peliculas/confirmarCambios">
  <input type="hidden" name="id" value="<%= pelicula.getId() != null ? pelicula.getId() : -1 %>"/>
  <img src="<%= pelicula.getUrlcaratula() %>" alt="Portada"> <br/>
  <!--<label for="caratula">Cambiar carátula:</label>
  <input type="file" name="caratula" accept="image/*"> <br/>-->
  Pelicula: <input type="text" name="nombre" value="<%= pelicula.getTitulooriginal()!=null?pelicula.getTitulooriginal():""%>"> <br/>
  Fecha de Estreno: <input type="text" name="fecha" value="<%= pelicula.getFechaestreno()!=null?pelicula.getFechaestreno():""%>"> <br/>
  Duración: <input type="text" name="duracion" value="<%= pelicula.getDuracion()!=null?pelicula.getDuracion() + " min" :""%>"> <br/>
  <label for="descripcion" style="display: inline-block; vertical-align: top; margin-right: 5px;">Sinópsis:</label>
  <textarea id="descripcion" name="descripcion" rows="5" cols="50" style="display: inline-block;"><%
    out.println(pelicula.getDescripcion()!=null?pelicula.getDescripcion():"");
  %></textarea> <br/>

  <label for="crew" style="display: inline-block; vertical-align: top; margin-right: 5px;">Crew:</label>
  <textarea id="crew" name="crew" rows="5" cols="50" style="display: inline-block;"><%
    for (TrabajoEntity trabajo : pelicula.getTrabajoList()) {
      out.println(trabajo.getPersonaid().getNombre() + " - " + trabajo.getTrabajonombre());
    }
  %></textarea> <br/>

  <label for="cast" style="display: inline-block; vertical-align: top; margin-right: 5px;">Cast:</label>
  <textarea id="cast" name="cast" rows="5" cols="50" style="display: inline-block;"><%
    for (ActuacionEntity actuacion : pelicula.getActuacionList()) {
      out.println(actuacion.getPersonaid().getNombre() + " - " + actuacion.getPersonaje());
    }
  %>
</textarea> <br/>

  <label for="cast" style="display: inline-block; vertical-align: top; margin-right: 5px;">Productoras:</label>
  <textarea id="productoras" name="productoras" rows="5" cols="50" style="display: inline-block;"><%
    for(ProductoraEntity productora : pelicula.getProductoraList()){
      out.println(productora.getProductoranombre());
    }
  %></textarea> <br/>

  <label for="cast" style="display: inline-block; vertical-align: top; margin-right: 5px;">Países de rodaje:</label>
  <textarea id="paisesRodaje" name="paisesRodaje" rows="5" cols="50" style="display: inline-block;"><%
    for(PaisRodajeEntity pais : pelicula.getPaisRodajeList()){
      out.println(pais.getPaisrodajenombre());
    }
  %></textarea> <br/>

  <label for="cast" style="display: inline-block; vertical-align: top; margin-right: 5px;">Idiomas:</label>
  <textarea id="idiomas" name="idiomas" rows="5" cols="50" style="display: inline-block;"><%
    for(IdiomaHabladoEntity idioma : pelicula.getIdiomaHabladoList()){
      out.println(idioma.getIdiomahabladonombre());
    }
  %></textarea> <br/>

  <label for="cast" style="display: inline-block; vertical-align: top; margin-right: 5px;">Géneros:</label>
  <textarea id="generos" name="generos" rows="5" cols="50" style="display: inline-block;"><%
    for(GeneroPeliculaEntity genero : pelicula.getGeneroPeliculaList()){
      out.println(genero.getGeneronombre());
    }
  %></textarea> <br/>

  Recaudación: <input type="text" name="recaudacion" value="<%= pelicula.getRecaudacion()!=null?pelicula.getRecaudacion()+" €":""%>"> <br/>
  Estado: <input type="text" name="estado" value="<%= pelicula.getEstado()!=null?pelicula.getEstado():""%>">

  <input type="submit" value="Confirmar cambios">
</form>
</body>
</html>
