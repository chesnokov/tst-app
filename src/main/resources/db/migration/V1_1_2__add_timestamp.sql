drop table result;

create table if not exists result (
    re_id identity,
    ex_name varchar(50) not null,
    correct_count int not null,
    ans_count int not null,
    date_time timestamp not null
);