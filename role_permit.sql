# 创建项目数据库管理表，通过数据库管理表对每张表的权限进行管理
CREATE TABLE IF NOT EXISTS `sys_table`(
                                         `table_id` INT UNSIGNED AUTO_INCREMENT,
                                         `table_name` VARCHAR(60) NOT NULL,
                                         `table_description` VARCHAR(60) NOT NULL,
                                         PRIMARY KEY ( `table_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建项目权限列表，通过权限表来进行权限的动态管理，其中permit_table是权限所依赖的数据表
CREATE TABLE IF NOT EXISTS `sys_permit`(
                                         `permit_id` INT UNSIGNED AUTO_INCREMENT,
                                         `permit_name` VARCHAR(60) NOT NULL,
                                         `permit_description` VARCHAR(60) NOT NULL,
                                         `permit_url` varchar(120) not null,
                                         `permit_table` int unsigned not null ,
                                         foreign key (`permit_table`) references sys_table(table_id),
                                         PRIMARY KEY ( `permit_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建项目角色列表，通过角色列表来进行角色的动态管理
CREATE TABLE IF NOT EXISTS `sys_role`(
                                         `role_id` INT UNSIGNED AUTO_INCREMENT,
                                         `role_name` VARCHAR(60) NOT NULL,
                                         `role_description` VARCHAR(60) NOT NULL,
                                         PRIMARY KEY ( `role_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


# 创建用户表，进行用户管理
CREATE TABLE IF NOT EXISTS `user`(
                                   `user_id` INT UNSIGNED AUTO_INCREMENT,
                                   `user_name` varchar(60) NOT NULL,
                                   `role_id` int unsigned not null,
                                   PRIMARY KEY ( `user_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建角色权限表，通过角色权限表来进行权限的动态管理
CREATE TABLE IF NOT EXISTS `sys_permit_role`(
                                         `permit_role_id` INT UNSIGNED AUTO_INCREMENT,
                                         `permit_id` int unsigned NOT NULL,
                                         `role_id` int unsigned not null,
                                         foreign key (`permit_id`) references sys_permit(`permit_id`),
                                         foreign key (`role_id`) references sys_role(`role_id`),
                                         PRIMARY KEY ( `permit_role_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建用户角色表，通过用户角色表来进行用户角色的动态管理，即一个用户可以拥有多个角色
CREATE TABLE IF NOT EXISTS `sys_role_user`(
                                              `role_user_id` INT UNSIGNED AUTO_INCREMENT,
                                              `user_id` int unsigned NOT NULL,
                                              `role_id` int unsigned not null,
                                              foreign key (`user_id`) references user(`user_id`),
                                              foreign key (`role_id`) references sys_role(`role_id`),
                                              PRIMARY KEY ( `role_user_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 角色权限设计模型遵守的基本理念是：一个用户可以拥有多个角色，角色的权限可以动态调整，权限的设置与数据表相关

# 在互联网+羊项目中，根据实际情况
# 在权限表中，增加等级——10\20\30\40\50\60，其中10为最高等级，只有总系统管理员可以修改（即写代码者），默认为10
# 在角色表中，增加等级——10\20\30\40\50\60，其中10为最高等级，只有总系统管理员可以修改（即写代码者），默认为10
# 在权限表中，增加借口请求方法
alter table sys_permit add `permit_rank` int unsigned not null default 10;
alter table sys_role add `role_rank` int unsigned not null default 10;
alter table sys_permit add `permit_method` varchar(10) not null;

select sys_permit_role.permit_id, sys_permit_role.role_id,
       sys_role.role_name, sys_role.role_description, sys_role.role_rank,
       sys_permit.permit_description, sys_permit.permit_url, sys_permit.permit_table, sys_permit.permit_rank, sys_permit.permit_method,
       sys_table.table_name, sys_table.table_description
from sys_permit_role, sys_role, sys_permit, sys_table
where sys_permit_role.role_id = sys_role.role_id
  and sys_permit_role.permit_id = sys_permit.permit_id
  and sys_permit.permit_table = sys_table.table_id;

desc user;

select max(table_id) as max
from sys_table;

select max(permit_id) as max
from sys_permit;

select max(role_id) as max
from sys_role;

select max (user_id) as max
from user
