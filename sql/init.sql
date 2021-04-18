DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

CREATE TABLE `triple`(
    `tableId` varchar(64) DEFAULT NULL,
    `head` varchar(64) DEFAULT '',
    `relation` varchar(64) DEFAULT '',
    `tail` varchar(64) DEFAULT '',
    PRIMARY KEY (`head`,`relation`,`tail`)
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
   `tableId` int(11) NOT NULL,
   `name` varchar(256) DEFAULT NULL,
   `description` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`tableId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `question`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `keyWords` varchar(256) DEFAULT NULL,
    `help` varchar(256) DEFAULT NULL,
    `relatedIds` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account (name, pwd, email, authority) VALUES ('trump','123456','magg@trump.com','president');
INSERT INTO account (name, pwd, email, authority) VALUES ('obama','123456','blm@obama.com','president');

INSERT INTO question (keyWords, help, relatedIds) VALUES ('[{"0":"农民工"},{"1":"预防"}]','请仔细阅读以上内容，在完成的项后打勾，如果您已经全部完成，说明您已经百毒不侵，可以下地干活了！','[{"0":"19321220"},{"1":"19747406"},{"2":"19261796"},{"3":"19509710"},{"4":"19771248"},{"5":"19357164"},{"6":"19891182"},{"7":"19836900"}]');