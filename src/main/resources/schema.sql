--ユーザー情報を格納する user テーブルを作成する SQL 文を記載します。
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL UNIQUE,
   password VARCHAR(100) NOT NULL,
   authority VARCHAR(100)
);

--DROP TABLE IF EXISTS user_role;
--DROP TABLE IF EXISTS roles;
--DROP TABLE IF EXISTS login_user;
--
--CREATE TABLE roles
--(
--   id INTEGER PRIMARY KEY,
--   name VARCHAR(32) NOT NULL
--);
--
--CREATE TABLE login_user
--(
--   id INTEGER PRIMARY KEY,
--   name VARCHAR(128) NOT NULL,
--   email VARCHAR(256) NOT NULL,
--   password VARCHAR(128) NOT NULL
--);

--ユーザートロールは多比多
--CREATE TABLE user_role
--(
--   user_id INTEGER,
--   role_id INTEGER,
--   CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id),
--   CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES login_user(id),
--   CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
--);





