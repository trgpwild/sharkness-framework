INSERT INTO perfil (id_perfil,descricao) VALUES (1,'ROLE_ADMIN');
INSERT INTO perfil (id_perfil,descricao) VALUES (2,'ROLE_USER');

INSERT INTO usuario (id,login,senha,email,ativo,tentativas_login) VALUES (1,'admin','202cb962ac59075b964b07152d234b70','admin@sharkness.org',1,0);
INSERT INTO usuario (id,login,senha,email,ativo,tentativas_login) VALUES (2,'user','202cb962ac59075b964b07152d234b70','user@sharkness.org',1,0);

INSERT INTO usuario_perfil (id_perfil,id_usuario) VALUES (1,1);
INSERT INTO usuario_perfil (id_perfil,id_usuario) VALUES (2,2);

INSERT INTO contato (id,nome,endereco) VALUES (1,'teste1','endereco1');
INSERT INTO contato (id,nome,endereco) VALUES (2,'teste2','endereco2');
INSERT INTO contato (id,nome,endereco) VALUES (3,'teste3','endereco3');
INSERT INTO contato (id,nome,endereco) VALUES (4,'teste4','endereco4');
INSERT INTO contato (id,nome,endereco) VALUES (5,'teste5','endereco5');
INSERT INTO contato (id,nome,endereco) VALUES (6,'teste6','endereco6');
INSERT INTO contato (id,nome,endereco) VALUES (7,'teste7','endereco7');
INSERT INTO contato (id,nome,endereco) VALUES (8,'teste8','endereco8');
INSERT INTO contato (id,nome,endereco) VALUES (9,'teste9','endereco9');
INSERT INTO contato (id,nome,endereco) VALUES (10,'teste10','endereco10');
INSERT INTO contato (id,nome,endereco) VALUES (11,'teste11','endereco11');
INSERT INTO contato (id,nome,endereco) VALUES (12,'teste12','endereco12');
INSERT INTO contato (id,nome,endereco) VALUES (13,'teste13','endereco13');
INSERT INTO contato (id,nome,endereco) VALUES (14,'teste14','endereco14');
INSERT INTO contato (id,nome,endereco) VALUES (15,'teste15','endereco15');
INSERT INTO contato (id,nome,endereco) VALUES (16,'teste16','endereco16');
INSERT INTO contato (id,nome,endereco) VALUES (17,'teste17','endereco17');
INSERT INTO contato (id,nome,endereco) VALUES (18,'teste18','endereco18');
INSERT INTO contato (id,nome,endereco) VALUES (19,'teste19','endereco19');
INSERT INTO contato (id,nome,endereco) VALUES (20,'teste20','endereco20');