-- User Table Creation Script
CREATE TABLE user
(
    id_user       INT PRIMARY KEY AUTO_INCREMENT, -- Clave primaria autoincrementable
    num_control   VARCHAR(100) NOT NULL,          -- Número de control
    user_password VARCHAR(255) NOT NULL,          -- Contraseña
    user_role     INT,                            -- Rol del usuario
    id_per_info   INT,                            -- Clave foránea hacia personal_info
    CONSTRAINT fk_user_personal_info FOREIGN KEY (id_per_info) REFERENCES personal_info (id_per_info) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;