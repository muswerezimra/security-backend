-- 1. Create Departments Table
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    code VARCHAR(50),
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- 2. Create UserSystems Table (The external systems)
CREATE TABLE user_systems (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    status VARCHAR(50), -- e.g., ACTIVE, INACTIVE
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- 3. Create Roles Table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    system_id BIGINT NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255),
    CONSTRAINT fk_role_system FOREIGN KEY (system_id) REFERENCES user_systems(id)
);

-- 4. Create Users Table
CREATE TABLE users (
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
    CONSTRAINT fk_user_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- 5. Create User-Roles Join Table (The core relationship)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 6. Create Allowed Origins Table (CORS)
CREATE TABLE allowed_origins (
    id BIGSERIAL PRIMARY KEY,
    origin_url VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP WITHOUT TIME ZONE,
    modified_by VARCHAR(255)
);

-- Indexing for performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_roles_system_id ON roles(system_id);
