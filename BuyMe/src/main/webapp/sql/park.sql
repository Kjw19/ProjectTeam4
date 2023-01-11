---[세부기능] : 문의 게시판 댓글 & 문의 관리
--문의 게시판 댓글 관리 (문의에 대한)
create table request_comment(
 comment_num number,
 comm_content varchar2(900) not null,
 comm_ip varchar2(40) not null,
 comm_reg_date date default sysdate not null,
 mem_num number not null,
 req_num number not null,
 constraint request_comment_pk primary key (comment_num),
 constraint request_comment_fk foreign key (mem_num) references member (mem_num),
 constraint request_comment_fk2 foreign key (req_num) references request_board (req_num)
);
create sequence req_comm_seq;

--문의 게시판 내의 응원 게시판
create table request_cheer(
 cheer_num number,
 cheer_content varchar2(900) not null,
 cheer_reg_date date default sysdate not null,
 cheer_modify_date date,
 cheer_ip varchar2(40) not null,
 mem_num number not null,
 constraint request_cheer_pk primary key (cheer_num),
 constraint request_cheer_fk foreign key (mem_num) references member (mem_num)
);
create sequence req_cheer_seq;

--문의 게시판 댓글 관리 (응원에 대한)
create table request_cheerComment(
 cheerComment_num number,
 cheerComm_title varchar2(150),
 cheerComm_content varchar2(900) not null,
 cheerComm_filename varchar2(150),
 cheerComm_reg_date date default sysdate not null,
 mem_num number not null,
 cheer_num number not null,
 constraint request_cheerComment_pk primary key (comment_num),
 constraint request_cheerComment_fk foreign key (mem_num) references member (mem_num),
 constraint request_cheerComment_fk2 foreign key (cheer_num) references request_cheer (cheer_num)
);
create sequence req_cheerComm_seq;

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
create sequence member_detail_seq;

--문의게시판 댓글·응원 테이블 삭제 예정
create table request_mainComment(
 mainComment_num number,
 mainComm_content varchar2(900) not null,
 mainComm_reg_date date default sysdate not null,
 mainComm_modify_date date,
 mainComm_ip varchar2(40) not null,
 mem_num number not null,
 constraint request_mainComment_pk primary key (mainComment_num),
 constraint request_mainComment_fk foreign key (mem_num) references member (mem_num)
);
create sequence req_mainComm_seq;

--문의게시판 내문의 테이블
create table request_myInquiry(
 myInquiry_num number,
 myInqu_title varchar2(150) not null, --문의 제목
 myInqu_content clob not null, --문의 내용
 re_myInqu_is_ok varchar2(900), --문의에 대한 답글 → 댓글로 대체하면 될 것 같기도
 myInqu_reg_date date default sysdate not null,
 myInqu_modify_date date,
 myInqu_filename varchar2(150),
 myInqu_ip varchar2(40) not null,
 mem_num number not null,
 constraint request_myInquiry_pk primary key (myInquiry_num),
 constraint request_myInquiry_fk foreign key (mem_num) references member (mem_num),
);
create sequence req_myInqu_seq;