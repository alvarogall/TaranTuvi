USE TaranTuvi;

TRUNCATE TABLE USUARIO;
TRUNCATE TABLE ROL;

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
