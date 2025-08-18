INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('TRAINER');
INSERT INTO roles (name) VALUES ('NUTRITIONIST');

-- users
INSERT INTO users (cuit, email, password) VALUES
('20-00000000-0', 'a@a.com', '$2a$10$8LvodzfUMHXMBJdI5rFuRu0gexDBcltXtzF8O/mfBZ2IpxO5akfQG'),--dato de pruebas
('11-11111111-1', 'trainer@trainer.com', '$2a$10$2GB1QtXuLx/7p0huq8CRe.bjhKrZwQ7c1PuSiC/kfA0KkueRmJPF2'),
('22-22222222-2', 'client@client.com', '$2a$10$3Mlnzoi3xXrs1JtRUmByCeuSccqQibomi5mQEAxkc0RKvTAOAAANi'),
('33-33333333-3', 'nutri@nutri.com', '$2a$10$T4B5mdXG55I4mR7o7K5yBOIEwqSmwmsGVLClLe0D/AjV7H9iTb3e6');

INSERT INTO users_roles (user_id, role_id) VALUES(1, 2), (2, 3), (3, 1), (4, 4);

INSERT INTO trainers (cuit, name, lastname, email, phone_number, enabled,created_at) VALUES
('11111111111', 'trainer', 'trainer', 'trainer@trainer.com', '1111111111', TRUE,'2025-08-29');

INSERT INTO nutritionists (cuit, name, lastname, email, phone_number, enabled,created_at) VALUES
('33333333333', 'nutri', 'nutri', 'nutri@nutri.com', '3333333333', TRUE,'2025-08-29');

INSERT INTO gyms (address, domain, mail, phone, enabled) VALUES ('Calle 123', 'gimnasio1.com', 'gimnasio1@example', '1234567890', TRUE);
INSERT INTO gyms (address, domain, mail, phone, enabled) VALUES ('Calle 456', 'gimnasio2.com', 'gimnasio2@example', '9876543210', TRUE);
INSERT INTO gyms (address, domain, mail, phone, enabled) VALUES ('Calle 789', 'gimnasio3.com', 'gimnasio3@example', '5555555555', TRUE);

INSERT INTO clients (birth_date,cuit, name, lastname, email, phone_number, enabled,assigned_gym_id,assigned_nutritionist_id,assigned_trainer_id)VALUES
('2025-08-29','22222222222', 'client', 'client', 'client@client.com', '2222222222', TRUE,1,1,1);

INSERT INTO exercises (name, description, muscle_group, enabled) VALUES
('Sentadillas', 'Ejercicio para fortalecer los músculos de las piernas', 'Piernas', TRUE),
('Peso muerto', 'Ejercicio para fortalecer los músculos de la espalda y piernas', 'Espalda y piernas', TRUE),
('Press de banca', 'Ejercicio para fortalecer los músculos del pecho', 'Pecho', TRUE),
('Remo', 'Ejercicio para fortalecer los músculos de la espalda', 'Espalda', TRUE),
('Elevaciones laterales', 'Ejercicio para fortalecer los músculos de los hombros', 'Hombros', TRUE),
('Extensiones de piernas', 'Ejercicio para fortalecer los músculos de las piernas', 'Piernas', TRUE),
('Curl de bíceps', 'Ejercicio para fortalecer los músculos de los brazos', 'Brazos', TRUE),
('Tríceps', 'Ejercicio para fortalecer los músculos de los brazos', 'Brazos', TRUE),
('Abdominales', 'Ejercicio para fortalecer los músculos del abdomen', 'Abdomen', TRUE),
('Elevaciones de piernas', 'Ejercicio para fortalecer los músculos de las piernas', 'Piernas', TRUE),
('Peso muerto con mancuernas', 'Ejercicio para fortalecer los músculos de la espalda y piernas', 'Espalda y piernas', TRUE),
('Press de hombros', 'Ejercicio para fortalecer los músculos de los hombros', 'Hombros', TRUE),
('Extensiones de brazos', 'Ejercicio para fortalecer los músculos de los brazos', 'Brazos', TRUE),
('Curl de tríceps', 'Ejercicio para fortalecer los músculos de los brazos', 'Brazos', TRUE),
('Elevaciones de hombros', 'Ejercicio para fortalecer los músculos de los hombros', 'Hombros', TRUE),
('Abdominales con peso', 'Ejercicio para fortalecer los músculos del abdomen', 'Abdomen', TRUE),
('Sentadillas con mancuernas', 'Ejercicio para fortalecer los músculos de las piernas', 'Piernas', TRUE),
('Peso muerto con barra', 'Ejercicio para fortalecer los músculos de la espalda y piernas', 'Espalda y piernas', TRUE);

INSERT INTO routines (goals, created_at, trainer_id, client_id, active) VALUES
('Perder 5 kg en 3 meses', '2022-01-01', 1, 1, FALSE);

INSERT INTO exercise_sets (reps, sets, rest_in_minutes, routine_id, exercise_id) VALUES
(10, 3, 30, 1, 1),
(12, 4, 45, 1, 2);

INSERT INTO routines (goals, created_at, trainer_id, client_id, active) VALUES
('Mejorar la técnica de levantamiento de pesas', '2022-04-01', 1, 1, FALSE);

INSERT INTO exercise_sets (reps, sets, rest_in_minutes, routine_id, exercise_id) VALUES
(12, 4, 60, 2, 11),
(10, 3, 30, 2, 12);

INSERT INTO routines (goals, created_at, trainer_id, client_id, active) VALUES
('Aumentar la flexibilidad y movilidad en 3 meses', '2022-04-15', 1, 1, FALSE);

INSERT INTO exercise_sets (reps, sets, rest_in_minutes, routine_id, exercise_id) VALUES
(15, 5, 45, 3, 12),
(10, 3, 60, 3, 13);

INSERT INTO routines (goals, created_at, trainer_id, client_id, active) VALUES
('Aumentar la flexibilidad y movilidad en 3 meses', '2022-04-15', 1, 1, FALSE);

INSERT INTO exercise_sets (reps, sets, rest_in_minutes, routine_id, exercise_id) VALUES
(12, 4, 30, 4, 3),
(15, 5, 45, 4, 8),
(10, 3, 60, 4, 9);

INSERT INTO routines (goals, created_at, trainer_id, client_id, active) VALUES
('Mejorar la resistencia cardiovascular en 6 meses', '2022-05-15', 1, 1, TRUE);

INSERT INTO exercise_sets (reps, sets, rest_in_minutes, routine_id, exercise_id) VALUES
(10, 3, 30, 5, 4),
(12, 4, 45, 5, 10);

INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 1', '2022-01-01 08:00:00', 1);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 2', '2022-01-02 09:30:00', 1);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 3', '2022-01-03 10:45:00', 1);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 4', '2022-01-04 12:15:00', 1);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 5', '2022-01-05 14:30:00', 1);

INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 1', '2022-01-01 08:00:00', 2);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 2', '2022-01-02 09:30:00', 2);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 3', '2022-01-03 10:45:00', 2);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 4', '2022-01-04 12:15:00', 2);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 5', '2022-01-05 14:30:00', 2);

INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 1', '2022-01-01 08:00:00', 3);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 2', '2022-01-02 09:30:00', 3);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 3', '2022-01-03 10:45:00', 3);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 4', '2022-01-04 12:15:00', 3);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 5', '2022-01-05 14:30:00', 3);

INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 1', '2022-01-01 08:00:00', 4);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 2', '2022-01-02 09:30:00', 4);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 3', '2022-01-03 10:45:00', 4);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 4', '2022-01-04 12:15:00', 4);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 5', '2022-01-05 14:30:00', 4);

INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 1', '2022-01-01 08:00:00', 5);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 2', '2022-01-02 09:30:00', 5);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 3', '2022-01-03 10:45:00', 5);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 4', '2022-01-04 12:15:00', 5);
INSERT INTO training_diary (commentary, created_at, routine_id) VALUES ('Comentario 5', '2022-01-05 14:30:00', 5);
