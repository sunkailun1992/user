-- 排序列 sorting 改名 sort（第6阶段第三部分，对应 utils 2.0.9 的 EntityBase.sort）。
-- 只改列名，列注释、默认值与数据随列保留；本库没有名字或定义里带 sorting 的索引。
-- 每张表只由本服务读写、前后端同批发布，按《Refactoring Databases》的 Rename Column 直接改名，不留过渡列。

-- 认证部门表。
ALTER TABLE auth_dept RENAME COLUMN sorting TO sort;

-- 认证资源表。
ALTER TABLE auth_resource RENAME COLUMN sorting TO sort;

-- 认证角色表。
ALTER TABLE auth_role RENAME COLUMN sorting TO sort;

-- 角色自定义数据范围表。
ALTER TABLE auth_role_data_scope RENAME COLUMN sorting TO sort;

-- 认证角色资源关系表。
ALTER TABLE auth_role_resource RENAME COLUMN sorting TO sort;

-- 认证租户表。
ALTER TABLE auth_tenant RENAME COLUMN sorting TO sort;

-- 认证用户表。
ALTER TABLE auth_user RENAME COLUMN sorting TO sort;

-- 认证用户角色关系表。
ALTER TABLE auth_user_role RENAME COLUMN sorting TO sort;

-- 认证用户租户关联表。
ALTER TABLE auth_user_tenant RENAME COLUMN sorting TO sort;
