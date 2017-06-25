
begin
  execute immediate 'drop table Books cascade constraint';
  exception
  when others then null;
end;
/
CREATE TABLE Books
(
  book_id     NUMBER(20)  PRIMARY KEY,
  name       VARCHAR2(50) ,
  pages_count    NUMBER(20) 
) ;

begin
  execute immediate 'drop table Authors cascade constraint';
  exception
  when others then null;
end;
/
CREATE TABLE Authors
(
  author_id     NUMBER(20)  PRIMARY KEY,
  first_name       VARCHAR2(50) ,
  last_name       VARCHAR2(50) ,
  age    NUMBER(20) 
) ;

begin
  execute immediate 'drop table BookShelfs cascade constraint';
  exception
  when others then null;
end;
/
CREATE TABLE BookShelfs
(
  book_shelf_id     NUMBER(20)  PRIMARY KEY,
  length    NUMBER(20,2) 
) ;

drop sequence book_id;
create sequence book_id;

create or replace trigger book_id_trg
before insert on books
for each row
begin
if :new.book_id is null then
select book_id.nextval into :new.book_id from dual;
end if;
end;
/

drop sequence author_id;
create sequence author_id;

create or replace trigger author_id_trg
before insert on authors
for each row
begin
if :new.author_id is null then
select author_id.nextval into :new.author_id from dual;
end if;
end;
/

drop sequence book_shelf_id;
create sequence book_shelf_id;

create or replace trigger book_shelf_id_trg
before insert on BookShelfs
for each row
begin
if :new.book_shelf_id is null then
select book_shelf_id.nextval into :new.book_shelf_id from dual;
end if;
end;
/




COMMIT;