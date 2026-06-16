CREATE TABLE IF NOT EXISTS alertas (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    insumo_id INT NOT NULL,
    tipo_alerta VARCHAR(50) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha_generacion DATE NOT NULL,
    revisada BOOLEAN NOT NULL DEFAULT FALSE
);
