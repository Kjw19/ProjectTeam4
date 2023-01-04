--펀딩 게시판 댓글
create table fund_comment(
	comment_num number,
	comm_content varchar2(300) not null,
	comm_reg_date date not null,
	mem_num number not null,
	fund_num number not null,
	constraint fund_comment_pk primary key (comment_num),
	constraint fund_comment_fk foreign key (mem_num)
                            references member (mem_num),
    constraint fund_comment_fk foreign key (fund_num)
                            references fund_board (fund_num)
);
create sequence fund_comment_seq;

--펀딩 게시판 문의
create table fund_inquiry()
	inquiry_num number,
	inqu_title varchar2(150) not null,
	inqu_content crob not null,
	inqu_reg_date date default sysdate not null,
	re_inqu_is_ok varchar2(500),
	mem_num number not null,
	fund_num number not null,
	constraint fund_inquiry_pk primary key (inquiry_num),
	constraint fund_inquiry_fk foreign key (mem_num)
                            references member (mem_num),
    constraint fund_inquiry_fk2 foreign key (fund_num)
                            references fund_board (fund_num)
);
create sequence fund_inquiry_seq;


--펀딩게시판 카테고리
create table fund_category(
	category_num number,
	category_name varchar2(50) not null,
	constraint fund_category_pk primary key (category_num)
);
create sequence fund_category_seq;



