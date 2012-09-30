CREATE DATABASE IF NOT EXISTS contato;
USE contato;

DROP TABLE IF EXISTS `contato`;
CREATE TABLE `contato` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `endereco` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `perfil`;
CREATE TABLE `perfil` (
  `id_perfil` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(40) NOT NULL,
  PRIMARY KEY (`id_perfil`)
);

INSERT INTO `perfil` (`id_perfil`,`descricao`) VALUES 
 (1,'ROLE_APPLICATION'),
 (2,'ROLE_ADMIN');

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `senha` varchar(40) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT NULL,
  `tentativas_login` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

INSERT INTO `usuario` (`id`,`login`,`senha`,`email`,`ativo`,`tentativas_login`) VALUES 
 (1,'admin','202cb962ac59075b964b07152d234b70','admin@sharkness.org',1,0);

DROP TABLE IF EXISTS `usuario_perfil`;
CREATE TABLE `usuario_perfil` (
  `id_perfil` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id_perfil`,`id_usuario`),
  KEY `FK57CD23FDF1C64B0C` (`id_perfil`),
  KEY `FK57CD23FD88195D68` (`id_usuario`),
  KEY `FK57CD23FDD5EE7B79` (`id_perfil`),
  KEY `FK57CD23FD28F73A9B` (`id_usuario`),
  CONSTRAINT `FK57CD23FD28F73A9B` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK57CD23FD88195D68` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK57CD23FDD5EE7B79` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`),
  CONSTRAINT `FK57CD23FDF1C64B0C` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
);

INSERT INTO `usuario_perfil` (`id_perfil`,`id_usuario`) VALUES 
 (1,1),
 (2,1);