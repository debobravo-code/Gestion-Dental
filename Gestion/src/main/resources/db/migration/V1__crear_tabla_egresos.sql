CREATE TABLE IF NOT EXISTS egresos (
    id_egreso INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    observacion VARCHAR(500),
    insumo_id INT NOT NULL,
    destino_id INT NOT NULL
);
