<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.*" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ListaPeliculaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.tarantuvi.entity.PeliculaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mi Perfil</title>
    <link rel="stylesheet" href="/css/usuarioPremium/perfilUsuarioPremium.css">
</head>

<%
    List<PeliculaEntity> peliculasQueMeGustan = (List<PeliculaEntity>) request.getAttribute("peliculasQueMeGustan");
    Usuario user = (Usuario) session.getAttribute("usuario");
    List<ListaPeliculaEntity> listasPeliculas = (List<ListaPeliculaEntity>) request.getAttribute("listasPeliculas");
%>




<body>

<jsp:include page="../Componentes/navBarEnlacePerfil.jsp">
    <jsp:param name="activePage" value="inicio"/>
</jsp:include>

<main class="perfil-container">
    <aside class="perfil-usuario">
        <div class="icono-usuario">👤</div>
        <h2><%=user.getUsuario().toUpperCase()%></h2>

        <a class="cerrar-sesion" href="/logout">Cerrar Sesión</a>

    </aside>

    <section class="perfil-contenido">

        <%
            if(listasPeliculas == null || listasPeliculas.isEmpty()){
        %>
        <div class="seccion">
            <h3>NO HAY LISTAS PARA MOSTRAR</h3>
        </div>
        <%
            }else{

                for(ListaPeliculaEntity lista : listasPeliculas){

        %>
                <div class="seccion">
                    <h3><%=lista.getListapeliculanombre()%>    <a id="enlaceEliminarLista" class="cerrar-sesion" href="/usuarioPremium/eliminarLista?idLista=<%=lista.getId()%>">Eliminar Lista</a>
                    <a id="enlaceAnyadirPelicula" class="anyadir-pelicula" href="/usuarioPremium/anyadirPelicula?idLista=<%=lista.getId()%>">Añadir película</a></h3>


                    <div class="grid-caratulas">
                        <%
                            if(lista.getPeliculaList().isEmpty()){
                        %>
                            
                            <p>La lista está vacía</p>
                            <br>
                        <%
                            }else{
                        
                                for(PeliculaEntity pelicula : lista.getPeliculaList()){
                        %>
                        <div>
                            <div class="pelicula-item">
                                <img class="caratula" src="<%=pelicula.getUrlcaratula()%>" alt="">
                                <p class="titulo-pelicula"><%=pelicula.getTitulooriginal()%></p>
                                <a id="botonEliminarPelicula" href="/usuarioPremium/eliminarPeliculaLista?idLista=<%=lista.getId()%>&idPelicula=<%=pelicula.getId()%>" class="cerrar-sesion">Eliminar película</a>
                            </div>

                        </div>
                            
                        <%
                                }
                            }
                        %>
                        

                    </div>
                    <br>



        <%
                }
            }
        %>



    </section>

    <div class="formulario-lista">
        <h2>Crear nueva lista</h2>
        <br>
        <form:form method="POST" action="/usuarioPremium/crearLista" modelAttribute="listaPelicula">
            <!-- Campo oculto para usuario -->
            <form:hidden path="usuarioId" value="<%=user.getUsuarioId()%>"/>

            <!-- Campo para el nombre de la lista -->
            <label for="listaPeliculaNombre">Nombre de la lista:</label>
            <form:input path="listaPeliculaNombre" id="listaPeliculaNombre" required="true"/>

            <br/><br/>
            <button type="submit">Crear Lista</button>
        </form:form>
    </div>
</main>








</body>
</html>
