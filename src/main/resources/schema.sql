create table if not exists user_roles
(
  id        bigint      not null auto_increment,
  role_name varchar(16) not null,
  primary key (id)
);

create table if not exists users
(
  id           bigint             not null auto_increment,
  email        varchar(64) unique not null,
  phone_number varchar(12) unique not null,
  first_name   varchar(32)        not null,
  surname      varchar(32)        not null,
  birth        date               not null,
  age          smallint           not null,
  sex          char(7)            not null,
  rating       bigint             not null,
  user_status  varchar(16)        not null,
  role_id      bigint             not null,
  created_date date               not null,
  created_time time               not null,
  primary key (id),
  foreign key (role_id) references user_roles (id)
);

create table if not exists user_auths
(
  user_id bigint             not null,
  login   varchar(32) unique not null,
  pass    varchar(255)       not null,
  foreign key (user_id) references users (id)
);

create table if not exists chats
(
  id             bigint not null auto_increment,
  first_user_id  bigint not null,
  second_user_id bigint not null,
  primary key (id),
  foreign key (first_user_id) references users (id),
  foreign key (second_user_id) references users (id)
);

create table if not exists chat_messages
(
  id           bigint       not null auto_increment,
  user_id      bigint       not null,
  chat_id      bigint       not null,
  text_msg     varchar(255) not null,
  created_date date         not null,
  created_time time         not null,
  primary key (id),
  foreign key (user_id) references users (id),
  foreign key (chat_id) references chats (id)
);

create table if not exists chats_of_users
(
  user_id bigint not null,
  chat_id bigint not null,
  foreign key (user_id) references users (id),
  foreign key (chat_id) references chats (id)
);

create table if not exists advert_categories
(
  id          bigint             not null auto_increment,
  name        varchar(32) unique not null,
  description varchar(128),
  primary key (id)
);

create table if not exists adverts
(
  id            bigint         not null auto_increment,
  title         varchar(64)    not null,
  price         decimal(16, 2) not null,
  body          text           not null,
  category_id   bigint         not null,
  advert_status varchar(8)     not null,
  open_date     date           not null,
  open_time     time           not null,
  close_date    date,
  photo_url     varchar(256),
  close_time    time,
  seller_id     bigint         not null,
  is_paid       boolean        not null,
  buyer_id      bigint,
  primary key (id),
  foreign key (seller_id) references users (id),
  foreign key (buyer_id) references users (id),
  foreign key (category_id) references advert_categories (id)
);

create table if not exists advert_payments_for_top
(
  id            bigint             not null auto_increment,
  paid_for_date date               not null,
  advert_id     bigint,
  verify_code   varchar(32) unique not null,
  primary key (id),
  foreign key (advert_id) references adverts (id)
);

create table if not exists advert_comments
(
  id                bigint       not null auto_increment,
  advert_id         bigint,
  user_id           bigint       not null,
  parent_comment_id bigint,
  text_comment      varchar(255) not null,
  created_date      date         not null,
  created_time      time         not null,
  primary key (id),
  foreign key (advert_id) references adverts (id),
  foreign key (user_id) references users (id),
  foreign key (parent_comment_id) references advert_comments (id)
);