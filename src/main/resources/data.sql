insert into exercise
    (ex_id, name)
values
('e1', 'Exercise 1'),
('e2', 'Exercise 2');

insert into question
    (qu_id, ex_id, text, seq)
values
('e1q1', 'e1', 'Question 1', 1),
('e1q2', 'e1', 'Question 2', 2),
('e1q3', 'e1', 'Question 3', 3),
('e2q1', 'e2', 'Question 1', 1),
('e2q2', 'e2', 'Question 2', 2);

insert into answer
    (qu_id, text, is_correct, seq)
values
('e1q1', 'Answer 1', TRUE, 1),
('e1q1', 'Answer 2', FALSE, 2),
('e1q2', 'Answer 1', FALSE, 1),
('e1q2', 'Answer 2', FALSE, 2),
('e1q2', 'Answer 3', TRUE, 3),
('e1q3', 'Answer 1', TRUE, 1),
('e1q3', 'Answer 2', FALSE, 2),
('e1q3', 'Answer 3', FALSE, 3),
('e1q3', 'Answer 4', FALSE, 4),
('e2q1', 'Answer 1', TRUE, 1),
('e2q1', 'Answer 2', FALSE, 2),
('e2q2', 'Answer 1', FALSE, 1),
('e2q2', 'Answer 2', TRUE, 2);
