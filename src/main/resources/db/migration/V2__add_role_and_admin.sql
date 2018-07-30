INSERT INTO roles(id, name) VALUES(1, 'USER');
INSERT INTO roles(id, name) VALUES(2, 'ADMIN');

INSERT INTO public.users (id, email, name, password, username) VALUES (1, 'admin@i.ua', 'admin', '$2a$10$QyT3z3a2//BD/3wzeTORAOoA6xlYqY8JUU2QVjPl2YKdYffLAoLR6', 'admin');
INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 2);