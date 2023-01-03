---공지 게시판 추천 테이블
create notice_like(
like_num number not null,
noti_num number not null,
mem_num  number not null,
constraint notice_like_pk primary key(like_num),
constraint notice_like_fk1 foreign key(noti_num) references notice_board (noti_num),
constraint notice_like_fk2 foreign key(mem_num) references member(mem_num)
);

create sequence notice_like_seq;

--공지게시판 댓글 테이블
create notice_comment
comment_num number not null,
comm_content varchar2(300) not null,
comm_reg_date date default sysdate not null,
mem_num number not null,
notice_num number not null,
constraint notice_comment_pk primary key(comment_num),
constraint notice_comment_fk1 foreign key(mem_num) references member(mem_num),
constraint notice_comment_fk2 foreign key(notice_num) references notice_board(noti_num)
);

create sequence notice_comment_seq;
