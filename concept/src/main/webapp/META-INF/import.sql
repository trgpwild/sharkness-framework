INSERT INTO perfil (id_perfil,descricao) VALUES (1,'ROLE_ADMIN');
INSERT INTO perfil (id_perfil,descricao) VALUES (2,'ROLE_USER');

INSERT INTO usuario (id,login,senha,email,ativo,tentativas_login) VALUES (1,'admin','202cb962ac59075b964b07152d234b70','admin@sharkness.org',1,0);
INSERT INTO usuario (id,login,senha,email,ativo,tentativas_login) VALUES (2,'user','202cb962ac59075b964b07152d234b70','user@sharkness.org',1,0);

INSERT INTO usuario_perfil (id_perfil,id_usuario) VALUES (1,1);
INSERT INTO usuario_perfil (id_perfil,id_usuario) VALUES (2,2);