USE TaranTuvi;

INSERT INTO ROL (ROLNOMBRE) 
VALUES
('analista'),
('administrador'),
('editor'),
('usuario'),
('usuarioPremium');


INSERT INTO USUARIO (USUARIO, PASSWORD, ROLID)
VALUES
('analista', 'analista', 1),
('administrador', 'administrador', 2),
('editor', 'editor', 3),
('usuario', 'usuario', 4),
('usuarioPremium', 'usuarioPremium', 5);

-- Insertar géneros de película
INSERT INTO GENEROPELICULA (GENERONOMBRE) VALUES
                                              ('Acción'),
                                              ('Comedia'),
                                              ('Drama'),
                                              ('Terror'),
                                              ('Ciencia Ficción'),
                                              ('Romántico');

-- Insertar palabras clave
INSERT INTO PALABRACLAVE (PALABRACLAVENOMBRE) VALUES
                                                  ('superhéroe'),
                                                  ('apocalipsis'),
                                                  ('espacio'),
                                                  ('romántico'),
                                                  ('vampiros'),
                                                  ('viaje en el tiempo');

-- Insertar idiomas
INSERT INTO IDIOMAHABLADO (IDIOMAHABLADONOMBRE) VALUES
                                                    ('Inglés'),
                                                    ('Español'),
                                                    ('Francés'),
                                                    ('Alemán'),
                                                    ('Italiano');

-- Insertar géneros de persona
INSERT INTO GENEROPERSONA (GENEROPERSONANOMBRE) VALUES
                                                    ('Masculino'),
                                                    ('Femenino'),
                                                    ('No binario');

-- Insertar nacionalidades
INSERT INTO NACIONALIDAD (NACIONALIDADNOMBRE) VALUES
                                                  ('Estadounidense'),
                                                  ('Mexicana'),
                                                  ('Francesa'),
                                                  ('Italiana'),
                                                  ('Británica');

-- Insertar departamentos
INSERT INTO DEPARTAMENTO (DEPARTAMENTONOMBRE) VALUES
                                                  ('Dirección'),
                                                  ('Producción'),
                                                  ('Guion'),
                                                  ('Fotografía'),
                                                  ('Sonido');

-- Insertar personas
INSERT INTO PERSONA (NOMBRE, GENEROPERSONAID, NACIONALIDADID, URLFOTO) VALUES
                                                                           ('Robert Downey Jr.', 1, 1, 'https://i.postimg.cc/CK7m8gXr/robert-downey-jr-star-wars.webp'),
                                                                           ('Salma Hayek', 2, 2, 'https://ejemplo.com/salma.jpg'),
                                                                           ('Chris Hemsworth', 1, 1, 'https://ejemplo.com/chris.jpg'),
                                                                           ('Scarlett Johansson', 2, 3, 'https://ejemplo.com/scarlett.jpg'),
                                                                           ('Tom Hiddleston', 1, 4, 'https://ejemplo.com/tom.jpg');

-- Insertar países de rodaje
INSERT INTO PAISRODAJE (PAISRODAJENOMBRE) VALUES
                                              ('EE.UU.'),
                                              ('Canadá'),
                                              ('Reino Unido'),
                                              ('España'),
                                              ('Francia');

-- Insertar productoras
INSERT INTO PRODUCTORA (PRODUCTORANOMBRE) VALUES
                                              ('Marvel Studios'),
                                              ('Pixar'),
                                              ('Warner Bros.'),
                                              ('Universal Pictures'),
                                              ('Paramount Pictures');

-- Insertar película (con RESUMEN)
INSERT INTO PELICULA (
    TITULOORIGINAL, IDIOMAORIGINALHABLADOID, PRESUPUESTO, FECHAESTRENO,
    DURACION, RECAUDACION, ESTADO, PAGINAWEB, DESCRIPCION, ESLOGAN,
    URLCARATULA, VOTOS, NOTA, POPULARIDAD
) VALUES
      ('Iron Man', 1, 140000000, '2008-05-02', 126, 585000000,
       'Estrenada', 'https://marvel.com/ironman', 'Un empresario crea una armadura para luchar contra el crimen.',
       'Héroe en armadura', 'https://i.postimg.cc/0jgKktHX/Iron-Man-1-Portada.webp', 800000, 7.9, 90.5),
      ('The Avengers', 1, 220000000, '2012-05-04', 143, 1510000000,
       'Estrenada', 'https://marvel.com/avengers', 'Un grupo de superhéroes se une para salvar el mundo.',
       'Los héroes se unen', 'https://i.postimg.cc/VvD4Gs5T/s-l1200.jpg', 1200000, 8.1, 91.2),
      ('Titanic', 2, 200000000, '1997-12-19', 195, 2200000000,
       'Estrenada', 'https://paramount.com/titanic', 'Una historia de amor en un desastre histórico.',
       'Nunca dejaré ir', 'https://i.postimg.cc/J7qT4xKq/91z-Gp74-Qc4-L.jpg', 1800000, 7.8, 85.7),
      ('SpiderMan 2', 2, 200000000, '1997-12-19', 195, 2200000000,
       'Estrenada', 'https://paramount.com/titanic', 'Una historia de amor en un desastre histórico.',
       'Nunca dejaré ir', 'https://i.postimg.cc/NMvjhWS6/Spider-Man-2.webp', 1800000, 7.8, 85.7);

-- Insertar relación película-género
INSERT INTO PELICULAGENEROPELICULA (GENEROPELICULAID, PELICULAID) VALUES
                                                                      (1, 1),
                                                                      (3, 1),
                                                                      (1, 2),
                                                                      (3, 2),
                                                                      (2, 3);

-- Insertar relación película-palabra clave
INSERT INTO PELICULAPALABRACLAVE (PALABRACLAVEID, PELICULAID) VALUES
                                                                  (1, 1),
                                                                  (2, 1),
                                                                  (3, 2),
                                                                  (4, 2),
                                                                  (5, 3);

-- Insertar relación película-idioma hablado
INSERT INTO PELICULAIDIOMAHABLADO (IDIOMAHABLADOID, PELICULAID) VALUES
                                                                    (1, 1),
                                                                    (2, 1),
                                                                    (1, 2),
                                                                    (2, 2),
                                                                    (2, 3);

-- Insertar relación película-productora
INSERT INTO PELICULAPRODUCTORA (PELICULAID, PRODUCTORAID) VALUES
                                                              (1, 1),
                                                              (2, 1),
                                                              (3, 4);

-- Insertar relación película-país rodaje
INSERT INTO PELICULAPAISRODAJE (PELICULAID, PAISRODAJEID) VALUES
                                                              (1, 1),
                                                              (1, 2),
                                                              (2, 1),
                                                              (2, 3),
                                                              (3, 4);

-- Insertar actuación
INSERT INTO ACTUACION (PERSONAID, PELICULAID, GENEROPERSONAID, PERSONAJE, ORDEN) VALUES
                                                                                     (1, 1, 1, 'Tony Stark / Iron Man', 1),
                                                                                     (2, 1, 2, 'Pepper Potts', 2),
                                                                                     (3, 2, 1, 'Thor', 1),
                                                                                     (4, 2, 2, 'Natasha Romanoff', 2),
                                                                                     (5, 3, 1, 'Jack Dawson', 1),
                                                                                     (1, 3, 1, 'Caledon Hockley', 2);

-- Insertar trabajo
INSERT INTO TRABAJO (PERSONAID, PELICULAID, TRABAJONOMBRE, DEPARTAMENTOID) VALUES
                                                                               (1, 1, 'Actor Principal', 1),
                                                                               (2, 1, 'Actriz Secundaria', 1),
                                                                               (3, 2, 'Actor Principal', 1),
                                                                               (4, 2, 'Actriz Secundaria', 1),
                                                                               (5, 3, 'Actor Principal', 1);

-- Insertar lista de películas
INSERT INTO LISTAPELICULA (LISTAPELICULANOMBRE, USUARIOID) VALUES
                                                               ('Favoritas de Juan', 1),
                                                               ('Pendientes de Ana', 2),
                                                               ('Películas Clásicas', 3);

-- Insertar relación película-lista
INSERT INTO PELICULALISTAPELICULA (PELICULAID, LISTAPELICULAID) VALUES
                                                                    (1, 1),
                                                                    (1, 2),
                                                                    (2, 1),
                                                                    (3, 3);

-- Insertar valoración
INSERT INTO VALORACION (PELICULAID, USUARIOID, NOTA, VALORACIONID) VALUES
                                                                       (1, 1, 8.5, 1),
                                                                       (1, 2, 9.0, 2),
                                                                       (2, 1, 9.5, 3),
                                                                       (2, 2, 8.7, 4),
                                                                       (3, 3, 10.0, 5);
