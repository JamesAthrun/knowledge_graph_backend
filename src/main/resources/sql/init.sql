drop database if exists nkg;
create database nkg default charset utf8;
USE nkg;

create table `triple`
(
    `head`     varchar(11) not null,
    `relation` varchar(11) not null,
    `tail`     varchar(11) not null,
    `ver`      varchar(11) not null,
    `drop`     varchar(11) default null,
    `tableId`  varchar(11) default null,
    primary key (`head`, `relation`, `tail`, `ver`)
) engine = MyISAM
  default charset = utf8;
# create index headIndex on triple (head);
# create index tailIndex on triple (tail); #700ms

create index headIndex on triple (head, tail);
create index tailIndex on triple (tail, head);
#610ms

# create index headIndex on triple (head,relation,tail,tableId);
# create index tailIndex on triple (tail,relation,head,tableId); #600ms
# 据测试，在进行图搜索时，这两个索引能带来200%~800%的速度提升

create table `item`
(
    `id`       varchar(11) not null,
    `ver`      varchar(11) not null,
    `drop`     varchar(11)  default null,
    `tableId`  varchar(11)  default null,
    `title`    varchar(256) default null,
    `name`     varchar(256) default null,
    `division` varchar(11)  default null,
    `comment`  varchar(256) default null,
    primary key (`id`, `ver`)
) engine = MyISAM
  default charset = utf8;

create table `verify`
(
    `ip`     varchar(256) not null,
    `desKey` varchar(256) default null,
    `userName` varchar(256) default null,
    primary key (`ip`)
) engine = MyISAM
  default charset = utf8;

create table `account`
(
    `userId`    int primary key AUTO_INCREMENT,
    `name`      varchar(256) not null,
    `pwd`       varchar(256) default null,
    `email`     varchar(256) default null
) engine = MyISAM
  default charset = utf8;

create table `group`
(
    `groupId`    int primary key AUTO_INCREMENT,
    `name`       varchar(256) not null,
    `description`varchar(1024) default null
) engine = MyISAM
  default charset = utf8;


create table `userGroup`
(
    `userId`  int,
    `groupId` int,
    primary key (`userId`, `groupId`)
)
default charset = utf8;

create table `graph`
(
    `tableId`     varchar(11) not null,
    `name`        varchar(256) default null,
    `description` varchar(256) default null,
    `ver`         varchar(11)  default null,
    `userId`      int,
    `groupId`     int,
    `authority`   int,
    primary key (`tableId`)
) engine = MyISAM
  default charset = utf8;

create table `question`
(
    `keyWords`   varchar(256) not null,
    `help`       varchar(256) default null,
    `relatedIds` varchar(256) default null,
    `ver`        varchar(11)  default null,
    primary key (`keyWords`)
) engine = MyISAM
  default charset = utf8;

create table `history`
(
    `id`      int(11) auto_increment,
    `tableId` varchar(11)   not null,
    `ver`     varchar(11)   not null,
    `time`    varchar(256)  not null,
    `detail`  varchar(1024) not null,
    primary key (`id`)
) engine = MyISAM
  default charset = utf8;

insert into account (name, pwd, email)
values ('trump', '123456', 'magg@trump.com');
insert into account (name, pwd, email)
values ('obama', '123456', 'blm@obama.com');
insert into `group` (name, description)
values ('common', 'common user');
insert into `group` (name, description)
values ('root', 'Administrator');
insert into userGroup(userId, groupId)
values (1, 1);
insert into userGroup(userId, groupId)
values (2, 1);
insert into userGroup(userId, groupId)
values (1, 2);
insert into question (keyWords, help, relatedIds, ver)
values ('[{"0":"农民工"},{"1":"预防"}]', '请仔细阅读以上内容，在完成的项后打勾，如果您已经全部完成，说明您已经百毒不侵，可以下地干活了！',
        '[{"0":"19321220"},{"1":"19747406"},{"2":"19261796"},{"3":"19509710"},{"4":"19771248"},{"5":"19357164"},{"6":"19891182"},{"7":"19836900"}]',
        '0');

insert into verify(ip, desKey, userName)
values ('0:0:0:0:0:0:0:1', '9d791cf70b167002','obama');