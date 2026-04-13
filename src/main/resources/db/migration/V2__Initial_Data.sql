-- Insert Sample Department
INSERT INTO departments (name, description, code, created_by, created_date)
VALUES ('Information Technology', 'IT Department', 'IT_DEPT', 'admin', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING; -- Assuming id 1 or handle by unique code if you have one

-- Insert Sample User System
INSERT INTO user_systems (name, description, status, created_by, created_date)
VALUES ('TarmsServiceBus', 'Tax Revenue Management System Bus', 'ACTIVE', 'admin', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Insert Roles
INSERT INTO roles (name, system_id, created_by, created_date)
SELECT 'TaRMS_User', id, 'admin', CURRENT_TIMESTAMP FROM user_systems WHERE name = 'TarmsServiceBus'
WHERE NOT EXISTS (SELECT 1 FROM roles r JOIN user_systems s ON r.system_id = s.id WHERE r.name = 'TaRMS_User' AND s.name = 'TarmsServiceBus');

INSERT INTO roles (name, system_id, created_by, created_date)
SELECT 'TaRMS_Dev', id, 'admin', CURRENT_TIMESTAMP FROM user_systems WHERE name = 'TarmsServiceBus'
WHERE NOT EXISTS (SELECT 1 FROM roles r JOIN user_systems s ON r.system_id = s.id WHERE r.name = 'TaRMS_Dev' AND s.name = 'TarmsServiceBus');

-- Insert Test User (password: password)
INSERT INTO users (first_name, last_name, username, email, phone, password, active, password_expired, department_id, created_by, created_date)
VALUES (
    'Test', 'User', 'tjokonya', 'tjokonya@zimra.co.zw', '0770000000',
    '$2a$10$8.UnVuG9HHgffUDAlk8qn.9clQE5dq77JneYcHfL901F.eGsaIsRm',
    TRUE, FALSE, (SELECT id FROM departments WHERE code = 'IT_DEPT'), 'admin', CURRENT_TIMESTAMP
)
ON CONFLICT (username) DO NOTHING;

-- Assign Roles
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'tjokonya' AND r.name IN ('TaRMS_User', 'TaRMS_Dev')
ON CONFLICT DO NOTHING;

-- Insert Whitelist
INSERT INTO allowed_origins (origin_url, created_by, created_date)
VALUES ('http://localhost:8081', 'admin', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO allowed_origins (origin_url, created_by, created_date)
VALUES ('http://localhost:8082', 'admin', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
