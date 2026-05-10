SET NAMES utf8mb4;

-- ---------------------------------------------------------------------------
-- app_settings
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS app_settings (
  id INT NOT NULL DEFAULT 1,
  organization_name VARCHAR(512) NOT NULL,
  currency_code VARCHAR(16) NOT NULL DEFAULT 'PEN',
  energy_unit_label VARCHAR(64) NOT NULL DEFAULT 'kWh',
  energy_unit_price DECIMAL(12, 2) NOT NULL DEFAULT 0,
  minimum_payment DECIMAL(12, 2) NOT NULL DEFAULT 0,
  technical_payment DECIMAL(12, 2) NOT NULL DEFAULT 0,
  printing_cost DECIMAL(12, 2) NOT NULL DEFAULT 0,
  reconnection_fee DECIMAL(12, 2) NOT NULL DEFAULT 0,
  default_due_days INT NOT NULL DEFAULT 7,
  rounding_enabled TINYINT(1) NOT NULL DEFAULT 1,
  rounding_increment DECIMAL(12, 2) NOT NULL DEFAULT 0.10,
  rounding_mode VARCHAR(32) NOT NULL DEFAULT 'ceil',
  collective_contract_number VARCHAR(128) NULL,
  seal_kwh_price DECIMAL(12, 2) NOT NULL DEFAULT 0,
  collective_kwh_price DECIMAL(12, 2) NOT NULL DEFAULT 0,
  location_label VARCHAR(255) NULL DEFAULT 'Arequipa, Cayma',
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  CONSTRAINT app_settings_singleton_check CHECK (id = 1),
  CONSTRAINT app_settings_energy_unit_price_check CHECK (energy_unit_price >= 0),
  CONSTRAINT app_settings_minimum_payment_check CHECK (minimum_payment >= 0),
  CONSTRAINT app_settings_technical_payment_check CHECK (technical_payment >= 0),
  CONSTRAINT app_settings_printing_cost_check CHECK (printing_cost >= 0),
  CONSTRAINT app_settings_reconnection_fee_check CHECK (reconnection_fee >= 0),
  CONSTRAINT app_settings_default_due_days_check CHECK (default_due_days >= 0),
  CONSTRAINT app_settings_rounding_increment_check CHECK (rounding_increment > 0),
  CONSTRAINT app_settings_rounding_mode_check CHECK (rounding_mode IN ('ceil')),
  CONSTRAINT app_settings_seal_kwh_price_check CHECK (seal_kwh_price >= 0),
  CONSTRAINT app_settings_collective_kwh_price_check CHECK (collective_kwh_price >= 0),
  CONSTRAINT app_settings_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO app_settings (
  id,
  organization_name,
  currency_code,
  energy_unit_label,
  energy_unit_price,
  minimum_payment,
  technical_payment,
  printing_cost,
  reconnection_fee,
  default_due_days,
  rounding_enabled,
  rounding_increment,
  rounding_mode,
  collective_contract_number,
  seal_kwh_price,
  collective_kwh_price,
  location_label
)
SELECT
  1,
  'Comité de Electrificación Colectiva',
  'PEN',
  'kWh',
  0,
  0,
  0,
  0,
  0,
  7,
  1,
  0.10,
  'ceil',
  NULL,
  0,
  0,
  'Arequipa, Cayma'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM app_settings WHERE id = 1);

-- ---------------------------------------------------------------------------
-- customers
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS customers (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  registration_number VARCHAR(128) NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  document_number VARCHAR(64) NULL,
  phone VARCHAR(64) NULL,
  block VARCHAR(64) NOT NULL,
  lot VARCHAR(64) NOT NULL,
  address_reference VARCHAR(512) NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'active',
  notes TEXT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY customers_block_lot_key (block, lot),
  CONSTRAINT customers_status_check CHECK (status IN ('active', 'inactive', 'suspended')),
  CONSTRAINT customers_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT customers_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- meter_readings
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS meter_readings (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  customer_id CHAR(36) NOT NULL,
  period_year INT NOT NULL,
  period_month INT NOT NULL,
  previous_reading DECIMAL(12, 2) NOT NULL,
  current_reading DECIMAL(12, 2) NOT NULL,
  consumption DECIMAL(12, 2) GENERATED ALWAYS AS (current_reading - previous_reading) STORED,
  reading_date DATE NOT NULL,
  notes TEXT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY meter_readings_customer_period_key (customer_id, period_year, period_month),
  CONSTRAINT meter_readings_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT meter_readings_period_year_check CHECK (period_year BETWEEN 2000 AND 2100),
  CONSTRAINT meter_readings_period_month_check CHECK (period_month BETWEEN 1 AND 12),
  CONSTRAINT meter_readings_previous_reading_check CHECK (previous_reading >= 0),
  CONSTRAINT meter_readings_current_reading_check CHECK (current_reading >= 0),
  CONSTRAINT meter_readings_current_gte_previous_check CHECK (current_reading >= previous_reading),
  CONSTRAINT meter_readings_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT meter_readings_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- provider_bills
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS provider_bills (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  period_year INT NOT NULL,
  period_month INT NOT NULL,
  provider_name VARCHAR(128) NOT NULL DEFAULT 'SEAL',
  provider_receipt_number VARCHAR(128) NULL,
  collective_contract_number VARCHAR(128) NULL,
  issue_date DATE NULL,
  due_date DATE NULL,
  previous_reading DECIMAL(12, 2) NULL,
  current_reading DECIMAL(12, 2) NULL,
  provider_consumption_kwh DECIMAL(12, 2) NULL,
  provider_total_amount DECIMAL(12, 2) NULL,
  seal_kwh_reference_price DECIMAL(12, 4) NOT NULL DEFAULT 0,
  collective_kwh_price DECIMAL(12, 4) NOT NULL DEFAULT 0,
  status VARCHAR(32) NOT NULL DEFAULT 'draft',
  notes TEXT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY provider_bills_period_key (period_year, period_month),
  CONSTRAINT provider_bills_period_month_check CHECK (period_month BETWEEN 1 AND 12),
  CONSTRAINT provider_bills_period_year_check CHECK (period_year BETWEEN 2000 AND 2100),
  CONSTRAINT provider_bills_provider_consumption_kwh_check CHECK (
    provider_consumption_kwh IS NULL OR provider_consumption_kwh >= 0
  ),
  CONSTRAINT provider_bills_provider_total_amount_check CHECK (
    provider_total_amount IS NULL OR provider_total_amount >= 0
  ),
  CONSTRAINT provider_bills_seal_kwh_reference_price_check CHECK (seal_kwh_reference_price >= 0),
  CONSTRAINT provider_bills_collective_kwh_price_check CHECK (collective_kwh_price >= 0),
  CONSTRAINT provider_bills_previous_reading_check CHECK (
    previous_reading IS NULL OR previous_reading >= 0
  ),
  CONSTRAINT provider_bills_current_reading_check CHECK (
    current_reading IS NULL OR current_reading >= 0
  ),
  CONSTRAINT provider_bills_current_gte_previous_check CHECK (
    previous_reading IS NULL OR current_reading IS NULL OR current_reading >= previous_reading
  ),
  CONSTRAINT provider_bills_status_check CHECK (status IN ('draft', 'active', 'closed', 'cancelled')),
  CONSTRAINT provider_bills_due_date_check CHECK (
    due_date IS NULL OR issue_date IS NULL OR due_date >= issue_date
  ),
  CONSTRAINT provider_bills_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT provider_bills_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- receipts
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS receipts (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  customer_id CHAR(36) NOT NULL,
  meter_reading_id CHAR(36) NULL,
  provider_bill_id CHAR(36) NULL,
  receipt_number VARCHAR(128) NULL,
  period_year INT NOT NULL,
  period_month INT NOT NULL,
  issue_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  due_date DATE NULL,
  subtotal DECIMAL(12, 2) NOT NULL DEFAULT 0,
  total DECIMAL(12, 2) NOT NULL DEFAULT 0,
  paid_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  balance DECIMAL(12, 2) NOT NULL DEFAULT 0,
  seal_kwh_reference_price DECIMAL(12, 4) NULL,
  collective_kwh_price DECIMAL(12, 4) NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'pending',
  notes TEXT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY receipts_customer_period_key (customer_id, period_year, period_month),
  UNIQUE KEY receipts_receipt_number_key (receipt_number),
  CONSTRAINT receipts_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT receipts_meter_reading_id_fkey FOREIGN KEY (meter_reading_id) REFERENCES meter_readings (id) ON DELETE SET NULL,
  CONSTRAINT receipts_provider_bill_id_fkey FOREIGN KEY (provider_bill_id) REFERENCES provider_bills (id) ON DELETE SET NULL,
  CONSTRAINT receipts_period_year_check CHECK (period_year BETWEEN 2000 AND 2100),
  CONSTRAINT receipts_period_month_check CHECK (period_month BETWEEN 1 AND 12),
  CONSTRAINT receipts_due_date_check CHECK (due_date IS NULL OR due_date >= issue_date),
  CONSTRAINT receipts_subtotal_check CHECK (subtotal >= 0),
  CONSTRAINT receipts_total_check CHECK (total >= 0),
  CONSTRAINT receipts_paid_amount_check CHECK (paid_amount >= 0),
  CONSTRAINT receipts_balance_check CHECK (balance >= 0),
  CONSTRAINT receipts_status_check CHECK (
    status IN ('draft', 'pending', 'partially_paid', 'paid', 'overdue', 'cancelled')
  ),
  CONSTRAINT receipts_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT receipts_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- receipt_items
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS receipt_items (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  receipt_id CHAR(36) NOT NULL,
  concept VARCHAR(512) NOT NULL,
  description TEXT NULL,
  quantity DECIMAL(12, 2) NOT NULL DEFAULT 1,
  unit_price DECIMAL(12, 4) NOT NULL DEFAULT 0,
  amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  item_type VARCHAR(64) NOT NULL DEFAULT 'other',
  sort_order INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  CONSTRAINT receipt_items_receipt_id_fkey FOREIGN KEY (receipt_id) REFERENCES receipts (id) ON DELETE CASCADE,
  CONSTRAINT receipt_items_item_type_check CHECK (
    item_type IN (
      'energy_consumption',
      'minimum_payment',
      'technical_payment',
      'reconnection',
      'printing',
      'rounding',
      'previous_debt',
      'other'
    )
  ),
  CONSTRAINT receipt_items_quantity_check CHECK (quantity >= 0),
  CONSTRAINT receipt_items_unit_price_check CHECK (unit_price >= 0),
  CONSTRAINT receipt_items_amount_check CHECK (amount >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- payments
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS payments (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  receipt_id CHAR(36) NOT NULL,
  customer_id CHAR(36) NOT NULL,
  payment_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  amount DECIMAL(12, 2) NOT NULL,
  payment_method VARCHAR(32) NOT NULL,
  reference_number VARCHAR(128) NULL,
  notes TEXT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'valid',
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  CONSTRAINT payments_receipt_id_fkey FOREIGN KEY (receipt_id) REFERENCES receipts (id),
  CONSTRAINT payments_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT payments_amount_check CHECK (amount > 0),
  CONSTRAINT payments_payment_method_check CHECK (
    payment_method IN ('cash', 'yape', 'plin', 'bank_transfer', 'other')
  ),
  CONSTRAINT payments_status_check CHECK (status IN ('valid', 'cancelled')),
  CONSTRAINT payments_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT payments_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- extra_charges
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS extra_charges (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  start_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  due_date DATE NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'draft',
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  CONSTRAINT extra_charges_amount_check CHECK (amount >= 0),
  CONSTRAINT extra_charges_due_date_check CHECK (due_date IS NULL OR due_date >= start_date),
  CONSTRAINT extra_charges_status_check CHECK (status IN ('draft', 'active', 'closed', 'cancelled')),
  CONSTRAINT extra_charges_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT extra_charges_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- extra_charge_assignments
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS extra_charge_assignments (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  extra_charge_id CHAR(36) NOT NULL,
  customer_id CHAR(36) NOT NULL,
  amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  paid_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  balance DECIMAL(12, 2) NOT NULL DEFAULT 0,
  status VARCHAR(32) NOT NULL DEFAULT 'pending',
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY extra_charge_assignments_charge_customer_key (extra_charge_id, customer_id),
  CONSTRAINT extra_charge_assignments_extra_charge_id_fkey FOREIGN KEY (extra_charge_id) REFERENCES extra_charges (id) ON DELETE CASCADE,
  CONSTRAINT extra_charge_assignments_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT extra_charge_assignments_amount_check CHECK (amount >= 0),
  CONSTRAINT extra_charge_assignments_paid_amount_check CHECK (paid_amount >= 0),
  CONSTRAINT extra_charge_assignments_balance_check CHECK (balance >= 0),
  CONSTRAINT extra_charge_assignments_status_check CHECK (
    status IN ('pending', 'partially_paid', 'paid', 'cancelled')
  ),
  CONSTRAINT extra_charge_assignments_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT extra_charge_assignments_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- extra_charge_payments
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS extra_charge_payments (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  extra_charge_assignment_id CHAR(36) NOT NULL,
  extra_charge_id CHAR(36) NOT NULL,
  customer_id CHAR(36) NOT NULL,
  payment_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  amount DECIMAL(12, 2) NOT NULL,
  payment_method VARCHAR(32) NOT NULL,
  reference_number VARCHAR(128) NULL,
  notes TEXT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'valid',
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  CONSTRAINT extra_charge_payments_assignment_id_fkey FOREIGN KEY (extra_charge_assignment_id) REFERENCES extra_charge_assignments (id),
  CONSTRAINT extra_charge_payments_extra_charge_id_fkey FOREIGN KEY (extra_charge_id) REFERENCES extra_charges (id),
  CONSTRAINT extra_charge_payments_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT extra_charge_payments_amount_check CHECK (amount > 0),
  CONSTRAINT extra_charge_payments_payment_method_check CHECK (
    payment_method IN ('cash', 'yape', 'plin', 'bank_transfer', 'other')
  ),
  CONSTRAINT extra_charge_payments_status_check CHECK (status IN ('valid', 'cancelled')),
  CONSTRAINT extra_charge_payments_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT extra_charge_payments_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- customer_service_events
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS customer_service_events (
  id CHAR(36) NOT NULL DEFAULT (UUID()),
  customer_id CHAR(36) NOT NULL,
  event_type VARCHAR(32) NOT NULL,
  event_date DATE NOT NULL DEFAULT (CURRENT_DATE),
  reason TEXT NULL,
  amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
  charge_status VARCHAR(32) NOT NULL DEFAULT 'none',
  receipt_id CHAR(36) NULL,
  notes TEXT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  created_by CHAR(36) NULL,
  updated_by CHAR(36) NULL,
  PRIMARY KEY (id),
  CONSTRAINT customer_service_events_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customers (id),
  CONSTRAINT customer_service_events_receipt_id_fkey FOREIGN KEY (receipt_id) REFERENCES receipts (id) ON DELETE SET NULL,
  CONSTRAINT customer_service_events_event_type_check CHECK (event_type IN ('cut', 'reconnection')),
  CONSTRAINT customer_service_events_charge_status_check CHECK (
    charge_status IN ('none', 'pending', 'billed', 'cancelled')
  ),
  CONSTRAINT customer_service_events_amount_check CHECK (amount >= 0),
  -- Reglas cut/reconnection: en MySQL no se pueden combinar CHECK sobre receipt_id con FK ON DELETE SET NULL;
  -- se validan en Java (CustomerServiceEventRules).
  CONSTRAINT customer_service_events_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL,
  CONSTRAINT customer_service_events_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Índices (equivalentes a Supabase)
-- ---------------------------------------------------------------------------
CREATE INDEX customers_document_number_idx ON customers (document_number);
CREATE INDEX customers_block_lot_idx ON customers (block, lot);

CREATE INDEX meter_readings_customer_id_idx ON meter_readings (customer_id);
CREATE INDEX meter_readings_period_idx ON meter_readings (period_year, period_month);

CREATE INDEX receipts_customer_id_idx ON receipts (customer_id);
CREATE INDEX receipts_status_idx ON receipts (status);
CREATE INDEX receipts_period_idx ON receipts (period_year, period_month);
CREATE INDEX receipts_provider_bill_id_idx ON receipts (provider_bill_id);

CREATE INDEX receipt_items_receipt_id_idx ON receipt_items (receipt_id);

CREATE INDEX payments_receipt_id_idx ON payments (receipt_id);
CREATE INDEX payments_customer_id_idx ON payments (customer_id);
CREATE INDEX payments_payment_date_idx ON payments (payment_date);
CREATE INDEX payments_status_idx ON payments (status);

CREATE INDEX extra_charges_status_idx ON extra_charges (status);
CREATE INDEX extra_charges_start_date_idx ON extra_charges (start_date);

CREATE INDEX extra_charge_assignments_extra_charge_id_idx ON extra_charge_assignments (extra_charge_id);
CREATE INDEX extra_charge_assignments_customer_id_idx ON extra_charge_assignments (customer_id);
CREATE INDEX extra_charge_assignments_status_idx ON extra_charge_assignments (status);

CREATE INDEX extra_charge_payments_assignment_id_idx ON extra_charge_payments (extra_charge_assignment_id);
CREATE INDEX extra_charge_payments_extra_charge_id_idx ON extra_charge_payments (extra_charge_id);
CREATE INDEX extra_charge_payments_customer_id_idx ON extra_charge_payments (customer_id);
CREATE INDEX extra_charge_payments_payment_date_idx ON extra_charge_payments (payment_date);
CREATE INDEX extra_charge_payments_status_idx ON extra_charge_payments (status);

CREATE INDEX provider_bills_period_idx ON provider_bills (period_year, period_month);
CREATE INDEX provider_bills_status_idx ON provider_bills (status);
CREATE INDEX provider_bills_due_date_idx ON provider_bills (due_date);
CREATE INDEX provider_bills_provider_receipt_number_idx ON provider_bills (provider_receipt_number);

CREATE INDEX customer_service_events_customer_id_idx ON customer_service_events (customer_id);
CREATE INDEX customer_service_events_event_type_idx ON customer_service_events (event_type);
CREATE INDEX customer_service_events_charge_status_idx ON customer_service_events (charge_status);
CREATE INDEX customer_service_events_receipt_id_idx ON customer_service_events (receipt_id);
CREATE INDEX customer_service_events_event_date_idx ON customer_service_events (event_date);
