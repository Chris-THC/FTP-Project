-- Personal Information Table Creation Script
CREATE TABLE personal_info
(
    id_per_info        INT PRIMARY KEY AUTO_INCREMENT, -- Clave primaria autoincrementable
    name               VARCHAR(100) NOT NULL,          -- Nombre
    last_name          VARCHAR(100) NOT NULL,          -- Apellido paterno
    maternal_last_name VARCHAR(100),                   -- Apellido materno
    personal_path      VARCHAR(500)                    -- Ruta personal
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;