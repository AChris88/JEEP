use D0836605

create table contacts_table(
	id			int unsigned not null primary key auto_increment,
	first_name	varchar(50) not null,
	last_name 	varchar(50) not null,
	address		varchar(50) not null
);
