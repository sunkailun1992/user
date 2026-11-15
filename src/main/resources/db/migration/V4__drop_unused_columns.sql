-- 删除没人读写的列：13 张表的 label；三方客户端、外部身份、签名 nonce、OAuth 客户端四表的 code、description、sorting；
-- 角色数据范围、角色资源、用户角色、用户租户四张关系表的 sorting。服务代码、手写 SQL、接口、rpc-api 与 admin-web 都不用它们。
-- 删之前逐列核对：整列只有 NULL 或列默认值才删；只要有一行别的值就报错，整版回滚，服务停止启动，留给人工判断。
DO $$
DECLARE
    target record;
    column_default text;
    remaining bigint;
BEGIN
    FOR target IN
        SELECT * FROM (VALUES
            ('auth_dept', 'label'),
            ('auth_external_client', 'label'),
            ('auth_external_identity', 'label'),
            ('auth_external_signature_nonce', 'label'),
            ('auth_oauth_client', 'label'),
            ('auth_resource', 'label'),
            ('auth_role', 'label'),
            ('auth_role_data_scope', 'label'),
            ('auth_role_resource', 'label'),
            ('auth_tenant', 'label'),
            ('auth_user', 'label'),
            ('auth_user_role', 'label'),
            ('auth_user_tenant', 'label'),
            ('auth_external_client', 'code'),
            ('auth_external_client', 'description'),
            ('auth_external_client', 'sorting'),
            ('auth_external_identity', 'code'),
            ('auth_external_identity', 'description'),
            ('auth_external_identity', 'sorting'),
            ('auth_external_signature_nonce', 'code'),
            ('auth_external_signature_nonce', 'description'),
            ('auth_external_signature_nonce', 'sorting'),
            ('auth_oauth_client', 'code'),
            ('auth_oauth_client', 'description'),
            ('auth_oauth_client', 'sorting'),
            ('auth_role_data_scope', 'sorting'),
            ('auth_role_resource', 'sorting'),
            ('auth_user_role', 'sorting'),
            ('auth_user_tenant', 'sorting')
        ) AS drops(table_name, column_name)
    LOOP
        SELECT c.column_default INTO column_default
        FROM information_schema.columns c
        WHERE c.table_schema = current_schema() AND c.table_name = target.table_name AND c.column_name = target.column_name;
        IF NOT FOUND THEN
            CONTINUE;
        END IF;
        EXECUTE format('SELECT count(*) FROM %I WHERE %I IS NOT NULL%s', target.table_name, target.column_name,
                CASE WHEN column_default IS NULL THEN ''
                     ELSE format(' AND %I IS DISTINCT FROM (%s)', target.column_name, column_default) END)
            INTO remaining;
        IF remaining > 0 THEN
            RAISE EXCEPTION '%.% 还有 % 行数据，不能删除', target.table_name, target.column_name, remaining;
        END IF;
        EXECUTE format('ALTER TABLE %I DROP COLUMN %I', target.table_name, target.column_name);
    END LOOP;
END
$$;
