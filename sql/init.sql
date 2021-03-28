DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

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

DROP TABLE IF EXISTS `verify`;
CREATE TABLE `verify`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `ip` varchar(256) NOT NULL,
    `desKey` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`(
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(256) UNIQUE DEFAULT NULL,
     `pwd` varchar(256) DEFAULT NULL,
     PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;