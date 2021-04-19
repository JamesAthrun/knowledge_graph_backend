DROP DATABASE IF EXISTS nkg;
CREATE DATABASE nkg DEFAULT CHARACTER SET utf8;
USE nkg;

CREATE TABLE `triple`(
    `tableId` varchar(11) DEFAULT NULL,
    `head` varchar(11) NOT NULL,
    `relation` varchar(11) NOT NULL,
    `tail` varchar(11) NOT NULL,
    PRIMARY KEY (`head`,`relation`,`tail`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `item`(
    `id` varchar(11) NOT NULL,
    `tableId` varchar(11) DEFAULT NULL,
    `title` varchar(256) DEFAULT NULL,
    `name` varchar(256) DEFAULT NULL,
    `division` varchar(11) DEFAULT NULL,
    `comment` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `verify`(
    `ip` varchar(256) NOT NULL,
    `desKey` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`ip`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `account`(
     `name` varchar(256) NOT NULL,
     `pwd` varchar(256) DEFAULT NULL,
     `email` varchar(256) DEFAULT NULL,
     `authority` varchar(256) DEFAULT NULL,
     PRIMARY KEY (`name`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `graph`(
    `tableId` varchar(11) NOT NULL,
    `name` varchar(256) DEFAULT NULL,
    `description` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`tableId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `question`(
    `keyWords` varchar(256) NOT NULL,
    `help` varchar(256) DEFAULT NULL,
    `relatedIds` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`keyWords`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account (name, pwd, email, authority) VALUES ('trump','123456','magg@trump.com','president');
INSERT INTO account (name, pwd, email, authority) VALUES ('obama','123456','blm@obama.com','president');
INSERT INTO question (keyWords, help, relatedIds) VALUES ('[{"0":"农民工"},{"1":"预防"}]','请仔细阅读以上内容，在完成的项后打勾，如果您已经全部完成，说明您已经百毒不侵，可以下地干活了！','[{"0":"19321220"},{"1":"19747406"},{"2":"19261796"},{"3":"19509710"},{"4":"19771248"},{"5":"19357164"},{"6":"19891182"},{"7":"19836900"}]');