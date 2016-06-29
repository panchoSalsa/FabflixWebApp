CREATE TABLE movies( 
    id int NOT NULL AUTO_INCREMENT, 
    title varchar(100) NOT NULL, 
    year int NOT NULL, 
    director varchar(100) NOT NULL, 
    banner_url varchar(200) DEFAULT '', 
    trailer_url varchar(200) DEFAULT '',
    PRIMARY KEY(id)
)ENGINE = MYISAM;

CREATE TABLE stars( 
    id int NOT NULL AUTO_INCREMENT, 
    first_name varchar(50) NOT NULL, 
    last_name varchar(50) NOT NULL, 
    dob date DEFAULT NULL, 
    photo_url varchar(200) DEFAULT '',
    PRIMARY KEY(id)
);

CREATE TABLE stars_in_movies( 
    star_id int NOT NULL, 
    movie_id int NOT NULL, 
    PRIMARY KEY(star_id, movie_id),
    FOREIGN KEY(star_id) REFERENCES stars(id) ON DELETE CASCADE,
    FOREIGN KEY(movie_id) REFERENCES movies(id) ON DELETE CASCADE
)ENGINE = MYISAM;

CREATE TABLE genres(
        id int NOT NULL AUTO_INCREMENT,
        name varchar(32) NOT NULL, 
        PRIMARY KEY(id)
);

CREATE TABLE genres_in_movies(
        genre_id int NOT NULL,
        movie_id int NOT NULL, 
        PRIMARY KEY(genre_id, movie_id),
        FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE,
        FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
)ENGINE = MYISAM;

CREATE TABLE creditcards(
        id varchar(20) NOT NULL,
        first_name varchar(50) NOT NULL,
        last_name varchar(50) NOT NULL,
        expiration date NOT NULL,
        PRIMARY KEY(id)
);

CREATE TABLE customers(
        id int NOT NULL AUTO_INCREMENT, 
        first_name varchar(50) NOT NULL,
        last_name varchar(50) NOT NULL,
        cc_id varchar(20) NOT NULL,
        address varchar(200) NOT NULL,
        email varchar(50) NOT NULL,
        password varchar(20) NOT NULL,
        PRIMARY KEY(id),
        FOREIGN KEY(cc_id) REFERENCES creditcards(id) ON DELETE CASCADE
);

CREATE TABLE sales(
        id int NOT NULL AUTO_INCREMENT,
        customer_id int NOT NULL,
        movie_id int NOT NULL,
        sale_date date NOT NULL, 
        PRIMARY KEY(id),
        FOREIGN KEY(customer_id) REFERENCES customers(id) ON DELETE CASCADE,
        FOREIGN KEY(movie_id) REFERENCES movies(id) ON DELETE CASCADE
)ENGINE = MYISAM;

alter table movies add fulltext(title); 