INSERT INTO workspaces (type, price, available) VALUES 
('Private Office', 100.0, true),
('Hot Desk', 50.0, true),
('Meeting Room', 75.0, true);

INSERT INTO reservations (customer_name, date, start_time, end_time, workspace_id) VALUES
('John Doe', '2025-04-01', '09:00:00', '17:00:00', 1),
('Jane Smith', '2025-04-02', '10:00:00', '14:00:00', 2);

INSERT INTO bookings (user_id, workspace_id, start_date, end_date, total_price, payment_method, status)
VALUES (1, 'workspace-1', '2023-06-01 09:00:00', '2023-06-01 17:00:00', 80.00, 'creditcard', 'CONFIRMED');