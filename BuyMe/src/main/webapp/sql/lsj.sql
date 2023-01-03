--FUND_BOARD 펀딩 게시판 테이블
creat table fund_board(
 fund_num number not null, /*펀딩게시판 글 번호*/
 fund_title varchar2(50) not null,
 fund_content clob not null,
 fund_hit number(5)  default 0 not null,
 fund_reg_date date default sysdate not null,
 fund_modify_date date default sysdate
 fund_ip varchar2(40) not null,
 fund_filename varchar2(150),/*파일*/
 category_num number not null,/*카테고리 번호*/
 mem_num number not null,/*회원번호*/
 constraint fund_board_pk primary key (fund_num),
 constraint fund_board_fk1 foreign key (category_num)
 						references fund_category (category_num),
 constraint fund_board_fk2 foreign key (mem_num)
 						references member (mem_num)
);
create sequence fund_board_seq;

--FUND_LIKE 펀딩 게시판 추천 테이블
create table fund_like(
 like_num number not null,
 fund_num number not null,/*펀딩게시판 일련번호*/
 mem_num number not null,/*회원번호*/
 constraint fund_like_pk primary key (like_num),
 constraint fund_like_fk1 foreign key (fund_num)
 						references fund_board (fund_num),
 constraint fund_like_fk2 foreign key (mem_num)
 						references member (mem_num)
);
create sequence fund_like_seq;