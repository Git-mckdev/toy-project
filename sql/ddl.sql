CREATE DATABASE `toy-project` CHARACTER SET utf8mb4;

USE `toy-project`;

CREATE TABLE `member` (  
    `id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    `email` VARCHAR(255) NOT NULL COMMENT '회원 이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '회원 비밀번호',
    `username` VARCHAR(255) NOT NULL COMMENT '회원 이름',
    `portfolio` VARCHAR(255) NOT NULL COMMENT '포트폴리오 링크',
    `gitHub` VARCHAR(255) NOT NULL COMMENT '깃허브 아이디'
) COMMENT '회원 테이블';

CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    title VARCHAR(255),
    content TEXT,
    create_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (member_id) REFERENCES member(id)
)COMMENT '게시글 테이블';

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    post_id BIGINT,
    content TEXT,
    create_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
)COMMENT '댓글 테이블';