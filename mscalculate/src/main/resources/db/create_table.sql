CREATE TABLE IF NOT EXISTS calculate (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          CATEGORY VARCHAR(200) NOT NULL UNIQUE,
                          PARITY INT NOT NULL
);