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

DROP TABLE IF EXISTS `TripleList`;
CREATE TABLE `TripleList`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `head` varchar(256) DEFAULT NULL,
   `relation` varchar(256) DEFAULT NULL,
   `tail` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `NumIdMapList`;
CREATE TABLE `NumIdMapList`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `num` varchar(256) DEFAULT NULL,
   `id` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;