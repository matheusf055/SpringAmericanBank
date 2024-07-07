DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
                         ID VARCHAR(36) PRIMARY KEY,
                         CUSTOMER_ID BIGINT NOT NULL,
                         CATEGORY_ID BIGINT NOT NULL,
                         TOTAL DOUBLE NOT NULL,
                         CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (CUSTOMER_ID) REFERENCES customer(ID),
                         FOREIGN KEY (CATEGORY_ID) REFERENCES rule(ID)
);
