INSERT INTO usuarios(enabled,password,username,email,nombre,apellido)VALUES(true,'$2a$10$AYPGcHKruhEKV.CmEMo/6e30IBuvaZ1pHkfUU1ddORjsQe30SRB/.','victorcg','cruz.gomez.victor@gmail.com','victor','cruz');
INSERT INTO usuarios(enabled,password,username,email,nombre,apellido)VALUES(true,'$2a$10$dzWbykuRHjKUrX1GiYO/p.6JQJQma0dX7As7N2BVsEcNw0SafqKPa','gracecg','cruz.gomez.grace@gmail.com','grace','cruz');

INSERT INTO roles(nombre)VALUES('ROLE_USER');
INSERT INTO roles(nombre)VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles(usuario_id, rol_id)VALUES(1,1);
INSERT INTO usuarios_roles(usuario_id, rol_id)VALUES(2,2);
INSERT INTO usuarios_roles(usuario_id, rol_id)VALUES(2,1);