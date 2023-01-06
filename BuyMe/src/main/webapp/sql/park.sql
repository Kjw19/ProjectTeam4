---[세부기능] : 문의 게시판 댓글 & 문의 관리
--문의 게시판 댓글 관리
create table request_comment(
 comment_num number,
 comm_content varchar2(300) not null,
 comm_reg_date date default sysdate not null,
 mem_num number not null,
 req_num number not null,
 constraint request_comment_pk primary key (comment_num),
 constraint request_comment_fk foreign key (mem_num) references member (mem_num),
 constraint request_comment_fk2 foreign key (req_num) references request_board (req_num)
);
create sequence req_comm_seq;

--문의 게시판 문의 관리
create table request_inquiry(
 inquiry_num number,
 inqu_title varchar2(150) not null, --문의 제목
 inqu_content clob not null, --문의 내용
 re_inqu_is_ok varchar2(500), --문의에 대한 답글 → 댓글로 대체하면 될 것 같기도
 inqu_reg_date date default sysdate not null,
 inqu_modify_date date,
 inqu_filename varchar2(150),
 inqu_ip varchar2(40) not null,
 mem_num number not null,
 req_num number not null,
 constraint request_inquiry_pk primary key (inquiry_num),
 constraint request_inquiry_fk foreign key (mem_num) references member (mem_num),
 constraint request_inquiry_fk2 foreign key (req_num) references request_board (req_num)
);
create sequence req_inqu_seq;

--회원 상세 정보 관리
create table member_detail(
 mem_num number, /* member의 mem_num을 가져다 쓰기때문에 pk이면서 fk 제약조건이 걸린다. */
 name varchar2(30) not null,
 passwd varchar2(12) not null,
 email varchar2(50) not null,
 phone varchar2(15) not null,
 zipcode varchar2(5) not null,
 address1 varchar2(90) not null,
 address2 varchar2(90) not null,
 photo varchar2(150),
 reg_date date default sysdate not null,
 modify_date date,
 constraint member_detail_pk primary key (mem_num), /* pk + fk */
 constraint member_detail_fk foreign key (mem_num) references member (mem_num)
);
create sequence member_seq;