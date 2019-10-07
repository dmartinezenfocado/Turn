

create database turn;
use turn;

/*All User's gets stored in APP_USER table*/
create table APP_USER (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);
   
/* USER_PROFILE table contains all possible roles */ 
create table USER_PROFILE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);
   
/* JOIN TABLE for MANY-TO-MANY relationship*/  
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);
  
/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');
  
INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');
  
INSERT INTO USER_PROFILE(type)
VALUES ('DBA');

  
INSERT INTO USER_PROFILE(type)
VALUES ('CAJERO'); 

INSERT INTO USER_PROFILE(type)
VALUES ('SERVICIO');   
  
/* Populate one Admin User which will further create other users for the application using GUI */
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email)
VALUES ('delvin','$2a$10$J/p/wUOTnYD9xujf20ETv.JxLh8e6nJ0bXn9/k3qpjzhiQZyZfyaS', 'Delvin','Martinez','dmartinez@icq24.com');
  
  
/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM app_user user, user_profile profile
  where user.sso_id='delvin' and profile.type='ADMIN';
 
/* Create persistent_logins Table used to store rememberme related stuff*/
CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);






CREATE TABLE `contador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO contador(numero)
VALUES (1);


CREATE TABLE `departamentos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departamento` varchar(200) DEFAULT NULL,
  `prefijo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;



CREATE TABLE `estaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estacion` varchar(200) DEFAULT NULL,
  `departamento` varchar(200) DEFAULT NULL,
  `usuario_fk` int(11) DEFAULT '0',
  `estatus` varchar(200) DEFAULT 'NO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



CREATE TABLE `servicios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_servicio` varchar(45) DEFAULT NULL,
  `departamento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `turno` varchar(200) DEFAULT NULL,
  `servicio` varchar(45) DEFAULT NULL,
  `nombre_cliente` varchar(200) DEFAULT NULL,
  `apellido_cliente` varchar(200) DEFAULT NULL,
  `cedula_cliente` varchar(200) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT CURRENT_TIMESTAMP,
  `fecha_cierre` datetime DEFAULT CURRENT_TIMESTAMP,
  `usuariofk` int(11) DEFAULT NULL,
  `departamento` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;



CREATE TABLE `turnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `turno` varchar(45) DEFAULT NULL,
  `estacion` varchar(200) DEFAULT NULL,
  `usuario_fk` int(11) DEFAULT NULL,
  `duracion` varchar(200) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `servicio` varchar(45) DEFAULT NULL,
  `ticket_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;







