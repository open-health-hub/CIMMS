use  users;
go


alter table user_role add constraint PK_URL_10 PRIMARY KEY (role_id, user_id);
