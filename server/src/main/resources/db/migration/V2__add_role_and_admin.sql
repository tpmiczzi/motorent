INSERT INTO roles(id, name) VALUES(1, 'USER');
INSERT INTO roles(id, name) VALUES(2, 'ADMIN');

INSERT INTO public.users (id, email, name, password, username) VALUES (1, 'admin@i.ua', 'admin', '$2a$10$Tz.bnrCWHwcY/xj8wjbPyOtIMxz0fgYZa1Q5g4lkUXqC2bKRauUSq', 'admin');
INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO public.users (id, email, name, password, username) VALUES (2, 'user@i.ua', 'user', '$2a$10$Tz.bnrCWHwcY/xj8wjbPyOtIMxz0fgYZa1Q5g4lkUXqC2bKRauUSq', 'user');
INSERT INTO public.user_roles (user_id, role_id) VALUES (2, 1);