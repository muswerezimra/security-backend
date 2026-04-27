-- Consolidated V1 Migration Script
-- 1. Create Department Table
CREATE TABLE IF NOT EXISTS department (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    code VARCHAR(50),
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- 2. Create User System Table
CREATE TABLE IF NOT EXISTS user_system (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    status VARCHAR(50),
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- 3. Create Roles Table
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    system_id BIGINT NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255),
    CONSTRAINT fk_role_system FOREIGN KEY (system_id) REFERENCES user_system(id)
);

-- 4. Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    department_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_date TIMESTAMP WITHOUT TIME ZONE,
    password_expired BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255),
    CONSTRAINT fk_user_department FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 5. Create User-Roles Join Table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 6. Create Allowed Origins Table
CREATE TABLE IF NOT EXISTS allowed_origins (
    id BIGSERIAL PRIMARY KEY,
    origin_url VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- Indexing
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_users_username') THEN
        CREATE INDEX idx_users_username ON users(username);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_roles_system_id') THEN
        CREATE INDEX idx_roles_system_id ON roles(system_id);
    END IF;
END $$;


--resolved
