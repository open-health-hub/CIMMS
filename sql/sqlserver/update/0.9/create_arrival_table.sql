USE [STROKE]
GO

create table arrival (
	id numeric(19,0) identity not null, 
	version numeric(19,0) not null, 
	ambulance_trust_id numeric(19,0)  null, 
	arrive_by_ambulance tinyint  null, 
	cad_number numeric(19,0) null, 
	cad_number_unknown tinyint null, 
	outcome_questionnair_opt_out tinyint  null, 
	transferred_from_another_hospital tinyint NULL,
	this_hospital_arrival_date datetime NULL,
	this_hospital_arrival_time int NULL,
	primary key(id));
alter table arrival add constraint FKD43CE3F9B53E504D foreign key (ambulance_trust_id) references ambulance_trust_type;
/*

alter table [stroke].[dbo].[arrival]
	ADD 	
	transferred_from_another_hospital tinyint NULL,
	this_hospital_arrival_date datetime NULL,
	this_hospital_arrival_time int NULL;*/