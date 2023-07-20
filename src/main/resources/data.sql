--初期データを挿入する SQL 文を書いておきます。
--password ハッシュ化する必要がある確認？
INSERT INTO user(name, password, authority)
VALUES('user', '$2a$10$fCiTj4SOdT0pO2D1nGLuh.vBxYaUWxIh.6NgHVvBsgvKjVdpRPB/i', 'ROLE_USER');

--INSERT INTO roles(id, name) VALUES(1, 'ROLE_GENERAL');
--INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');
--
--
--INSERT INTO login_user(id, name, email, password)
--VALUES(1, '一般太郎',　'general@example.com', '$2a$10$arA0UUD2nXYNrnr5Rf2tSem7pZOKJwngISWfralcyanrQYyRxLE/G');
--
--INSERT INTO login_user(id, name, email, password)
--VALUES(2, '管理太郎',　'admin@example.com', '$2a$10$Toz2K2X/aLbSbKpWHhjqxeF1sIgGZrPqwcXCKwOFAMxY9MTUDcbxi');
--
--INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
--INSERT INTO user_role(user_id, role_id) VALUES(2, 1);
--INSERT INTO user_role(user_id, role_id) VALUES(2, 2);


