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
values ('Авто, запчасти б/у', 'Авто, запчасти б/у');
insert into advert_categories(name, description)
values ('Книги и журналы, литература, б/у', 'Книги и журналы, художественная литература, б/у');
insert into advert_categories(name, description)
values ('Одежда', 'Одежда');
insert into advert_categories(name, description)
values ('Ящики, коробки, корзины', 'Ящики, коробки, корзины');
insert into advert_categories(name, description)
values ('Бытовая техника', 'Бытовая техника');
insert into advert_categories(name, description)
values ('Домашние питомцы', 'Домашние питомцы');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Корзинка плетёная', 'Плетёная корзинка для мелочевки или для конфет.
', 10, 4, 'OPENED', '2021-04-02', '12:11:45', 2,
        true, 'https://cs1.livemaster.ru/storage/23/a2/c20645c76301b5d0bafdc379b0wx--dlya-doma-i-interera-pletenaya-korzina-gribnaya-klassika.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Опель Астра F по запчастям 1993 год', 'продается по запчастям', -1, 1, 'OPENED', '2021-02-21', '13:21:55', 5, true, 'https://cache3.youla.io/files/images/360_360/5d/e1/5de135a3e7696a70202b23f2.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Штамм. Трилогия (Гильермо дель Торо/Чак Хоган)', 'Стивен Кинг рекомендует! Трилогия Гильермо дель Торо и Чака ' ||
 'Хогана о вампирах известна по экранизации в одноименном телесериале канала FX. Сериал интересный, ' ||
  'но после прочтения книги вся история становится гораздо понятнее и логичнее. Трилогия аккуратно прочитана 1 раз, ' ||
   'в магазинах (как и на куфаре) уже давно не найти. Издание красивое, бумага белая и плотная, книги приятно держать в руках. ' ||
    'Цена указана за все три книги, по отдельности не продаются. В подарок закладка. Без торга, обмена и пересылки. ' ||
     'Только самовывоз (400м от Катюши).',
     40, 2, 'OPENED', '2021-02-18', '17:26:12', 5, true, 'https://cache3.youla.io/files/images/720_720_out/58/d0/58d00ae4e57ad42f227bf2bb.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Плащ женский синий', 'Плащ женский размер 42-44
', 20, 3, 'OPENED', '2011-02-02', '18:21:32',
        3, true, 'https://www.pohodd.ru/f/1c/import_files/68/6867124a-9462-11e6-a781-000c292c96e8_61e2c3ca-107b-11e7-92f5-000c292c96e8.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Стиральная машина BOSCH', 'Стиральная машина Bosch WAT24442BL', 240, 4, 'OPENED',
        '2020-04-11', '12:11:45', 5, false, 'https://st21.stpulscen.ru/images/product/321/058/835_big.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Щенки в добрые руки', 'В добрые руки !!Ищем дом для этих прекрасных щенков
Нашли щенков в стволе дерева ,безумно красивые и умные ! Уже самостоятельно едят . Переживаем об их дальнейшей судьбе ,тк скоро вырастут и могут разбежаться.
Поможем с доставкой домой', 0, 6, 'OPENED',
        '2021-04-11', '12:11:45', 5, false, 'https://gde.ru/images/img_ru/124x124/2c/32/2c32b5761ba84b8f4634833f7bc0ccb2.jpg');

insert into adverts(title, body, price, category_id, advert_status, open_date, open_time, seller_id, is_paid, photo_url)
values ('Котёнок в добрые руки', 'Приютите бедного котёнка', 0, 6, 'OPENED',
        '2021-02-23', '12:11:45', 5, false, 'https://86.img.avito.st/640x480/8949619986.jpg');

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