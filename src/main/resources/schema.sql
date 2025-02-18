drop database if exists play_view;

create database play_view;

use play_view;

create table if not exists companies (
	company_id int primary key not null auto_increment,
    company_name varchar(255) unique not null
);

create table if not exists games (
	game_id int primary key not null auto_increment,
    cod_company int not null,
	title varchar(255) not null,
    cover_url varchar(255) not null,
    release_date date not null,
    game_description text not null,
    restriction varchar(3) not null,
    constraint fk_games_cod_company foreign key (cod_company) references companies (company_id)
);

create table if not exists genres (
	genre_id int primary key not null auto_increment,
	genre varchar(255) unique
);

create table if not exists publishers (
	publisher_id int primary key not null auto_increment,
	publisher_name varchar(255) not null
);

create table if not exists users (
	user_id int primary key not null auto_increment,
    user_name varchar(255) not null,
    email varchar(255) unique not null,
    user_password char(60) not null,
    profile_url varchar(255),
    tel varchar(255),
    birth_date date not null,
    user_status tinyint(1) not null default 1
);

create table if not exists `roles` (
	cod_user int not null,
    `role` varchar(50) not null,
    primary key (`cod_user`, `role`),
    constraint fk_roles_cod_user foreign key (cod_user) references users (user_id) on delete cascade
);

create table if not exists games_genres (
	cod_game int not null,
    cod_genre int not null,
    primary key (cod_game, cod_genre),
    constraint fk_games_genres_cod_game foreign key (cod_game) references games (game_id),
    constraint fk_games_genres_cod_genre foreign key (cod_genre) references genres (genre_id)
);

create table if not exists games_publishers (
	cod_game int not null,
    cod_publisher int not null,
    primary key (cod_game, cod_publisher),
    constraint fk_games_publishers_cod_game foreign key (cod_game) references games (game_id),
    constraint fk_games_publishers_cod_publisher foreign key (cod_publisher) references publishers (publisher_id)
);

create table if not exists games_reviews (
	cod_game int not null,
    cod_user int not null,
    review_id int primary key not null auto_increment,
    review text not null,
    constraint fk_games_reviews_cod_game foreign key (cod_game) references games (game_id),
    constraint fk_games_reviews_cod_user foreign key (cod_user) references users (user_id)
);