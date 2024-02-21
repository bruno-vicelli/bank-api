CREATE DATABASE bank_api;
CREATE USER 'bank_api'@'%' IDENTIFIED BY 'bank_api';
CREATE USER 'bank_api'@'localhost' IDENTIFIED BY 'bank_api';
GRANT ALL PRIVILEGES ON bank_api.* to bank_api@localhost;
GRANT ALL PRIVILEGES ON bank_api.* to bank_api@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS T_CHECKING_ACCOUNT(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `accountNumber` varchar(10) DEFAULT NULL,
    `balance` decimal(60),
    `active` tinyint(1),
    `dailyLimit` decimal(60),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT into t_checking_account(id, account_number, balance, active, daily_limit) values (1, "123456", 1000, 1, 1000);
INSERT into t_checking_account(id, account_number, balance, active, daily_limit) values (1, "456789", 250, 1, 1000);
INSERT into t_checking_account(id, account_number, balance, active, daily_limit) values (1, "759842", 300, 1, 1000);
INSERT into t_checking_account(id, account_number, balance, active, daily_limit) values (1, "698524", 2500, 1, 1000);