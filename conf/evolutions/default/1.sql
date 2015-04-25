
# -- ===========================================================
# --
# --	IT Tallaght, 
# --	Bart Bula, X00107883, 
# --	Andro Haavandi, X00057252
# --	April 2015 
# --
# -- =========================================================== 



# --- !Ups

create table account (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email)
);

create table course (
  course_id                 bigint not null auto_increment,
  course_type               varchar(20),
  course_name               varchar(100),
  course_description        varchar(255),
  course_morn_evn           varchar(20),
  course_length             integer,
  course_ppw                integer,
  constraint pk_course primary key (course_id)
   )
;

create table event (
  event_id                  bigint not null auto_increment,
  event_name                varchar(100),
  event_description1        varchar(255),
  event_description2        varchar(255),
  event_description3        varchar(255),   
  event_date                date,
  event_time                varchar(20),
  event_max_capacity        integer,
  event_curr_capacity       integer,
  constraint pk_event primary key (event_id)
  )
;

create table student (
  student_id                bigint not null auto_increment,
  student_username          varchar(20),
  student_password          varchar(20),
  student_fname             varchar(50),
  student_lname             varchar(50),
  student_gender            varchar(6),
  student_nationality       varchar(20),
  student_dob               date,
  student_street            varchar(100),
  student_city              varchar(100),
  student_province          varchar(100),
  student_country           varchar(100),
  student_phone             varchar(20),
  student_email             varchar(100),
  constraint pk_student primary key (student_id)
  )
;

create table stu_course (
  stu_course_id             bigint not null auto_increment,
  student_id                bigint,
  course_id                 bigint,
  course_status             varchar(20),
  course_start_date         date,
  course_end_date           date,
  course_level              varchar(20),
  course_attendance         integer,
  course_accommodation      varchar(20),
  course_is_paid            boolean,
  constraint pk_stu_course primary key (stu_course_id)
  )
;

create table stu_event (
  stu_event_id              bigint not null auto_increment,
  event_id                  bigint,
  student_id                bigint,
  constraint pk_stu_event primary key (stu_event_id)
  )
;


create sequence course_seq;
create sequence event_seq;
create sequence student_seq;
create sequence stu_course_seq;
create sequence stu_event_seq;


insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Morn: 9:30-13:30',2,195)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Evn: 18:00-21:00',2,180)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Morn: 9:30-13:30',4,185)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Evn: 18:00-21:00',4,170)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Morn: 9:30-13:30',8,175)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Evn: 18:00-21:00',8,160)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Morn: 9:30-13:30',12,165)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('general','General English','General English course','Evn: 18:00-21:00',12,150)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('examprep','IELTS Exam Preparation','Includes General Engilsh course and IELTS Exam Preparation module','Morn: 9:30-14:30',4,220)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('examprep','Cambrige Exam Preparation','Includes General Engilsh course and Cambrige Exam Preparation module','Morn: 9:30-14:30',4,200)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('examprep','TOEFL Exam Preparation','Includes General Engilsh course and TOEFL Exam Preparation module','Morn: 9:30-14:30',4,210)
;
insert into course (course_type, course_name, course_description, course_morn_evn, course_length, course_ppw)
values ('acyear','Academic Year Programme','Includes 25 weeks of General Engilsh course and 27 weeks holidays','Morn: 9:30-13:30',25,100)
;




insert into event(event_name, event_description1, event_description2, event_description3, event_date, event_time, event_max_capacity, event_curr_capacity)
values('Trip to Glendalough','Glendalough is famed for its beautiful scenery and historic monuments, but has also been a centre for pilgrims and visitors from the time of St. Kevin.','This is where St. Kevin founded his monastery in the sixth century. He lived here "in the hollow of a tree". His fame as a holy man spread and he attracted numerous followers.','The monastery would have included numerous dwellings, workshops, areas for manuscript writing and copying. The buildings which survive probably date from between the 8th and 12th centuries.','2015-05-16','08:00 AM',20,8)
;


insert into student (student_username, student_password, student_fname, student_lname, student_gender, student_nationality, student_dob, student_street, student_city, student_province, student_country, student_phone, student_email)
values('Student1','Student1','Yonohabla','Ingles','female','Spanish','1986-08-16','Calle Alguna','Algunaparte','Granada','Spain','012345678','yonohabla@ingles.com')
;

insert into stu_course(student_id, course_id, course_status, course_start_date, course_end_date, course_level, course_attendance, course_accommodation, course_is_paid)
values (1, 3, 'Completed', '2014-07-07', '2014-08-03', 'Intermediate', 98, 'Host Family', true)
;
insert into stu_course(student_id, course_id, course_status, course_start_date, course_end_date, course_level, course_attendance, course_accommodation, course_is_paid)
values (1, 5, 'In Progress', '2015-04-13', '2015-05-31', 'Upper Intermediate', 85, 'Host Family', true)
;
insert into stu_course(student_id, course_id, course_status, course_start_date, course_end_date, course_level, course_attendance, course_accommodation, course_is_paid)
values (1, 3, 'Saved for later', '2015-06-29', '2015-07-26', 'Not assessed', 0, 'Host Family', false)
;



insert into stu_event(event_id, student_id)
values (1, 1)
;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists stu_course;
drop table if exists stu_event;
drop table if exists course;
drop table if exists event;
drop table if exists student;
drop table if exists account;


SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists course_seq;
drop sequence if exists event_seq;
drop sequence if exists student_seq;
drop sequence if exists stu_course_seq;
drop sequence if exists stu_event_seq;



