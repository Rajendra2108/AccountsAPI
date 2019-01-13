DROP TABLE IF EXISTS ACCOUNT;
CREATE TABLE ACCOUNT(id INT auto_increment PRIMARY KEY, FIRST_NAME VARCHAR(255), SECOND_NAME VARCHAR(255), ACCOUNT_NUMBER VARCHAR(255));
INSERT INTO Account (FIRST_NAME, SECOND_NAME, ACCOUNT_NUMBER) values('Jane', 'Bike', '1111');
INSERT INTO Account (FIRST_NAME, SECOND_NAME, ACCOUNT_NUMBER) values('Ryan', 'Jones', '1112');
INSERT INTO Account (FIRST_NAME, SECOND_NAME, ACCOUNT_NUMBER) values('Lee', 'Taylor', '1113');
INSERT INTO Account (FIRST_NAME, SECOND_NAME, ACCOUNT_NUMBER) values('Ben', 'Spikes', '1114');