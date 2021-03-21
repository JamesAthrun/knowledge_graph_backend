DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

DROP TABLE IF EXISTS `jackList`;
CREATE TABLE `jackList`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `dsp` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `JackList` VALUES(1,'hello my name is jack');

DROP TABLE IF EXISTS `triple`;
CREATE TABLE `triple`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `tableId` varchar(256) DEFAULT NULL,
   `head` varchar(256) DEFAULT NULL,
   `relation` varchar(256) DEFAULT NULL,
   `tail` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `id` varchar(256) DEFAULT NULL,
   `nameEn` varchar(256) DEFAULT NULL,
   `nameCn` varchar(256) DEFAULT NULL,
   `division` varchar(256) DEFAULT NULL,
   `from` varchar(256) DEFAULT NULL,
   `comment` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `property`;
CREATE TABLE `property`(
    `recordId` int(11) NOT NULL AUTO_INCREMENT,
    `id` varchar(256) DEFAULT NULL,
    `nameEn` varchar(256) DEFAULT NULL,
    `nameCn` varchar(256) DEFAULT NULL,
    `domain` varchar(256) DEFAULT NULL,
    `range` varchar(256) DEFAULT NULL,
    `from` varchar(256) DEFAULT NULL,
    `comment` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;