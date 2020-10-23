DROP DATABASE IF EXISTS beerinventoryservice;
DROP USER IF EXISTS `beer_inventory_service`@`%`;
CREATE DATABASE IF NOT EXISTS beerinventoryservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `beer_inventory_service`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `beerinventoryservice`.* TO `beer_inventory_service`@`%`;
FLUSH PRIVILEGES;

USE beerinventoryservice;

CREATE TABLE `beer_inventory` (
                                `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `created_date` datetime(6) DEFAULT NULL,
                                `last_modified_date` datetime(6) DEFAULT NULL,
                                `version` bigint(20) DEFAULT NULL,
                                `beer_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `quantity_on_hand` int(11) DEFAULT NULL,
                                `upc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;