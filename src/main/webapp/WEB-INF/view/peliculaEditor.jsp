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
</head>
<body>
<h1><%= (esEditar? "Editar" : "Nuevo") %> Película</h1>

Pelicula: <input type="text" name="nombre" value="<%= pelicula.getTitulooriginal()!=null?pelicula.getTitulooriginal():""%>">
Fecha de Estreno: <input type="text" name="anyo" value="<%= pelicula.getFechaestreno()!=null?pelicula.getFechaestreno():""%>">

</body>
</html>
