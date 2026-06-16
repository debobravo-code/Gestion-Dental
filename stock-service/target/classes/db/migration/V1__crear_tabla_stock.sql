CREATE TABLE IF NOT EXISTS stock (
    id_stock INT AUTO_INCREMENT PRIMARY KEY,
    insumo_id INT NOT NULL UNIQUE,
    cantidad_actual INT NOT NULL DEFAULT 0,
    cantidad_minima INT NOT NULL DEFAULT 0
);
