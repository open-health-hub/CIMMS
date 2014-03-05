USE [stroke]
go

SET IDENTITY_INSERT [dbo].[cimss_role] ON
insert into [dbo].[cimss_role]( [id],[version],[authority])
VALUES (1,1, 'ROLE_ADMIN');
insert into [dbo].[cimss_role]( [id],[version],[authority])
VALUES (2,1, 'ROLE_THERAPIST');
SET IDENTITY_INSERT [dbo].[cimss_role] OFF
           

SET IDENTITY_INSERT [cimss_user] ON
insert into cimss_user (     
    [id],    [version],    [account_expired],    [account_locked],    [enabled],    [password],    [password_expired],    [username],    [first_name],    [surname])
    VALUES ( 1, 1, 0, 0, 1, 'c0e21a8ff85153deac82fe7f09c0da1b3bd90ac0ae204e78d7148753b4363c03', 0, 'admin', 'administrator', 'administrator');
insert into cimss_user (     
    [id],    [version],    [account_expired],    [account_locked],    [enabled],    [password],    [password_expired],    [username],    [first_name],    [surname])
    VALUES ( 2, 1, 0, 0, 1, 'eca8e05d94c236e78c389e15e1cad71ff9326bdfa5e1d79d92766f38414e66e5', 0, 'padgvi', 'Test', 'user');
SET IDENTITY_INSERT [cimss_user] OFF


INSERT INTO cimss_user_role(role_id,user_id) VALUES (1,1);
INSERT INTO cimss_user_role(role_id,user_id) VALUES (2,2);
GO
