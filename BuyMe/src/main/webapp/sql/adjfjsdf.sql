create table member(mem_num number,id varcher2(12) unique not null, 
auth number(1) default 1 not null,/*회원등급:0 비회원,1회원,2탈퇴,5관리자*/
constraint member_pk primary key(mem_num));