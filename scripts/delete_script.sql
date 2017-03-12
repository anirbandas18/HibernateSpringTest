truncate table id_gen;

drop table id_gen;

truncate table subject;

alter table subject drop foreign key fk_parent_subject;

drop table subject;

truncate table teacher;

alter table teacher drop foreign key fk_teacher_subject;

drop table teacher;