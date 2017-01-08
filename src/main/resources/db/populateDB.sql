DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO users (id, name, email, password)
VALUES (100000,'User', 'user@yandex.ru', 'password');

INSERT INTO users (id, name, email, password)
VALUES (100001,'Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100002, '01.05.2015 09:00:37', 100000, 'Завтрак', 100);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100003, '01.05.2015 19:37:04', 100000, 'Ужин', 100);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100004, '02.05.2015 09:12:45', 100000, 'Завтрак', 500);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100005, '02.05.2015 14:37:04', 100000, 'Обед', 2000);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100006, '03.05.2015 09:00:37', 100000, 'Завтрак', 1500);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100007, '03.05.2015 15:08:32', 100000, 'Обед', 1000);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100008, '01.05.2015 07:14:22', 100001, 'Завтрак', 100);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100009, '01.05.2015 18:34:18', 100001, 'Ужин', 100);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100010, '02.05.2015 08:24:02', 100001, 'Завтрак', 1000);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100011, '02.05.2015 13:27:03', 100001, 'Обед', 1500);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100012, '03.05.2015 10:04:00', 100001, 'Завтрак', 1700);
INSERT INTO meals (id, date_time, user_id, description, calories) VALUES
  (100013, '03.05.2015 16:14:04', 100001, 'Обед', 900);

ALTER SEQUENCE global_seq RESTART WITH 100014;