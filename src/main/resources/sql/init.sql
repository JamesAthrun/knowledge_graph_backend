drop database if exists nkg;
create database nkg default charset utf8;
USE nkg;

create table `triple`(
    `tableId` varchar(11) default null,
    `head` varchar(11) not null,
    `relation` varchar(11) not null,
    `tail` varchar(11) not null,
    primary key (`head`,`relation`,`tail`)
)engine=MyISAM default charset=utf8;
create index headIndex on triple (head);
create index tailIndex on triple (tail);
# 据测试，在进行图搜索时，这两个索引能带来200%~800%的速度提升

create table `item`(
    `id` varchar(11) not null,
    `tableId` varchar(11) default null,
    `title` varchar(256) default null,
    `name` varchar(256) default null,
    `division` varchar(11) default null,
    `comment` varchar(256) default null,
    primary key (`id`)
)engine=MyISAM default charset=utf8;

create table `verify`(
    `ip` varchar(256) not null,
    `desKey` varchar(256) default null,
    primary key (`ip`)
)engine=MyISAM default charset=utf8;

create table `account`(
     `name` varchar(256) not null,
     `pwd` varchar(256) default null,
     `email` varchar(256) default null,
     `authority` varchar(256) default null,
     primary key (`name`)
)engine=MyISAM default charset=utf8;

create table `graph`(
    `tableId` varchar(11) not null,
    `name` varchar(256) default null,
    `description` varchar(256) default null,
    primary key (`tableId`)
)engine=MyISAM default charset=utf8;

create table `question`(
    `keyWords` varchar(256) not null,
    `help` varchar(256) default null,
    `relatedIds` varchar(256) default null,
    primary key (`keyWords`)
)engine=MyISAM default charset=utf8;

# create table `example`(
#     `id` int(11) AUTO_INCREMENT,
#     `column1` varchar(256) default null,
#     `column2` varchar(256) default null,
#     primary key (`id`)
# )engine=MyISAM default charset=utf8;


insert into account (name, pwd, email, authority) values ('trump','123456','magg@trump.com','president');
insert into account (name, pwd, email, authority) values ('obama','123456','blm@obama.com','president');
insert into question (keyWords, help, relatedIds) values ('[{"0":"农民工"},{"1":"预防"}]','请仔细阅读以上内容，在完成的项后打勾，如果您已经全部完成，说明您已经百毒不侵，可以下地干活了！','[{"0":"19321220"},{"1":"19747406"},{"2":"19261796"},{"3":"19509710"},{"4":"19771248"},{"5":"19357164"},{"6":"19891182"},{"7":"19836900"}]');