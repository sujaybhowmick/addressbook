create table contacts (id bigint not null, contact_hash varchar(25) not null, first_name varchar(100) not null, last_name varchar(100) not null, middle_name varchar(100), phone_number varchar(15) not null, address_book_id bigint, primary key (id));