CREATE TABLE GROUPS (
    group_id bigserial PRIMARY KEY,
    group_name varchar(128) NOT NULL
);

CREATE TABLE SUBJECT (
    subject_id bigserial PRIMARY KEY,
    subject_name varchar(200) NOT NULL
);

CREATE TABLE GROUPS_SUBJECTS (
    group_id bigint,
    subject_id bigint,
    FOREIGN KEY (group_id) REFERENCES GROUPS(group_id),
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(subject_id)
);

CREATE TABLE STUDENT (
    student_id bigserial PRIMARY KEY,
    full_name varchar(128) NOT NULL,
    email varchar(128) UNIQUE NOT NULL,
    password varchar(128) NOT NULL,
    phone_num varchar(11) NOT NULL,
    group_id bigint,
    FOREIGN KEY (group_id) REFERENCES GROUPS(group_id)
);

CREATE TABLE TEACHER (
    teacher_id bigserial PRIMARY KEY,
    full_name varchar(128) NOT NULL,
    email varchar(128) UNIQUE NOT NULL,
    password varchar(128) NOT NULL,
    phone_num varchar(11) NOT NULL,
    academic_degree varchar(128) NOT NULL
);

CREATE TABLE TEACHERS_GROUPS (
    teacher_id bigint,
    group_id bigint,
    FOREIGN KEY (teacher_id) REFERENCES TEACHER(teacher_id),
    FOREIGN KEY (group_id) REFERENCES GROUPS(group_id)
);

CREATE TABLE TASK (
    task_id bigserial PRIMARY KEY,
    task_name varchar(128) NOT NULL,
    description text,
    start_line timestamp NOT NULL,
    dead_line timestamp NOT NULL,
    teacher_id bigint,
    group_id bigint,
    FOREIGN KEY (teacher_id) REFERENCES TEACHER(teacher_id),
    FOREIGN KEY (group_id) REFERENCES GROUPS(group_id)
);

CREATE TABLE TASKS_FILES (
    task_id bigint,
    file_uri varchar(250) NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASK(task_id)
);

CREATE TABLE RATING (
    rating_id bigserial PRIMARY KEY,
    mark smallint NOT NULL,
    comment text,
    task_id bigint,
    subject_id bigint,
    student_id bigint,
    FOREIGN KEY (task_id) REFERENCES TASK(task_id),
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(subject_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
);

CREATE TABLE TASK_ANSWER (
    task_answer_id bigserial PRIMARY KEY,
    comment text,
    student_id bigint,
    task_id bigint,
    date timestamp NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASK(task_id),
    FOREIGN KEY (student_id) REFERENCES STUDENT(student_id)
);

CREATE TABLE ANSWERS_FILES (
    task_answer_id bigint,
    file_uri varchar(250) NOT NULL,
    FOREIGN KEY (task_answer_id) REFERENCES TASK_ANSWER(task_answer_id)
);

CREATE TABLE TEACHERS_SUBJECTS (
    teacher_id bigint,
    subject_id bigint,
    FOREIGN KEY (teacher_id) REFERENCES TEACHER(teacher_id),
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(subject_id)
)





