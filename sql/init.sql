DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

DROP TABLE IF EXISTS `JackList`;
CREATE TABLE `JackList`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `dsp` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `JackList` VALUES(1,'hello my name is jack');

DROP TABLE IF EXISTS `Triple`;
CREATE TABLE `Triple`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `head` varchar(256) DEFAULT NULL,
   `relation` varchar(256) DEFAULT NULL,
   `tail` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Entity`;
CREATE TABLE `Entity`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `id` varchar(256) DEFAULT NULL,
   `nameEn` varchar(256) DEFAULT NULL,
   `nameCn` varchar(256) DEFAULT NULL,
   `division` varchar(256) DEFAULT NULL,
   `from` varchar(256) DEFAULT NULL,
   `comment` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Property`;
CREATE TABLE `Property`(
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