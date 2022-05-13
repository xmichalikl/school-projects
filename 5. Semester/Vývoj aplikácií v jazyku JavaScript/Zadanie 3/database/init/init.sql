CREATE DATABASE IF NOT EXISTS eshop;
USE eshop;

CREATE TABLE `products` (
  `id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `price` double unsigned NOT NULL,
  `img` text NOT NULL
);
INSERT INTO `products` (`name`, `price`, `img`)
VALUES ('Hrable', '14.89', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/SoilRake.jpg/320px-SoilRake.jpg');

INSERT INTO `products` (`name`, `price`, `img`)
VALUES ('Krompac', '18.95', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Pickaxe.jpg/307px-Pickaxe.jpg');

INSERT INTO `products` (`name`, `price`, `img`)
VALUES ('Vedro', '12.99', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Bucket.agr.jpg/252px-Bucket.agr.jpg');


CREATE TABLE `customers` (
  `id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `surname` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `email` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `number` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `address` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `city` varchar(32) COLLATE 'utf8_general_ci' NOT NULL,
  `postcode` varchar(32) COLLATE 'utf8_general_ci' NOT NULL
);

CREATE TABLE `advertisement` (
  `id` int unsigned NOT NULL DEFAULT '1',
  `link` text NOT NULL,
  `img` text NOT NULL,
  `counter` int unsigned NOT NULL DEFAULT '0'
);
INSERT INTO `advertisement` (`id`, `link`, `img`, `counter`)
VALUES ('1', 'https://www.fiit.stuba.sk/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Exchanging_gifts_at_Christmas.jpg/640px-Exchanging_gifts_at_Christmas.jpg', '0');

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `products` text COLLATE 'utf32_general_ci' NOT NULL,
  `price` double unsigned NOT NULL,
  `customer_id` int NOT NULL,
  `state` varchar(32) COLLATE 'utf8_general_ci' NOT NULL
);