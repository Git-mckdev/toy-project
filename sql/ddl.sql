CREATE DATABASE `toy-project` CHARACTER SET utf8mb4;

USE `toy-project`;

CREATE TABLE `member` (  
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    `email` VARCHAR(255) NOT NULL COMMENT '회원 이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '회원 비밀번호',
    `username` VARCHAR(255) NOT NULL COMMENT '회원 이름'
) COMMENT '회원 테이블';