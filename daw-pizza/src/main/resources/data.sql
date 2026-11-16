-- ==========================================
-- 1. CLIENTES (Tabla: cliente)
-- ==========================================
INSERT INTO cliente (id, nombre, direccion, email, telefono) VALUES
(1, 'Laura Gómez', 'Calle Mayor 12, 3ºB, Madrid', 'laura.gomez@email.com', '612345678'),
(2, 'Carlos Ruiz', 'Avenida de la Constitución 45, Sevilla', 'cruiz@email.com', '623456789'),
(3, 'Elena Navarro', 'Paseo de Gracia 88, 1ºA, Barcelona', 'elena.navarro@email.com', '634567890'),
(4, 'Javier Torres', 'Calle Larios 5, Málaga', 'jtorres@email.com', '645678901'),
(5, 'Sofía Martínez', 'Plaza del Pilar 3, Zaragoza', 'sofia.mtz@email.com', '656789012');

-- ==========================================
-- 2. PIZZAS (Tabla: pizza)
-- ==========================================
INSERT INTO pizza (id, nombre, descripcion, precio, vegetariana, vegana, disponible) VALUES
(1, 'Margarita', 'Salsa de tomate natural, mozzarella fresca y albahaca.', 9.50, TRUE, FALSE, TRUE),
(2, 'Pepperoni', 'Salsa de tomate, mozzarella y doble ración de pepperoni picante.', 11.50, FALSE, FALSE, TRUE),
(3, 'Cuatro Quesos', 'Mozzarella, gorgonzola, parmesano y queso de cabra.', 12.00, TRUE, FALSE, TRUE),
(4, 'Huerta Vegana', 'Tomate, queso vegano, pimiento rojo, cebolla morada, champiñones y aceitunas.', 12.50, TRUE, TRUE, TRUE),
(5, 'Barbacoa', 'Salsa barbacoa, mozzarella, carne picada de ternera, bacon y cebolla.', 13.00, FALSE, FALSE, TRUE),
(6, 'Trufa y Boletus', 'Crema de trufa negra, mozzarella, boletus salteados y rúcula.', 14.50, TRUE, FALSE, FALSE);

-- ==========================================
-- 3. PEDIDOS (Tabla: pedido)
-- Valores válidos para metodo: 'RECOGER', 'DOMICILIO', 'LOCAL'
-- ==========================================
INSERT INTO pedido (id, fecha, total, metodo, notas, cliente_id) VALUES
(1, '2026-11-10 20:15:00', 21.00, 'DOMICILIO', 'Llamar al telefonillo 3B, el timbre no funciona.', 1),
(2, '2026-11-11 14:30:00', 24.50, 'LOCAL', 'Mesa junto a la ventana.', 2),
(3, '2026-11-12 21:00:00', 12.50, 'RECOGER', 'Sin aceitunas negras, por favor.', 3),
(4, '2026-11-14 21:45:00', 36.50, 'DOMICILIO', NULL, 4),
(5, '2026-11-15 13:50:00', 19.00, 'RECOGER', 'Cortada en 8 porciones.', 1);

-- ==========================================
-- 4. LÍNEAS DE PEDIDO (Tabla: pizza_pedido)
-- ==========================================
-- Pedido 1 (Total: 9.50 + 11.50 = 21.00)
INSERT INTO pizza_pedido (id, cantidad, precio, pedido_id, pizza_id) VALUES
(1, 1.0, 9.50, 1, 1),
(2, 1.0, 11.50, 1, 2);

-- Pedido 2 (Total: 12.00 + 12.50 = 24.50)
INSERT INTO pizza_pedido (id, cantidad, precio, pedido_id, pizza_id) VALUES
(3, 1.0, 12.00, 2, 3),
(4, 1.0, 12.50, 2, 4);

-- Pedido 3 (Total: 12.50)
INSERT INTO pizza_pedido (id, cantidad, precio, pedido_id, pizza_id) VALUES
(5, 1.0, 12.50, 3, 4);

-- Pedido 4 (Total: 2 * 13.00 + 1 * 10.50 [oferta] = 36.50)
INSERT INTO pizza_pedido (id, cantidad, precio, pedido_id, pizza_id) VALUES
(6, 2.0, 13.00, 4, 5),
(7, 1.0, 10.50, 4, 2);

-- Pedido 5 (Total: 2 * 9.50 = 19.00)
INSERT INTO pizza_pedido (id, cantidad, precio, pedido_id, pizza_id) VALUES
(8, 2.0, 9.50, 5, 1);