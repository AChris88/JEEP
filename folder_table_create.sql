use D0836605

create table folders_table(
	id		int unsigned not null primary key auto_increment,
	folder	varchar(15) not null
);

insert into folders_table values(null, 'Inbox');
insert into folders_table values(null, 'Sent');
insert into folders_table values(null, 'Draft');
insert into folders_table values(null, 'Deleted');
