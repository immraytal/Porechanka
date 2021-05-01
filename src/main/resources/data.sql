insert into user_roles(role_name) values
('ROLE_ADMIN'),
('ROLE_USER');

insert into users(email, phone_number, first_name, surname, birth, age, sex, rating, user_status, role_id, created_date,
                  created_time)
values ('qwe@qwe.qqwe', '292042387', 'Andrey', 'Kisel', '2000-07-07', 19, 'MALE', 123, 'ACTIVE', 1, '2018-01-01',
        '23:12:10');
insert into users(email, phone_number, first_name, surname, birth, age, sex, rating, user_status, role_id, created_date,
                  created_time)
values ('test@qwe.qqwe', '292333387', 'Alexandra', 'Brown', '2001-07-07', 18, 'FEMALE', 3211, 'ACTIVE', 2, '2018-11-01',
        '12:44:19');
insert into users(email, phone_number, first_name, surname, birth, age, sex, rating, user_status, role_id, created_date,
                  created_time)
values ('third@qwe.qqwe', '292322187', 'Mike', 'Stone', '1999-07-07', 20, 'MALE', 100, 'ACTIVE', 2, '2018-08-19',
        '15:12:10');
insert into users(email, phone_number, first_name, surname, birth, age, sex, rating, user_status, role_id, created_date,
                  created_time)
values ('fourth@qwe.qqwe', '292375587', 'Dmitriy', 'Bereza', '1980-10-17', 39, 'MALE', 15211, 'ACTIVE', 2, '2020-02-05',
        '21:14:45');
insert into users(email, phone_number, first_name, surname, birth, age, sex, rating, user_status, role_id, created_date,
                  created_time)
values ('another.email@qwe.qqwe', '292333111', 'Olga', 'Vasilyvska', '1960-07-07', 59, 'FEMALE', 4444, 'ACTIVE', 2,
        '2018-10-11',
        '20:32:10');


insert into user_auths(user_id, login, pass)
values (1, 'Admin', 'somepassword');
insert into user_auths(user_id, login, pass)
values (2, 'Alex_Andra', 'somepassword2');
insert into user_auths(user_id, login, pass)
values (3, 'RollingStone', 'somepassword2');
insert into user_auths(user_id, login, pass)
values (4, 'Dubdub20', 'somepassword2');
insert into user_auths(user_id, login, pass)
values (5, 'Ooolga', 'somepassword2');


insert into chats(first_user_id, second_user_id)
values (1, 2);
insert into chats(first_user_id, second_user_id)
values (1, 3);
insert into chats(first_user_id, second_user_id)
values (4, 5);
insert into chats_of_users(user_id, chat_id)
values (1, 1);
insert into chats_of_users(user_id, chat_id)
values (2, 1);
insert into chats_of_users(user_id, chat_id)
values (1, 2);
insert into chats_of_users(user_id, chat_id)
values (3, 2);
insert into chats_of_users(user_id, chat_id)
values (4, 3);
insert into chats_of_users(user_id, chat_id)
values (5, 3);

insert into chat_messages(user_id, chat_id, text_msg, created_date, created_time)
values (1, 1, 'hello world,  ouch, i mean chat', '2020-05-31', '21:11:12');
insert into chat_messages(user_id, chat_id, text_msg, created_date, created_time)
values (2, 1, 'hey, whats up? :)', '2020-05-31', '21:14:23');
insert into chat_messages(user_id, chat_id, text_msg, created_date, created_time)
values (1, 2, 'hello, can i buy your boat for 2500$?', '2020-06-02', '12:10:10');
insert into chat_messages(user_id, chat_id, text_msg, created_date, created_time)
values (4, 3, 'discount 5%?', '2020-04-12', '16:09:23');
insert into chat_messages(user_id, chat_id, text_msg, created_date, created_time)
values (5, 3, 'nope', '2020-04-12', '16:11:51');

insert into advert_categories(name, description)
values ('Auto and transport', 'Adverts for sale of cars and various transport');
insert into advert_categories(name, description)
values ('Electronic', 'Adverts for sale of some electronics');
insert into advert_categories(name, description)
values ('Clothes', 'Just clothes');
insert into advert_categories(name, description)
values ('Book', 'no comments');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('Id to sell this boat', 'some pic and some description', 3000.12, 1, 'OPENED', '2020-01-01', '12:11:45', 2,
        true);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('Awesome clothes', 'Very beautiful clothes and socks', -1, 3, 'OPENED', '2019-10-21', '13:21:55', 5, true);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('Another cool boat', 'The coolest boat ever', 200, 1, 'OPENED', '2020-02-18', '17:26:12', 5, true);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('Cellphone NOKIA3310', 'can break bricks and the face your neighbor', 5, 2, 'OPENED', '2018-02-02', '18:21:32',
        3, true);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('Herb Schildts Java Programming Cookbook', 'ISBN 0-07-226315-6, Osborne/McGraw Hill, 2007', 120, 4, 'OPENED',
        '2020-01-01', '12:11:45', 4, false);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid,
                    close_date, close_time, buyer_id)
values ('Id to sell this boat', 'some pic and some description', 100, 1, 'CLOSED', '2020-01-01', '12:11:45', 2, false,
        '2020-06-10', '12:00:01', 1);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('deleted', 'update and then deleted', 10, 1, 'DELETED', '2018-11-23', '22:31:58', 2, false);

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid)
values ('very expensive', 'really very and without top', 9999999, 1, 'OPENED', '2020-02-05', '21:01:32', 1, false);

insert into advert_payments_for_top(paid_for_date, advert_id, verify_code)
values ('2020-10-05', 1, 'QWERTY');

insert into advert_payments_for_top(paid_for_date, advert_id, verify_code)
values ('2020-07-05', 2, 'QWERT1');

insert into advert_payments_for_top(paid_for_date, advert_id, verify_code)
values ('2020-07-05', 3, 'QWERT2');

insert into advert_payments_for_top(paid_for_date, advert_id, verify_code)
values ('2010-07-05', 4, 'QWERT--0105');

insert into advert_comments(advert_id, user_id, parent_comment_id, text_comment, created_date, created_time)
values (1, 1, null, 'first comment', '2020-05-05', '12:11:10');

insert into advert_comments(advert_id, user_id, parent_comment_id, text_comment, created_date, created_time)
values (1, 2, 1, 'second comment', '2020-05-05', '11:11:11');

insert into advert_comments(advert_id, user_id, parent_comment_id, text_comment, created_date, created_time)
values (1, 3, 1, 'thried comment', '2020-05-05', '11:11:11');

insert into advert_comments(advert_id, user_id, parent_comment_id, text_comment, created_date, created_time)
values (1, 4, null, 'real second comment', '2020-05-05', '11:11:11');

insert into advert_comments(advert_id, user_id, parent_comment_id, text_comment, created_date, created_time)
values (1, 4, 2, 'five... comment', '2020-05-05', '11:11:11');