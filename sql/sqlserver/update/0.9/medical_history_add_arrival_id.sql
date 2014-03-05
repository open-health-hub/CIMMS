USE stroke;
go

alter table medical_history add arrival_id numeric(19,0) NULL;
alter table medical_history add constraint FK26EB84E66EA1BA98 foreign key (arrival_id) references arrival;

alter table medical_history add inpatient_at_onset TINYINT NULL;