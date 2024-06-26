CREATE TABLE IF NOT EXISTS customer (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          CPF VARCHAR(14) NOT NULL UNIQUE,
                          NAME VARCHAR(100) NOT NULL,
                          GENDER ENUM('Masculino', 'Feminino') NOT NULL,
                          BIRTHDATE DATE NOT NULL,
                          EMAIL VARCHAR(100) NOT NULL UNIQUE,
                          POINTS INT DEFAULT 0,
                          URL_PHOTO VARCHAR(600)
);