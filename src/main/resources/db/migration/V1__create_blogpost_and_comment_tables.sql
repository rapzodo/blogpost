-- V1__create_blogpost_and_comment_tables.sql

CREATE TABLE PUBLIC.blog_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE PUBLIC.comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    blog_post_id BIGINT,
    FOREIGN KEY (blog_post_id) REFERENCES blog_post(id)
);

INSERT INTO PUBLIC.blog_post (title, content) VALUES ('First Post', 'This is my first post');
INSERT INTO PUBLIC.blog_post (title, content) VALUES ('Second Post', 'This is my second post');
INSERT INTO PUBLIC.blog_post (title, content) VALUES ('Third Post', 'This is my third post');
