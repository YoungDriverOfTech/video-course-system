-- Course
drop table if exists course;
create table course (
  id char(8) not null default '' comment 'id',
  name varchar(50) not null comment 'name',
  summary varchar(2000) comment 'summary',
  time int default 0 comment 'video length| unit second',
  price decimal(8,2) default 0.00 comment 'price',
  image varchar(100) comment 'profile',
  level char(1) comment 'level |enums[CourseLevelEnum]：ONE("1", "primary"),TWO("2", "middle"),THREE("3", "senior")',
  charge char(1) comment 'charge |enums[CourseChargeEnum]：CHARGE("C", "charge"),FREE("F", "free")',
  status char(1) comment 'status |enums[CourseStatusEnum]：PUBLISH("P", "published"),DRAFT("D", "draft")',
  enroll integer default 0 comment 'enroll number',
  sort int comment 'sort order',
  created_at datetime(3) comment 'created_at',
  updated_at datetime(3) comment 'updated_at',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='course';

INSERT INTO course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at)
VALUES ('00000001', '测试课程01', '这是一门测试课程', 7200, 19.9, '', 1, 'C', 'D', 100, 0, now(), now());


-- Chapter
drop table if exists `chapter`;
create table `chapter` (
  `id` char(8) not null comment 'Id',
  `course_id` char(8) comment 'Course Id',
  `name` varchar(50) comment 'Name',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='Chapter';

insert into `chapter` (id, course_id, name) values ('00000001', '00000000', '测试大章01');
insert into `chapter` (id, course_id, name) values ('00000002', '00000000', '测试大章02');
insert into `chapter` (id, course_id, name) values ('00000003', '00000000', '测试大章03');
insert into `chapter` (id, course_id, name) values ('00000004', '00000000', '测试大章04');
insert into `chapter` (id, course_id, name) values ('00000005', '00000000', '测试大章05');
insert into `chapter` (id, course_id, name) values ('00000006', '00000000', '测试大章06');
insert into `chapter` (id, course_id, name) values ('00000007', '00000000', '测试大章07');
insert into `chapter` (id, course_id, name) values ('00000008', '00000000', '测试大章08');
insert into `chapter` (id, course_id, name) values ('00000009', '00000000', '测试大章09');
insert into `chapter` (id, course_id, name) values ('00000010', '00000000', '测试大章10');
insert into `chapter` (id, course_id, name) values ('00000011', '00000000', '测试大章11');
insert into `chapter` (id, course_id, name) values ('00000012', '00000000', '测试大章12');
insert into `chapter` (id, course_id, name) values ('00000013', '00000000', '测试大章13');
insert into `chapter` (id, course_id, name) values ('00000014', '00000000', '测试大章14');


-- Section
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `id` CHAR(8) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` VARCHAR(50) NOT NULL COMMENT 'title',
  `course_id` CHAR(8) COMMENT 'course.id',
  `chapter_id` CHAR(8) COMMENT 'chapter.id',
  `video` VARCHAR(200) COMMENT 'video',
  `time` INT COMMENT 'video length',
  `charge` CHAR(1) COMMENT 'is free or not',
  `sort` INT COMMENT 'sort',
  `created_at` DATETIME(3) COMMENT 'created_at',
  `updated_at` DATETIME(3) COMMENT 'updated_at',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Section';

INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('00000001', '测试小节01', '00000001', '00000000', '', 500, 'F', 1, now(), now());



-----for test======
drop table if exists `test`;
create table `test` (
  `id` char(8) not null default '' comment 'id',
  `name` varchar(50) comment '名称',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `test` (id, name) values (1, '测试');
insert into `test` (id, name) values (2, '测试2');