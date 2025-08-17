CREATE TABLE public.source_orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100),
    amount DECIMAL(10,2),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Таблица назначения (обработанные заказы)
CREATE TABLE public.target_orders_enriched (
    id BIGINT PRIMARY KEY,
    customer_name VARCHAR(100),
    amount_usd DECIMAL(10,2),
    status VARCHAR(20),
    created_at TIMESTAMP,
    processed_at TIMESTAMP DEFAULT NOW(),
    currency_rate DECIMAL(5,2)
);

INSERT INTO public.source_orders (customer_name, amount, status, created_at) VALUES
('Анна', 1000.00, 'completed', '2025-07-01 10:00:00'),
('Борис', 1500.00, 'pending',   '2025-07-02 11:30:00'),
('Игнат', 500.00, 'pending',   '2025-07-01 11:30:00'),
('Светлана', 800.00, 'completed', '2025-07-03 14:45:00');
