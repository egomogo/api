DROP TABLE IF EXISTS `menu`;
DROP TABLE IF EXISTS `restaurant_categories`;
DROP TABLE IF EXISTS `restaurant`;

CREATE TABLE `restaurant` (
    id varchar(40) not null primary key ,
    name varchar(255) not null ,
    address varchar(255) not null ,
    x float(53) default 0.0 not null ,
    y float(53) default 0.0 not null ,
    kakao_place_id varchar(50) not null ,
    api_called_at datetime ,
    scraped_at datetime ,
    created_at datetime not null ,
    updated_at datetime
);

CREATE TABLE `menu` (
    id varchar(40) not null primary key ,
    name varchar(255) not null ,
    price varchar(255) ,
    restaurant_id varchar(40) not null ,
    created_at datetime not null ,
    updated_at datetime,
    FOREIGN KEY (restaurant_id) REFERENCES `restaurant` (id)
);

CREATE TABLE `restaurant_categories` (
    restaurant_id varchar(40) not null ,
    categories varchar(100) not null ,
    FOREIGN KEY (restaurant_id) REFERENCES `restaurant` (id)
);
