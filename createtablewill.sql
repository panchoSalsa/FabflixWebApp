-- DROP DATABASE moviedb;
-- CREATE DATABASE moviedb;
-- USE moviedb;

-- CREATE TABLE movies( 
-- 	id int NOT NULL AUTO_INCREMENT, 
-- 	title varchar(100) NOT NULL, 
-- 	year int NOT NULL, 
-- 	director varchar(100) NOT NULL, 
-- 	banner_url varchar(200) DEFAULT '', 
-- 	trailer_url varchar(200) DEFAULT '',
-- 	PRIMARY KEY(id)
-- );

-- CREATE TABLE stars( 
-- 	id int NOT NULL AUTO_INCREMENT, 
-- 	first_name varchar(50) NOT NULL, 
-- 	last_name varchar(50) NOT NULL, 
-- 	dob date DEFAULT NULL, 
-- 	photo_url varchar(200) DEFAULT '',
-- 	PRIMARY KEY(id)
-- );

-- CREATE TABLE stars_in_movies( 
-- 	star_id int NOT NULL, 
-- 	movie_id int NOT NULL, 
-- 	PRIMARY KEY(star_id, movie_id),
-- 	FOREIGN KEY(star_id) REFERENCES stars(id) ON DELETE CASCADE,
--     FOREIGN KEY(movie_id) REFERENCES movies(id) ON DELETE CASCADE
-- );

-- CREATE TABLE genres(
--         id int NOT NULL AUTO_INCREMENT,
--         name varchar(32) NOT NULL, 
--         PRIMARY KEY(id)
-- );

-- CREATE TABLE genres_in_movies(
--         genre_id int NOT NULL,
--         movie_id int NOT NULL, 
--         PRIMARY KEY(genre_id, movie_id),
--         FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE,
--         FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
-- );

-- CREATE TABLE creditcards(
--         id varchar(20) NOT NULL,
--         first_name varchar(50) NOT NULL,
--         last_name varchar(50) NOT NULL,
--         expiration date NOT NULL,
--         PRIMARY KEY(id)
-- );

-- CREATE TABLE customers(
--         id int NOT NULL AUTO_INCREMENT, 
--         first_name varchar(50) NOT NULL,
--         last_name varchar(50) NOT NULL,
--         cc_id varchar(20) NOT NULL,
--         address varchar(200) NOT NULL,
--         email varchar(50) NOT NULL,
--         password varchar(20) NOT NULL,
--         PRIMARY KEY(id),
--         FOREIGN KEY(cc_id) REFERENCES creditcards(id) ON DELETE CASCADE
-- );

-- CREATE TABLE sales(
--         id int NOT NULL AUTO_INCREMENT,
--         customer_id int NOT NULL,
--         movie_id int NOT NULL,
--         sale_date date NOT NULL, 
--         PRIMARY KEY(id),
--         FOREIGN KEY(customer_id) REFERENCES customers(id) ON DELETE CASCADE,
--         FOREIGN KEY(movie_id) REFERENCES movies(id) ON DELETE CASCADE
-- );

-- drop procedure add_movie;

delimiter //
create procedure add_movie(
in title_arg varchar(100), in year_arg int,
 in director_arg varchar(100), in first_name_arg varchar(50),
 in last_name_arg varchar(50), in genre_name_arg varchar(32),
 out o text)

begin
set @changes = 0;

set o = '';

if not exists (select * from movies where title = title_arg and `year` = year_arg
    and director = director_arg)
then insert into movies (title, year, director) 
     values(title_arg, year_arg, director_arg);
     set o = concat('<br><br>New Movie<br>Title: ', title_arg, 
     '<br>Year: ', year_arg, 
     '<br>Director: ', director_arg, 
     '<br>Successfully created!<br>');
	 set @changes = 1;
END IF;
    
if not exists (select * from stars where first_name = first_name_arg and 
    last_name = last_name_arg)
then insert into stars (first_name, last_name) values(first_name_arg, last_name_arg);
	 set o = concat(o, '<br>New Star: ', first_name_arg, ' ', last_name_arg, '<br>Successfully created!<br>');
     set @changes = 1;
END IF;

if not exists (select * from genres where name = genre_name_arg)
    then insert into genres (name) values (genre_name_arg);
	set o = concat(o, '<br>New Genre: ', genre_name_arg, '<br>Successfully created!<br>');
	set @changes = 1;
END IF;

set @id_m = (select id from movies where title = title_arg and `year` = year_arg
    and director = director_arg);
set @id_star = (select id from stars where first_name = first_name_arg and 
    last_name = last_name_arg);
set @g_id = (select id from genres where name = genre_name_arg);

if not exists (select * from stars_in_movies where star_id = @id_star AND movie_id = @id_m)
then insert into stars_in_movies values (@id_star, @id_m);
	 set o = concat(o, '<br>Star "', first_name_arg, ' ', last_name_arg, 
     '" successfully linked to movie "', title_arg, '"!<br>');
    set @changes = 1;
END IF;

if not exists (select * from genres_in_movies where genre_id = @g_id AND movie_id = @id_m)
then insert into genres_in_movies values (@g_id, @id_m);
	 set o = concat(o, '<br>Genre "', genre_name_arg, 
     '" successfully linked to movie "', title_arg, '"!<br>');
    set @changes = 1;
END IF;

if @changes = 0
then set o = '<br>Invalid input. No changes have been made<br>';
END IF;
END //
delimiter ;