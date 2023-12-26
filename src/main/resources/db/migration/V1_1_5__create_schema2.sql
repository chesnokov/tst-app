drop table result;
drop table answer;
drop table question;
drop table exercise;

create table if not exists user_test (
    test_id int auto_increment primary key,
    name varchar(512) not null
);

create table if not exists question (
    question_id int auto_increment primary key,
    text varchar(512) not null
);

create table if not exists user_test_questions (
    id int auto_increment primary key,
    test_id int references user_test(test_id),
    question_id int references question(question_id)
);

create table if not exists answer (
    answer_id int auto_increment primary key,
    text varchar(512) not null,
    is_correct boolean not null
);

create table if not exists question_answers (
    id int auto_increment primary key,
    question_id int references question(question_id),
    answer_id int references answer(answer_id)
);

create table if not exists result (
    result_id int auto_increment primary key,
    user_test_name varchar(512) not null,
    question_count int not null,
    correct_answer_count int not null,
    date_time timestamp not null
);