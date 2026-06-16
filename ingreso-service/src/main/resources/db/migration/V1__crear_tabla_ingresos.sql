CREATE TABLE IF NOT EXISTS ingresos (
    id_ingreso INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    cantidad INT NOT NULL,
    numero_factura VARCHAR(100) NOT NULL,
    observacion VARCHAR(500),
    insumo_id INT NOT NULL,
    proveedor_id INT NOT NULL
);
