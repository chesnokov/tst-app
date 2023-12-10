create table if not exists exercise (
    ex_id varchar(10) primary key,
    name varchar(50) not null
);

create table if not exists question (
    qu_id varchar(10) primary key,
    ex_id varchar(10) references exercise(ex_id),
    text varchar(512) not null,
    seq int not null
);

create table if not exists answer (
    qu_id varchar(10) references question(qu_id),
    text varchar(512) not null,
    is_correct boolean,
    seq int not null
);