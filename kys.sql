
--공지사항 문의 테이블--
create table notice_inquiry(
 inquiry_num number not null,
 inqu_title varchar2(150) not null,
 inqu_content crob not null,
 inqu_reg_date date default sysdate not null,
 re_inqu_is_ok varchar2(500),
 mem_num number not null,
 notice_num number not null,
 constraint notice_inquiry_pk primary key (inquiry_num),
 constraint notice_inquiry_fk foreign key (mem_num)
 							references member(mem_num),
 constraint notice_inquiry_fk foreign key (notice_num)
 							references notice_board(noti_num)
);
create sequence noticeinquiry_seq;

