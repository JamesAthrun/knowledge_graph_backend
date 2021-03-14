USE nkg;
DROP TABLE IF EXISTS `TripleList_zh`;
CREATE TABLE `TripleList_zh`(
    `recordId` int(11) NOT NULL AUTO_INCREMENT,
    `head` varchar(256) DEFAULT NULL,
    `relation` varchar(256) DEFAULT NULL,
    `tail` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`recordId`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8;