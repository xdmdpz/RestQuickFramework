CREATE TABLE user_info
(
  id int AUTO_INCREMENT PRIMARY KEY,
  username varchar(50),
  password varchar(50),
  type tinyint(1),
  realname varchar(50),
  create_by int,
  create_time timestamp,
  update_by int,
  update_time timestamp
);
COMMENT ON COLUMN user_info.id IS '主键';
COMMENT ON COLUMN user_info.username IS '用户名';
COMMENT ON COLUMN user_info.password IS '密码';
COMMENT ON COLUMN user_info.type IS '用户类型';
COMMENT ON COLUMN user_info.realname IS '真实姓名';
COMMENT ON COLUMN user_info.create_by IS '创建人';
COMMENT ON COLUMN user_info.create_time IS '创建时间';
COMMENT ON COLUMN user_info.update_by IS '更新人';
COMMENT ON COLUMN user_info.update_time IS '更新时间';
COMMENT ON TABLE user_info IS '用户信息表';

