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

alter table `course` add column (`teacher_id` char(8) comment 'Teacher|teacher.id');


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


-- 分类
drop table if exists `category`;
create table `category` (
  `id` char(8) not null default '' comment 'id',
  `parent` char(8) not null default '' comment 'parent id',
  `name` varchar(50) not null comment 'name',
  `sort` int comment 'sort',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='category';

insert into `category` (id, parent, name, sort) values ('00000100', '00000000', '前端技术', 100);
insert into `category` (id, parent, name, sort) values ('00000101', '00000100', 'html/css', 101);
insert into `category` (id, parent, name, sort) values ('00000102', '00000100', 'javascript', 102);
insert into `category` (id, parent, name, sort) values ('00000103', '00000100', 'vue.js', 103);
insert into `category` (id, parent, name, sort) values ('00000104', '00000100', 'react.js', 104);
insert into `category` (id, parent, name, sort) values ('00000105', '00000100', 'angular', 105);
insert into `category` (id, parent, name, sort) values ('00000106', '00000100', 'node.js', 106);
insert into `category` (id, parent, name, sort) values ('00000107', '00000100', 'jquery', 107);
insert into `category` (id, parent, name, sort) values ('00000108', '00000100', '小程序', 108);
insert into `category` (id, parent, name, sort) values ('00000200', '00000000', '后端技术', 200);
insert into `category` (id, parent, name, sort) values ('00000201', '00000200', 'java', 201);
insert into `category` (id, parent, name, sort) values ('00000202', '00000200', 'springboot', 202);
insert into `category` (id, parent, name, sort) values ('00000203', '00000200', 'spring cloud', 203);
insert into `category` (id, parent, name, sort) values ('00000204', '00000200', 'ssm', 204);
insert into `category` (id, parent, name, sort) values ('00000205', '00000200', 'python', 205);
insert into `category` (id, parent, name, sort) values ('00000206', '00000200', '爬虫', 206);
insert into `category` (id, parent, name, sort) values ('00000300', '00000000', '移动开发', 300);
insert into `category` (id, parent, name, sort) values ('00000301', '00000300', 'android', 301);
insert into `category` (id, parent, name, sort) values ('00000302', '00000300', 'ios', 302);
insert into `category` (id, parent, name, sort) values ('00000303', '00000300', 'react native', 303);
insert into `category` (id, parent, name, sort) values ('00000304', '00000300', 'ionic', 304);
insert into `category` (id, parent, name, sort) values ('00000400', '00000000', '前沿技术', 400);
insert into `category` (id, parent, name, sort) values ('00000401', '00000400', '微服务', 401);
insert into `category` (id, parent, name, sort) values ('00000402', '00000400', '区块链', 402);
insert into `category` (id, parent, name, sort) values ('00000403', '00000400', '机器学习', 403);
insert into `category` (id, parent, name, sort) values ('00000404', '00000400', '深度学习', 404);
insert into `category` (id, parent, name, sort) values ('00000405', '00000400', '数据分析&挖掘', 405);
insert into `category` (id, parent, name, sort) values ('00000500', '00000000', '云计算&大数据', 500);
insert into `category` (id, parent, name, sort) values ('00000501', '00000500', '大数据', 501);
insert into `category` (id, parent, name, sort) values ('00000502', '00000500', 'hadoop', 502);
insert into `category` (id, parent, name, sort) values ('00000503', '00000500', 'spark', 503);
insert into `category` (id, parent, name, sort) values ('00000504', '00000500', 'hbase', 504);
insert into `category` (id, parent, name, sort) values ('00000505', '00000500', '阿里云', 505);
insert into `category` (id, parent, name, sort) values ('00000506', '00000500', 'docker', 506);
insert into `category` (id, parent, name, sort) values ('00000507', '00000500', 'kubernetes', 507);
insert into `category` (id, parent, name, sort) values ('00000600', '00000000', '运维&测试', 600);
insert into `category` (id, parent, name, sort) values ('00000601', '00000600', '运维', 601);
insert into `category` (id, parent, name, sort) values ('00000602', '00000600', '自动化运维', 602);
insert into `category` (id, parent, name, sort) values ('00000603', '00000600', '中间件', 603);
insert into `category` (id, parent, name, sort) values ('00000604', '00000600', 'linux', 604);
insert into `category` (id, parent, name, sort) values ('00000605', '00000600', '测试', 605);
insert into `category` (id, parent, name, sort) values ('00000606', '00000600', '自动化测试', 606);
insert into `category` (id, parent, name, sort) values ('00000700', '00000000', '数据库', 700);
insert into `category` (id, parent, name, sort) values ('00000701', '00000700', 'mysql', 701);
insert into `category` (id, parent, name, sort) values ('00000702', '00000700', 'redis', 702);
insert into `category` (id, parent, name, sort) values ('00000703', '00000700', 'mongodb', 703);

--- course-category
drop table if exists `course_category`;
create table `course_category` (
  `id` char(8) not null default '' comment 'id',
  `course_id` char(8) comment 'course|course.id',
  `category_id` char(8) comment 'category|course.id',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='course category';

--- course content
drop table if exists `course_content`;
create table `course_content` (
  `id` char(8) not null default '' comment 'course id',
  `content` mediumtext not null comment 'course content',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='course content';


-- teacher
drop table if exists `teacher`;
create table `teacher` (
  `id` char(8) not null default '' comment 'id',
  `name` varchar(50) not null comment 'name',
  `nickname` varchar(50) comment 'nickname',
  `image` varchar(100) comment 'image',
  `position` varchar(50) comment 'position',
  `motto` varchar(50) comment 'motto',
  `intro` varchar(500) comment 'intro',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='teacher';


-- File
drop table if exists `file`;
create table `file` (
  `id` char(8) not null default '' comment 'id',
  `path` varchar(100) not null comment 'relative path',
  `name` varchar(100) comment 'file name',
  `suffix` varchar(10) comment 'suffix',
  `size` int comment 'size|byte',
  `use` char(1) comment 'application|Enum[FileUseEnum]：COURSE("C", "Course"), TEACHER("T", "Teacher")',
  `created_at` datetime(3) comment 'created_at',
  `updated_at` datetime(3) comment 'updated_at',
  primary key (`id`),
  unique key `path_unique` (`path`)
) engine=innodb default charset=utf8mb4 comment='file';

alter table `file` add column (`shard_index` int comment 'uploaded file section');
alter table `file` add column (`shard_size` int comment 'file section size');
alter table `file` add column (`shard_total` int comment 'total file section');
alter table `file` add column (`key` varchar(32) comment 'file key');
alter table `file` add unique key key_unique (`key`);


-- course content file
drop table if exists `course_content_file`;
create table `course_content_file` (
  `id` char(8) not null default '' comment 'id',
  `course_id` char(8) not null comment 'course id',
  `url` varchar(100) comment 'url address',
  `name` varchar(100) comment 'file name',
  `size` int comment 'size|byte',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='course content file';

-- user
drop table if exists `user`;
create table `user` (
  `id` char(8) not null default '' comment 'id',
  `login_name` varchar(50) not null comment 'login name',
  `name` varchar(50) comment 'nick name',
  `password` char(32) not null comment 'password',
  primary key (`id`),
  unique key `login_name_unique` (`login_name`)
) engine=innodb default charset=utf8mb4 comment='user';

insert into `user` (id, login_name, name, password) values ('10000000', 'test', '测试', '202cb962ac59075b964b07152d234b70');


-- Resource
drop table if exists `resource`;
create table `resource` (
  `id` char(6) not null default '' comment 'id',
  `name` varchar(100) not null comment 'name|menu or button',
  `page` varchar(50) null comment 'page|router',
  `request` varchar(200) null comment 'request|interface',
  `parent` char(6) comment 'parent id',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='resource';

insert into `resource` values ('01', '系统管理', null, null, null);
insert into `resource` values ('0101', '用户管理', '/system/user', null, '01');
insert into `resource` values ('010101', '保存', null, '["/system/admin/user/list", "/system/admin/user/save"]', '0101');
insert into `resource` values ('010102', '删除', null, '["/system/admin/user/delete"]', '0101');
insert into `resource` values ('010103', '重置密码', null, '["/system/admin/user/save-password"]', '0101');
insert into `resource` values ('0102', '资源管理', '/system/resource', null, '01');
insert into `resource` values ('010201', '保存/显示', null, '["/system/admin/resource"]', '0102');
insert into `resource` values ('0103', '角色管理', '/system/role', null, '01');
insert into `resource` values ('010301', '角色/权限管理', null, '["/system/admin/role"]', '0103');


-- role
drop table if exists `role`;
create table `role` (
  `id` char(8) not null default '' comment 'id',
  `name` varchar(50) not null comment 'character',
  `desc` varchar(100) not null comment 'description',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='character';

insert into `role` values ('00000000', '系统管理员', '管理用户、角色权限');
insert into `role` values ('00000001', '开发', '维护资源');
insert into `role` values ('00000002', '业务管理员', '负责业务管理');

-- role_resource
drop table if exists `role_resource`;
create table `role_resource` (
  `id` char(8) not null default '' comment 'id',
  `role_id` char(8) not null comment 'role|id',
  `resource_id` char(6) not null comment 'resource|id',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='role resource relationship';

insert into `role_resource` values ('00000000', '00000000', '01');
insert into `role_resource` values ('00000001', '00000000', '0101');
insert into `role_resource` values ('00000002', '00000000', '010101');
insert into `role_resource` values ('00000003', '00000000', '010102');
insert into `role_resource` values ('00000004', '00000000', '010103');
insert into `role_resource` values ('00000005', '00000000', '0102');
insert into `role_resource` values ('00000006', '00000000', '010201');
insert into `role_resource` values ('00000007', '00000000', '0103');
insert into `role_resource` values ('00000008', '00000000', '010301');


-- role_user
drop table if exists `role_user`;
create table `role_user` (
  `id` char(8) not null default '' comment 'id',
  `role_id` char(8) not null comment 'role|id',
  `user_id` char(8) not null comment 'user|id',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='role-user';

insert into `role_user` values ('00000000', '00000000', '10000000');


-----for test======
drop table if exists `test`;
create table `test` (
  `id` char(8) not null default '' comment 'id',
  `name` varchar(50) comment '名称',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `test` (id, name) values (1, '测试');
insert into `test` (id, name) values (2, '测试2');