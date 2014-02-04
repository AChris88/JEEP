use D0836605

create table mail_table(
	id			int unsigned not null primary key auto_increment,
	receiver	varchar(50) not null,
	sender		varchar(50) not null,
	subject		varchar(50) not null,
	cc			varchar(50) not null,
	bcc			varchar(50) not null,
	received	varchar(50) not null,
	message		varchar(512) not null,
	folder		varchar(50) not null
);
