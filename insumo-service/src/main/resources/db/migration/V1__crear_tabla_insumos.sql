CREATE TABLE IF NOT EXISTS insumos (
    id_insumo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(50) NOT NULL,
    fecha_vencimiento DATE,
    stock_minimo INT NOT NULL
);
