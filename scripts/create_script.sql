create table subject (
	subject_id int,
	subject_name varchar(20),
	subject_parent int,
	subject_duration int,
	subject_credits int,
	constraint pk_subject_id primary key (subject_id),
	constraint subject_id_not_null check (subject_id is not null),
	constraint subject_name_not_null check (subject_name is not null),
	constraint subject_duration_not_null check (subject_duration is not null),
	constraint subject_credits_not_null check (subject_credits is not null),
	constraint fk_parent_subject foreign key (subject_parent) references subject(subject_id)
);

create table teacher (
	teacher_name varchar(50),
	teacher_email varchar(80),
	teacher_phone_no int,
	teacher_teaching_charge decimal(10,0),
	teacher_teaches_subject int,
	constraint teacher_name_not_null check (teacher_name is not null),
	constraint teacher_email_not_null check (teacher_email is not null),
	constraint teacher_phone_no_not_null check (teacher_phone_no is not null),
	constraint teaching_charge_not_null check (teacher_teaching_charge is not null),
	constraint pk_teacher_id primary key (teacher_name,teacher_teaches_subject),
	constraint fk_teaches_subject foreign key (teacher_teaches_subject) references subject(subject_id)
);

create table id_gen(
	gen_key varchar(20),
	gen_value int,
	constraint key_not_null check (gen_key is not null),
	constraint value_not_null check (gen_value is not null),
	constraint pk_id_gen_key primary key (gen_key)
);