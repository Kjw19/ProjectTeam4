-- 공지사항 게시판
create table notice_board(
	noti_num number,
	noti_title varchar2(100) not null,
	noti_content clob not null,
	noti_hit number(5) default 0 not null,
	noti_reg_date date default sysdate not null,
	noti_modify_date date,
	noti_filename varchar2(150),
	noti_ip varchar2(40) not null,
	mem_num number not null,
	constraint notice_board_pk primary key (noti_num),
	constraint notice_board_fk foreign key (mem_num) references member (mem_num)
);

create sequence noticeboard_seq;