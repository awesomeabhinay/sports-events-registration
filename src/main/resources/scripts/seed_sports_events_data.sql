-- Create table for User entity
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL
);

-- Insert dummy data for User entity
INSERT INTO users (username, email) VALUES
                                       ('user1', 'user1@example.com'),
                                       ('user2', 'user2@example.com'),
                                       ('user3', 'user3@example.com');

-- Create table for Event entity
DROP TABLE IF EXISTS events;
CREATE TABLE events (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       event_name VARCHAR(255) NOT NULL,
                       event_category VARCHAR(255),
                       start_time DATETIME,
                       end_time DATETIME
);

-- Insert dummy data for Event entity
INSERT INTO events (event_name, event_category, start_time, end_time) VALUES
                                                                     ('Event 1', 'Category A', '2023-10-01 10:00:00', '2023-10-01 12:00:00'),
                                                                     ('Event 2', 'Category B', '2023-10-02 14:00:00', '2023-10-02 16:00:00'),
                                                                     ('Event 3', 'Category A', '2023-10-03 09:00:00', '2023-10-03 11:00:00');

-- Create table for EventRegistration entity
DROP TABLE IF EXISTS event_registration;
CREATE TABLE event_registration (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   user_id INT NOT NULL,
                                   event_id INT NOT NULL,
                                   FOREIGN KEY (user_id) REFERENCES users (id),
                                   FOREIGN KEY (event_id) REFERENCES events (id)
);

-- Insert dummy data for EventRegistration entity
INSERT INTO event_registration (user_id, event_id) VALUES
                                                      (1, 1),
                                                      (2, 1),
                                                      (1, 2),
                                                      (3, 3);
