CREATE SCHEMA IF NOT EXISTS `ab180` DEFAULT CHARACTER SET utf8 ;
USE `ab180` ;

CREATE TABLE `ab180`.`ShortenUrl` (
  `id` INT NOT NULL ,
  `shortId` VARCHAR(200) NULL,
  `url` VARCHAR(200) NULL,
  `createAt` DATETIME NULL,
  PRIMARY KEY (`id`))

PARTITION BY HASH(id)
PARTITIONS 4(
    PARTITION id_1,
    PARTITION id_2,
    PARTITION id_3,
    PARTITION id_4
);

--ALTER TABLE `ab180`.`shorten_url` ADD PARTITION BY HASH(id)
--PARTITIONS 4(
--    PARTITION id_1,
--    PARTITION id_2,
--    PARTITION id_3,
--    PARTITION id_4
--);