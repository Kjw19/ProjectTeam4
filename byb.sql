--문의게시판 테이블
create table REQUEST_BOARD(
 req_num number,
 req_title varchar2(50) not null,
 req_content clob not null,
 req_hit number not null,
 req_reg_date date default sysdate not null,
 req_modify_date date not null,
 req_ip varchar2(40) not null,
 req_filename varchar2(150),
 mem_num number not null,
 
 constraint REQUEST_BOARD_pk primary key (req_num),
 constraint REQUEST_BOARD_fk1 foreign key (mem_num)
                            references MEM_NUM (mem_num)
                            
);
create sequence requestboard_seq;

