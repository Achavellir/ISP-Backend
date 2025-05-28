-- This file is used to initialize the database with some test data
-- The table creation is handled by Hibernate (spring.jpa.hibernate.ddl-auto=update)

-- Insert test users with encoded passwords
INSERT INTO users (username, email, password, enabled) 
VALUES ('testuser', 'test@example.com', '$2a$10$eDhncK/4cNH2KE.Y51AWpeL8K4mtRWxudhATzFgtgR/y1HWKl.h/i', true);

-- Insert rishi123 user with encoded password
INSERT INTO users (username, email, password, enabled) 
VALUES ('rishi123', 'rishi@example.com', '$2a$10$eDhncK/4cNH2KE.Y51AWpeL8K4mtRWxudhATzFgtgR/y1HWKl.h/i', true);

-- Insert user roles
INSERT INTO user_roles (user_id, role) 
VALUES (1, 'USER');

-- Insert roles for rishi123 (using a SELECT to get the user_id)
INSERT INTO user_roles (user_id, role) 
SELECT id, 'USER' FROM users WHERE username = 'rishi123';
