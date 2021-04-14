DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

CREATE TABLE `triple`(
   `recordId` int(11) NOT NULL AUTO_INCREMENT,
   `tableId` varchar(256) DEFAULT NULL,
   `head` varchar(256) DEFAULT NULL,
   `relation` varchar(256) DEFAULT NULL,
   `tail` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

CREATE TABLE `verify`(
    `ip` varchar(256) NOT NULL,
    `desKey` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`ip`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `account`(
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(256) UNIQUE DEFAULT NULL,
     `pwd` varchar(256) DEFAULT NULL,
     `email` varchar(256) DEFAULT NULL,
     `authority` varchar(256) DEFAULT NULL,
     PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `graph`(
   `tableId` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(256) UNIQUE DEFAULT NULL,
   `description` varchar(256) UNIQUE DEFAULT NULL,
   PRIMARY KEY (`tableId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account (name, pwd, email, authority) VALUES ('trump','123456','magg@trump.com','president');
INSERT INTO account (name, pwd, email, authority) VALUES ('obama','123456','blm@obama.com','president');