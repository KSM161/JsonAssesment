DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews(
author VARCHAR,
productName VARCHAR(50),
rating INT,
review CLOB NOT NULL,
reviewSource VARCHAR(25),
reviewedDate DATE,
title VARCHAR(100),
id VARCHAR not null,
PRIMARY KEY (id));