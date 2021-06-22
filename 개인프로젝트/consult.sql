--------------------------------------------------------
--  파일이 생성됨 - 화요일-6월-22-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CONSULT
--------------------------------------------------------

  CREATE TABLE "SCOTT"."CONSULT" 
   (	"CODE" VARCHAR2(20 BYTE), 
	"CONSULTANT" VARCHAR2(20 BYTE), 
	"NAME" VARCHAR2(20 BYTE), 
	"CON_DATE" DATE, 
	"CON_STAT" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into SCOTT.CONSULT
SET DEFINE OFF;
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A001','임꺽정','김철수',to_date('15/02/20','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A002','홍길동','전우치',to_date('16/05/13','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A003','홍길동','가나다',to_date('15/07/10','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A004','홍길동','라마바',to_date('18/08/05','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A005','홍길동','사아자',to_date('19/05/13','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B001','임꺽정','차카타',to_date('20/04/03','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B002','임꺽정','김훈이',to_date('18/05/01','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B003','임꺽정','김짱구',to_date('21/03/20','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B004','임꺽정','김맹구',to_date('21/01/21','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B005','임꺽정','김자바',to_date('21/02/22','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('A006','홍길동','김검색',to_date('05/03/05','RR/MM/DD'),'상담예약');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('B006','홍길동','이순신',to_date('20/02/02','RR/MM/DD'),'상담완료');
Insert into SCOTT.CONSULT (CODE,CONSULTANT,NAME,CON_DATE,CON_STAT) values ('C001','홍길동','전우치',to_date('22/03/23','RR/MM/DD'),'상담예약');
--------------------------------------------------------
--  DDL for Index SYS_C007166
--------------------------------------------------------

  CREATE UNIQUE INDEX "SCOTT"."SYS_C007166" ON "SCOTT"."CONSULT" ("CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table CONSULT
--------------------------------------------------------

  ALTER TABLE "SCOTT"."CONSULT" ADD PRIMARY KEY ("CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
