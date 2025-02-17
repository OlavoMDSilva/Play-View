insert into companies (company_name) values
('FromSoftware'),
('Bethesda Game Studios');

insert into games (cod_company, title, cover_url, release_date, game_description, restriction) values
(1, 'Dark Souls', 'https://store-images.s-microsoft.com/image/apps.21855.69479559702140610.dad571dd-6b2c-42a6-be9e-fd11d891d66c.e16a97f5-c50d-4fe7-91fd-1ed4a19aaca7?q=90&w=480&h=270',
'2011-09-22', 'Testing API Dark Souls 1', '14+'),
(1, 'Dark Souls 2', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHlt71E5i8wzxJKsM9-6jKyqf0MGNKv9IPCw&s',
'2014-03-11', 'Testing API Dark Souls 2', '14+'),
(2, 'Skyrim Special Edition', 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/489830/capsule_616x353.jpg?t=1721923149',
'2016-10-27', 'Testing API Skyrim Specal Edition', '17+');

insert into publishers (publisher_name) values
('FromSoftware, Inc'),
('Bandai Namco'),
('Bethesda Softworks');

insert into genres (genre) values
('Action'),
('RPG'),
('Souls Like'),
('Adventure'),
('Romance');

insert into games_publishers (cod_game, cod_publisher) values
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 3);

insert into games_genres (cod_game, cod_genre) values
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 4);

insert into users (user_name, email, user_password, birth_date) values
('Admin', 'adm@gmail.com', '$2a$10$1vmJKbilF0nTyQ8X/rZIR.fDIMIgq8yEWnzahd64s8KkdJNKSdUE6',
'2006-05-30'),
('User', 'user@gmail.com', '$2a$10$m7Fuz2o8PRbvQ5vhJjhO1.Zz90VrRiHH5l56OsYUYGe5Hmzv68ypS',
'2005-11-29');

insert into `roles` (cod_user, `role`) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

insert into games_reviews (cod_game, cod_user, review) values
(1, 1, 'TIME FOR A CHALLENGE RUN!!!');