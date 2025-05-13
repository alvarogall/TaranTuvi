USE TaranTuvi;

-- Roles
INSERT INTO ROL (ROLNOMBRE) VALUES
('analista'),
('administrador'),
('editor'),
('usuario'),
('usuarioPremium');

-- Usuarios
INSERT INTO USUARIO (USUARIO, PASSWORD, ROLID) VALUES
('analista', 'analista', 1),
('administrador', 'administrador', 2),
('editor', 'editor', 3),
('usuario', 'usuario', 4),
('usuarioPremium', 'usuarioPremium', 5);

-- Géneros de película
INSERT INTO GENEROPELICULA (GENERONOMBRE) VALUES
('Acción'),
('Comedia'),
('Drama'),
('Terror'),
('Ciencia Ficción'),
('Romántico'),
('Suspense'),
('Aventura');

-- Palabras clave
INSERT INTO PALABRACLAVE (PALABRACLAVENOMBRE) VALUES
('superhéroe'),
('apocalipsis'),
('espacio'),
('amor prohibido'),
('submarino'),
('viaje en el tiempo'),
('asesinato');

-- Idiomas hablados
INSERT INTO IDIOMAHABLADO (IDIOMAHABLADONOMBRE) VALUES
('Inglés'),
('Español'),
('Francés'),
('Alemán'),
('Italiano'),
('Mandarín');

-- Géneros de persona
INSERT INTO GENEROPERSONA (GENEROPERSONANOMBRE) VALUES
('Masculino'),
('Femenino'),
('No binario');

-- Nacionalidades
INSERT INTO NACIONALIDAD (NACIONALIDADNOMBRE) VALUES
('Estadounidense'),
('Mexicana'),
('Francesa'),
('Italiana'),
('Británica'),
('Coreana del Sur');

-- Departamentos
INSERT INTO DEPARTAMENTO (DEPARTAMENTONOMBRE) VALUES
('Dirección'),
('Producción'),
('Guion'),
('Fotografía'),
('Sonido');

-- Personas
INSERT INTO PERSONA (NOMBRE, GENEROPERSONAID, NACIONALIDADID, URLFOTO) VALUES
('Robert Downey Jr.', 1, 1, 'https://i.postimg.cc/CK7m8gXr/robert-downey-jr-star-wars.webp'),
('Salma Hayek', 2, 2, 'https://i.postimg.cc/GhR4CKMM/Salma-Hayek-by-Gage-Skidmore-2.jpg'),
('Chris Hemsworth', 1, 1, 'https://i.postimg.cc/Z5TvrHp9/thor.webp'),
('Scarlett Johansson', 2, 1, 'https://i.postimg.cc/NfvH0B0k/Scarlett-Johansson-by-Gage-Skidmore-2-cropped-cropped.jpg'),
('Leonardo DiCaprio', 1, 1, 'https://i.postimg.cc/hGVFcLZr/MV5-BMj-I0-MTg3-Mz-I0-M15-BMl5-Ban-Bn-Xk-Ft-ZTcw-Mz-Qy-ODU2-Mw-V1.jpg'),
('Kate Winslet', 2, 5, 'https://i.postimg.cc/NfPv4fr1/kate-winslet.webp'),
('Tom Hanks', 1, 1, 'https://i.postimg.cc/K8rVWG1q/Tom-Hanks.jpg');

-- Países de rodaje
INSERT INTO PAISRODAJE (PAISRODAJENOMBRE) VALUES
('EE.UU.'),
('Canadá'),
('Reino Unido'),
('España'),
('Francia');

-- Productoras
INSERT INTO PRODUCTORA (PRODUCTORANOMBRE) VALUES
('Marvel Studios'),
('Pixar'),
('Paramount Pictures'),
('20th Century Fox'),
('Warner Bros.'),
('Universal Pictures');

-- Películas
INSERT INTO PELICULA (
  TITULOORIGINAL, IDIOMAORIGINALHABLADOID, PRESUPUESTO, FECHAESTRENO,
  DURACION, RECAUDACION, ESTADO, PAGINAWEB, DESCRIPCION, ESLOGAN,
  URLCARATULA, VOTOS, NOTA, POPULARIDAD
) VALUES
('Iron Man', 1, 140000000, '2008-05-02', 126, 585000000, 'Estrenada',
 'https://marvel.com/ironman',
 'Un empresario millonario crea una armadura para luchar contra amenazas globales.',
 'Héroe en armadura',
 'https://i.postimg.cc/0jgKktHX/Iron-Man-1-Portada.webp',
 800000, 7.9, 90.5),
('The Avengers', 1, 220000000, '2012-05-04', 143, 1518812988, 'Estrenada',
 'https://marvel.com/avengers',
 'Vengadores se unen para detener la invasión de Nueva York.',
 'Unos héroes nunca antes reunidos',
 'https://i.postimg.cc/VvD4Gs5T/s-l1200.jpg',
 1200000, 8.0, 92.3),
('Titanic', 1, 200000000, '1997-12-19', 195, 2201647264, 'Estrenada',
 'https://paramount.com/titanic',
 'Historia de amor épica a bordo del mítico barco que se hunde.',
 'Nunca dejaré ir',
 'https://i.postimg.cc/J7qT4xKq/91z-Gp74-Qc4-L.jpg',
 1800000, 7.8, 85.7),
('Spider-Man 2', 1, 200000000, '2004-06-30', 127, 783766341, 'Estrenada',
 'https://marvel.com/spiderman2',
 'Peter Parker lidia con la responsabilidad de ser Spider-Man.',
 'Con gran poder viene gran responsabilidad',
 'https://i.postimg.cc/NMvjhWS6/Spider-Man-2.webp',
 950000, 7.3, 88.1),
('Inception', 1, 160000000, '2010-07-16', 148, 836848102, 'Estrenada',
 'https://warnerbros.com/inception',
 'Un ladrón roba secretos a través de la tecnología de entrada a los sueños.',
 'Tu mente es el escenario',
 'https://i.postimg.cc/wvzCv1Sd/portada-Inception.jpg',
 1100000, 8.8, 94.2);

-- Relación película-género
INSERT INTO PELICULAGENEROPELICULA (GENEROPELICULAID, PELICULAID) VALUES
(1, 1), -- Iron Man: Acción
(6, 1), -- Iron Man: Aventura
(1, 2), -- The Avengers: Acción
(6, 2), -- The Avengers: Aventura
(3, 3), -- Titanic: Drama
(6, 3), -- Titanic: Romántico
(1, 4), -- Spider-Man 2: Acción
(6, 4), -- Spider-Man 2: Aventura
(5, 5), -- Inception: Ciencia Ficción
(7, 5); -- Inception: Suspense

-- Relación película-palabra clave
INSERT INTO PELICULAPALABRACLAVE (PALABRACLAVEID, PELICULAID) VALUES
(1, 1), -- superhéroe
(3, 2), -- espacio (The Avengers tiene secuencias espaciales)
(4, 3), -- amor prohibido
(6, 5), -- viaje en el tiempo (Inception)
(7, 5); -- asesinato (parcial en trama)

-- Relación película-idioma hablado
INSERT INTO PELICULAIDIOMAHABLADO (IDIOMAHABLADOID, PELICULAID) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

-- Relación película-productora
INSERT INTO PELICULAPRODUCTORA (PELICULAID, PRODUCTORAID) VALUES
(1, 1), -- Iron Man: Marvel Studios
(2, 1),
(3, 3), -- Titanic: Paramount
(4, 1),
(5, 5); -- Inception: Warner Bros.

-- Relación película-país rodaje
INSERT INTO PELICULAPAISRODAJE (PELICULAID, PAISRODAJEID) VALUES
(1, 1),(1, 2),
(2, 1),(2, 3),
(3, 1),(3, 4),
(4, 1),(4, 3),
(5, 1),(5, 3);

-- Actuaciones
INSERT INTO ACTUACION (PERSONAID, PELICULAID, GENEROPERSONAID, PERSONAJE, ORDEN) VALUES
(1, 1, 1, 'Tony Stark / Iron Man', 1),
(2, 1, 2, 'Pepper Potts', 2),
(3, 2, 1, 'Thor', 1),
(4, 2, 2, 'Natasha Romanoff', 2),
(5, 3, 1, 'Jack Dawson', 1),
(6, 3, 2, 'Rose DeWitt Bukater', 2),
(4, 4, 2, 'Mary Jane Watson', 1),
(3, 4, 1, 'Peter Parker / Spider-Man', 2),
(7, 5, 1, 'Dom Cobb', 1);

-- Trabajos (roles técnicos)
INSERT INTO TRABAJO (PERSONAID, PELICULAID, TRABAJONOMBRE, DEPARTAMENTOID) VALUES
(1, 1, 'Actor Principal', 1),
(2, 1, 'Actriz Secundaria', 1),
(3, 2, 'Actor Principal', 1),
(4, 2, 'Actriz Secundaria', 1),
(5, 3, 'Actor Principal', 1),
(6, 3, 'Actriz Principal', 1),
(7, 5, 'Actor Principal', 1);

-- Listas de películas
INSERT INTO LISTAPELICULA (LISTAPELICULANOMBRE, USUARIOID) VALUES
('Favoritas de Juan', 1),
('Pendientes de Ana', 2),
('Películas Clásicas', 3);

-- Relación película-lista
INSERT INTO PELICULALISTAPELICULA (PELICULAID, LISTAPELICULAID) VALUES
(1, 1),
(2, 1),
(3, 3),
(5, 2);

-- Valoraciones
INSERT INTO VALORACION (PELICULAID, USUARIOID, NOTA, VALORACIONID) VALUES
(1, 1, 8.5, 1),
(1, 2, 9.0, 2),
(2, 1, 9.5, 3),
(2, 2, 8.7, 4),
(3, 3, 10.0, 5),
(5, 4, 8.9, 6);
