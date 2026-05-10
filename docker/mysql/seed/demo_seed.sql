-- Seed de demostración: usuarios, clientes, lecturas, factura proveedor, recibos, pagos, cargos extra y eventos.
-- Ejecutar contra la BD luz_colectiva (se debe correr manualmente tras levantar MySQL).

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

UPDATE app_settings SET updated_by = NULL WHERE id = 1;

DELETE FROM customer_service_events;
DELETE FROM extra_charge_payments;
DELETE FROM extra_charge_assignments;
DELETE FROM extra_charges;
DELETE FROM payments;
DELETE FROM receipt_items;
DELETE FROM receipts;
DELETE FROM meter_readings;
DELETE FROM provider_bills;
DELETE FROM customers;
DELETE FROM profiles WHERE user_id IN (
  '11111111-1111-1111-1111-111111111111',
  '22222222-2222-2222-2222-222222222222'
);
DELETE FROM users WHERE id IN (
  '11111111-1111-1111-1111-111111111111',
  '22222222-2222-2222-2222-222222222222'
);

SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------------------------
-- Usuarios y perfiles
-- ---------------------------------------------------------------------------
INSERT INTO users (id, full_name, email, phone) VALUES
  ('11111111-1111-1111-1111-111111111111', 'Demo Administrador', 'admin@luzcolectiva.local', '959100001'),
  ('22222222-2222-2222-2222-222222222222', 'María Cobradora', 'cobrador@luzcolectiva.local', '959100002');

INSERT INTO profiles (user_id, role, status) VALUES
  ('11111111-1111-1111-1111-111111111111', 'admin', 'active'),
  ('22222222-2222-2222-2222-222222222222', 'collector', 'active');

UPDATE app_settings SET updated_by = '11111111-1111-1111-1111-111111111111' WHERE id = 1;

-- ---------------------------------------------------------------------------
-- Clientes
-- ---------------------------------------------------------------------------
INSERT INTO customers (
  id, registration_number, first_name, last_name, document_number, phone,
  block, lot, address_reference, status, notes, created_by, updated_by
) VALUES
  (
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    'REG-001',
    'Juan',
    'Pérez',
    '12345678',
    '959200001',
    'B-1',
    'L-5',
    'Frente al parque',
    'active',
    'Cliente de prueba — al día en lecturas',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000002',
    'REG-002',
    'Rosa',
    'Quispe',
    '87654321',
    '959200002',
    'B-2',
    'L-10',
    NULL,
    'active',
    NULL,
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000003',
    NULL,
    'Carlos',
    'Mamani',
    NULL,
    '959200003',
    'B-1',
    'L-6',
    NULL,
    'suspended',
    'Suspendido — escenario de corte/reconexión',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  );

-- ---------------------------------------------------------------------------
-- Factura proveedor
-- ---------------------------------------------------------------------------
INSERT INTO provider_bills (
  id, period_year, period_month, provider_name, provider_receipt_number,
  collective_contract_number, issue_date, due_date,
  previous_reading, current_reading, provider_consumption_kwh, provider_total_amount,
  seal_kwh_reference_price, collective_kwh_price, status, notes,
  created_by, updated_by
) VALUES
  (
    'bbbbbbbb-bbbb-bbbb-bbbb-000000000001',
    2026,
    5,
    'SEAL',
    'SEAL-2026-05432',
    'CONT-COL-001',
    '2026-05-05',
    '2026-05-20',
    10000.00,
    12500.00,
    2500.00,
    4500.00,
    0.4520,
    0.6500,
    'active',
    'Factura proveedor de prueba',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  );

-- ---------------------------------------------------------------------------
-- Lecturas de medidor
-- ---------------------------------------------------------------------------
INSERT INTO meter_readings (
  id, customer_id, period_year, period_month,
  previous_reading, current_reading, reading_date, notes, created_by, updated_by
) VALUES
  (
    'cccccccc-cccc-cccc-cccc-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    2026,
    4,
    1000.00,
    1150.00,
    '2026-04-28',
    'Abril',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  ),
  (
    'cccccccc-cccc-cccc-cccc-000000000002',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    2026,
    5,
    1150.00,
    1380.00,
    '2026-05-28',
    'Mayo',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  ),
  (
    'cccccccc-cccc-cccc-cccc-000000000003',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000002',
    2026,
    5,
    500.00,
    890.00,
    '2026-05-29',
    NULL,
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  );

-- ---------------------------------------------------------------------------
-- Recibos
-- ---------------------------------------------------------------------------
INSERT INTO receipts (
  id, customer_id, meter_reading_id, provider_bill_id, receipt_number,
  period_year, period_month, issue_date, due_date,
  subtotal, total, paid_amount, balance,
  seal_kwh_reference_price, collective_kwh_price, status, notes,
  created_by, updated_by
) VALUES
  (
    'dddddddd-dddd-dddd-dddd-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    'cccccccc-cccc-cccc-cccc-000000000002',
    'bbbbbbbb-bbbb-bbbb-bbbb-000000000001',
    'R-2026-05-001',
    2026,
    5,
    '2026-05-10',
    '2026-05-17',
    155.20,
    155.20,
    0.00,
    155.20,
    0.4520,
    0.6500,
    'pending',
    'Recibo pendiente de pago',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  ),
  (
    'dddddddd-dddd-dddd-dddd-000000000002',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000002',
    'cccccccc-cccc-cccc-cccc-000000000003',
    'bbbbbbbb-bbbb-bbbb-bbbb-000000000001',
    'R-2026-05-002',
    2026,
    5,
    '2026-05-10',
    '2026-05-17',
    268.40,
    268.40,
    100.00,
    168.40,
    0.4520,
    0.6500,
    'partially_paid',
    'Pago parcial registrado',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  );

-- ---------------------------------------------------------------------------
-- Ítems de recibo (Juan)
-- ---------------------------------------------------------------------------
INSERT INTO receipt_items (
  id, receipt_id, concept, description, quantity, unit_price, amount, item_type, sort_order
) VALUES
  (
    'eeeeeeee-eeee-eeee-eeee-000000000001',
    'dddddddd-dddd-dddd-dddd-000000000001',
    'Consumo kWh',
    '230 kWh × precio comité',
    230.00,
    0.6500,
    149.50,
    'energy_consumption',
    1
  ),
  (
    'eeeeeeee-eeee-eeee-eeee-000000000002',
    'dddddddd-dddd-dddd-dddd-000000000001',
    'Cargo mínimo',
    NULL,
    1.00,
    5.00,
    5.00,
    'minimum_payment',
    2
  ),
  (
    'eeeeeeee-eeee-eeee-eeee-000000000003',
    'dddddddd-dddd-dddd-dddd-000000000001',
    'Impresión',
    NULL,
    1.00,
    0.70,
    0.70,
    'printing',
    3
  );

-- Ítems de recibo (Rosa)
INSERT INTO receipt_items (
  id, receipt_id, concept, description, quantity, unit_price, amount, item_type, sort_order
) VALUES
  (
    'eeeeeeee-eeee-eeee-eeee-000000000004',
    'dddddddd-dddd-dddd-dddd-000000000002',
    'Consumo kWh',
    '390 kWh × precio comité',
    390.00,
    0.6500,
    253.50,
    'energy_consumption',
    1
  ),
  (
    'eeeeeeee-eeee-eeee-eeee-000000000005',
    'dddddddd-dddd-dddd-dddd-000000000002',
    'Cargo mínimo',
    NULL,
    1.00,
    5.00,
    5.00,
    'minimum_payment',
    2
  ),
  (
    'eeeeeeee-eeee-eeee-eeee-000000000006',
    'dddddddd-dddd-dddd-dddd-000000000002',
    'Redondeo',
    NULL,
    1.00,
    9.90,
    9.90,
    'rounding',
    3
  );

-- ---------------------------------------------------------------------------
-- Pagos
-- ---------------------------------------------------------------------------
INSERT INTO payments (
  id, receipt_id, customer_id, payment_date, amount, payment_method,
  reference_number, notes, status, created_by, updated_by
) VALUES
  (
    'ffffffff-ffff-ffff-ffff-000000000001',
    'dddddddd-dddd-dddd-dddd-000000000002',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000002',
    '2026-05-12',
    100.00,
    'yape',
    'YAPE-8899',
    'Abono parcial',
    'valid',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  );

-- ---------------------------------------------------------------------------
-- Cargos extra
-- ---------------------------------------------------------------------------
INSERT INTO extra_charges (
  id, name, description, amount, start_date, due_date, status, created_by, updated_by
) VALUES
  (
    '99999999-9999-9999-9999-000000000001',
    'Mantenimiento red mayo',
    'Aporte extraordinario por mantenimiento',
    30.00,
    '2026-05-01',
    '2026-05-31',
    'active',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  );

INSERT INTO extra_charge_assignments (
  id, extra_charge_id, customer_id, amount, paid_amount, balance, status, created_by, updated_by
) VALUES
  (
    'aaaaaaaa-bbbb-cccc-dddd-000000000001',
    '99999999-9999-9999-9999-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    30.00,
    10.00,
    20.00,
    'partially_paid',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'aaaaaaaa-bbbb-cccc-dddd-000000000002',
    '99999999-9999-9999-9999-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000002',
    30.00,
    0.00,
    30.00,
    'pending',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  );

INSERT INTO extra_charge_payments (
  id, extra_charge_assignment_id, extra_charge_id, customer_id,
  payment_date, amount, payment_method, reference_number, notes, status,
  created_by, updated_by
) VALUES
  (
    'bbbbbbbb-cccc-dddd-eeee-000000000001',
    'aaaaaaaa-bbbb-cccc-dddd-000000000001',
    '99999999-9999-9999-9999-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000001',
    '2026-05-08',
    10.00,
    'cash',
    NULL,
    'Pago cargo extra',
    'valid',
    '22222222-2222-2222-2222-222222222222',
    '22222222-2222-2222-2222-222222222222'
  );

-- ---------------------------------------------------------------------------
-- Eventos de servicio (corte / reconexión)
-- ---------------------------------------------------------------------------
INSERT INTO customer_service_events (
  id, customer_id, event_type, event_date, reason, amount, charge_status, receipt_id, notes,
  created_by, updated_by
) VALUES
  (
    'cccccccc-dddd-eeee-ffff-000000000001',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000003',
    'cut',
    '2026-04-15',
    'Deuda vencida',
    0.00,
    'none',
    NULL,
    'Corte de prueba',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'cccccccc-dddd-eeee-ffff-000000000002',
    'aaaaaaaa-aaaa-aaaa-aaaa-000000000003',
    'reconnection',
    '2026-05-08',
    'Pago acordado',
    25.00,
    'pending',
    NULL,
    'Reconexión pendiente de facturar en recibo',
    '11111111-1111-1111-1111-111111111111',
    '11111111-1111-1111-1111-111111111111'
  );
