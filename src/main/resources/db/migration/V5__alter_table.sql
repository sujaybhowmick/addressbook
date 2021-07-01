alter table users add constraint uk_k8d0f2n7n88w1a16yhua64onx unique (user_name);
alter table address_books add constraint fk2dj8ohycf9g1lf18ypkcy8vxw foreign key (owner_id) references users;
alter table contacts add constraint fk53x4ihl1wmiriun7wj3vvlis0 foreign key (address_book_id) references address_books;
