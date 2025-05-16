-- Insert an admin user into the database

-- 1. User table is already created
INSERT INTO personal_info (name, last_name, maternal_last_name, personal_path)
VALUES ('Alfonso', 'Flores', 'Leal', '/0001/Alfonso_Flores_Leal');

-- 2. Obtener el id_per_info recién insertado (adaptar según SGBD, aquí para MySQL)
SET @id_per_info := LAST_INSERT_ID();

-- 3. Insert the admin user into the user table
INSERT INTO user (num_control, user_password, user_role, id_per_info)
VALUES ('0001', '$2a$10$xu5Y1rV/ovJH2hQuNGeJou3Hs3o1uRxYeUudfJwSu/ztzLQm2muiG', 0, @id_per_info);