drop table if exists `chapter`;
create table `chapter` (
  `id` char(8) not null comment 'Id',
  `course_id` char(8) comment 'Course Id',
  `name` varchar(50) comment 'Name',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='Chapter';


-----for test======
drop table if exists `test`;
create table `test` (
  `id` char(8) not null default '' comment 'id',
  `name` varchar(50) comment '名称',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `test` (id, name) values (1, '测试');
insert into `test` (id, name) values (2, '测试2');