--DROP TABLE IF EXISTS USER;
--DROP TABLE IF EXISTS ROLE;
--DROP TABLE IF EXISTS USER_ROLES;
--
--CREATE TABLE USER (
--    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    username VARCHAR(255) UNIQUE NOT NULL,
--    password VARCHAR(255) not null,
--    name VARCHAR(255) not null,
--    email VARCHAR(255) not null,
--    img TEXT not null DEFAULT 'https://firebasestorage.googleapis.com/v0/b/userprofile-6e688.appspot.com/o/user%2Fdefault.png?alt=media&token=c465c073-1626-4c0c-b894-360013aeeba1'
--);
--
--CREATE TABLE ROLE (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(255) UNIQUE not null
--);
--
--INSERT INTO ROLE
--VALUES  (DEFAULT, 'ROLE_USER'),
--        (DEFAULT, 'ROLE_MANAGER'),
--        (DEFAULT, 'ROLE_ADMIN');
--
--CREATE TABLE USER_ROLES (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    user_id BIGINT not null,
--    role_id BIGINT not null
--);

CREATE TABLE OAUTH2_USER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT not null,
    oauth2_name VARCHAR(255) UNIQUE not null,
    provider VARCHAR(255) not null
);

--ALTER TABLE USER ADD COLUMN email VARCHAR(255) NOT NULL;