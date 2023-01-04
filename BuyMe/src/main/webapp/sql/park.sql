---[세부기능] : 문의 게시판 댓글 & 문의 관리
--문의 게시판 댓글 관리
create table request_comment(
 comment_num number not null,
 comm_content varchar2(300) not null,
 comm_reg_date date not null,
 mem_num number not null,
 req_num number not null,
 constraint request_comment_pk primary key (comment_num),
 constraint request_comment_fk foreign key (mem_num) references member (mem_num),
 constraint request_comment_fk2 foreign key (req_num) references request_board (req_num)
);

create sequence req_comm_seq;

--문의 게시판 문의 관리
create table request_inquiry(
 inquiry_num number not null,
 inqu_title varchar2(150) not null,
 inqu_content clob not null,
 inqu_reg_date date not null,
 re_inqu_is_ok varchar2(500),
 mem_num number not null,
 req_num number not null,
 constraint request_inquiry_pk primary key (inquiry_num),
 constraint request_inquiry_fk foreign key (mem_num) references member (mem_num),
 constraint request_inquiry_fk2 foreign key (req_num) references request_board (req_num)
);

create sequence req_inqu_seq;