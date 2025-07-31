create table teams(
                     id int primary key auto_increment,
                     name varchar(30) not null,
                     commission decimal(1, 2) not null,
                     balance decimal(19, 2) not null
);

create table players(
                       id int primary key auto_increment,
                       name varchar(30) not null,
                       debut_date date not null,
                       age varchar(3) not null,
                       team_id int not null,
                       foreign key (team_id) references teams(id)
                           on update cascade
                           on delete cascade
);