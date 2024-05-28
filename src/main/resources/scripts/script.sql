drop tabl if exists `sys_menu`;
create table `sys_menu` (
    `id` int not null,
    `menu_name` varchar(64) not null default 'null' comment 'menu name',
    `path` varchar(64) not null default null comment 'route address',
    `component` varchar(255) default null comment 'component path',
    `visible` char(1) default '0' comment 'menu status',
    `status` char(1) default '0',
    `perms` varchar(100) default null,
    `icon` varchar(100) default '#',
    `create_by` bigint(20) default null,
    `create_time` datetime default null,
    `update_by` bigint(20) default null,
    `update_time` datetime default null,
    `del_flag` int(11) default '0',
    `remark` varchar(500) default null,
    primary key(`id`)
) engine=innodb auto_increment = 2 default charset=utf8mb4

drop table if exists `sys_role`;
create table `sys_role`(
    `id` bigint(20) not null auto_increment,
    `name` varchar(128) default null,
    `role_key` varchar(100) default null,
    `status` char(1) default '0',
    `del_flag` int(1) default '0',
    `create_by` bigint(200) default null,
    `create_time` datetime default null,
    `update_by` bigint(200) default null,
    `update_time` datetime default null,
    `remark` varchar(200) default null,
    primary key (`id`)
) ENGINE=INNODB auto_increment=3 default charset=utf8mb4

drop table if exists `sys_role_menu`;

create table `sys_role_menu`(
    `role_id` bigint(200) not null auto_increment,
    `menu_id` bigint(200) not null default '0',
    primary key (`role_id`, `menu_id`)
) engine=innodb auto_increment=2 default charset=utf8mb4

drop table if exists `sys_user`;

create table `sys_user` (
                            `id` bigint(20) not null auto_increment comment 'primary key',
                            `user_name` varchar(64) not null default 'null' comment 'username',
                            `nick_name` varchar(64) not null default 'null' comment 'nick name',
                            `password` varchar(64) not null default 'null' comment 'password',
                            `status` varchar(64) not null default 'null',
                            `email` varchar(64) default null,
                            `phone_number` varchar(32) default null,
                            `sex` char(1) default null,
                            `avatar` varchar(128) default null,
                            `user_type` char(1) not null default '1',
                            `create_by` bigint(20) default null,
                            `create_time` datetime default null,
                            `update_by` bigint(20) default null,
                            `update_time` datetime default null,
                            `del_flag` int(11) default '0',
                            primary key (`id`)
) engine=innodb auto_increment=2 default charset=utf8mb4;

drop table if exists `sys_user_role`;

create table `sys_user_role` (
    `user_id` bigint(200) not null auto_increment,
    `role_id` bigint(200) not null default '0',
    primary key (`user_id`, `role_id`)
) engine=innodb default charset=utf8mb4