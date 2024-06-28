DROP TABLE IF EXISTS rule;

CREATE TABLE rule (
                      ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                      CATEGORY VARCHAR(200) NOT NULL UNIQUE,
                      PARITY INT NOT NULL
);
