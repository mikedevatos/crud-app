BEGIN;
ALTER TABLE roles DISABLE TRIGGER ALL;
ALTER TABLE users DISABLE TRIGGER ALL;
ALTER TABLE user_roles DISABLE TRIGGER ALL;

ALTER TABLE customer DISABLE TRIGGER ALL;


--  encoding  with Bcrypt also change config in SecurityConfiguration
insert INTO users (username,password) values ('user1','abcd1');
insert INTO users (username,password) values ('user2','abcd2');
insert INTO users (username,password) values ('user3','abcd3');
insert INTO users (username,password) values ('user4','abcd4');
insert INTO users (username,password) values ('user5','abcd5');



INSERT INTO roles (type_role) values ('ADMIN');
INSERT INTO roles (type_role) values ('STAFF');
INSERT INTO roles (type_role) values ('CUSTOMER');

INSERT INTO user_roles(user_id,roles_id) VALUES (1,1);
INSERT INTO user_roles(user_id,roles_id) VALUES (2,2);
INSERT INTO user_roles(user_id,roles_id) VALUES (3,3);
INSERT INTO user_roles(user_id,roles_id) VALUES (4,3);
INSERT INTO user_roles(user_id,roles_id) VALUES (5,3);

INSERT INTO customer(last_name,email,user_id)
VALUES('lastname1','email1','3');

ALTER TABLE roles ENABLE TRIGGER ALL;
ALTER TABLE users ENABLE TRIGGER ALL;
ALTER TABLE user_roles ENABLE TRIGGER ALL;
ALTER TABLE customer ENABLE TRIGGER ALL;

commit;
