<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.tarantuvi.dto.*" %>
<%@ page import="es.uma.taw.tarantuvi.entity.ListaPeliculaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mi Perfil</title>
    <link rel="stylesheet" href="/css/usuarioPremium/perfilUsuarioPremium.css">
</head>

<%
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
                    <h3><%=lista.getListapeliculanombre()%></h3>
                    <div class="grid-caratulas">
                        <div class="caratula"></div>
                        <div class="caratula"></div>
                        <div class="caratula"></div>
                        <div class="caratula"></div>
                    </div>



        <%
                }
            }
        %>

        <div class="seccion">
            <h3>MIS LISTAS</h3>
            <div class="grid-caratulas">
                <div class="caratula"></div>
                <div class="caratula"></div>
                <div class="caratula"></div>
                <div class="caratula"></div>
            </div>
        </div>
        <div class="seccion">
            <h3>PELÍCULAS VISTAS</h3>
            <div class="grid-caratulas">
                <div class="caratula"></div>
                <div class="caratula"></div>
                <div class="caratula"></div>
                <div class="caratula"></div>
            </div>
        </div>
    </section>
</main>
</body>
</html>
