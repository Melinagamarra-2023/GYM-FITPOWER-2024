INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('TRAINER');
INSERT INTO roles (name) VALUES ('NUTRITIONIST');


-- Gimnasios
INSERT INTO gyms (id, address, domain, mail, phone, enabled)
VALUES
(1, 'Calle 123', 'gimnasio.com', 'gimnasio@example.com', '1234567890', TRUE),
(2, 'Avenida 456', 'powergym.com', 'info@powergym.com', '0987654321', TRUE);

-- Clientes
INSERT INTO clients (
    cuit, assigned_gym_id, name, lastname, email, phone, enabled
)
VALUES
('20-11223344-5', 1, 'Carlos', 'Ramírez', 'carlos.ramirez@example.com', '1133445566', TRUE),
('27-55667788-9', 2, 'María', 'López', 'maria.lopez@example.com', '1144556677', TRUE);
