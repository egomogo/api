DROP TABLE IF EXISTS `menu`;
DROP TABLE IF EXISTS `restaurant`;
DROP TABLE IF EXISTS `restaurant_categories`;

CREATE TABLE `restaurant` (
    id varchar(40) not null primary key ,
    name varchar(255) not null ,
    address varchar(255) not null ,
    x float(53) default 0.0 not null ,
    y float(53) default 0.0 not null ,
    naver_shop_id varchar(50) not null
);

CREATE TABLE `menu` (
    id varchar(40) not null primary key ,
    name varchar(255) not null ,
    price varchar(255) ,
    restaurant_id varchar(40) not null ,
    FOREIGN KEY (restaurant_id) REFERENCES `restaurant` (id)
);

CREATE TABLE `restaurant_categories` (
    restaurant_id varchar(40) not null ,
    categories varchar(255) CHECK (categories IN ('KOREAN_FOOD', 'WESTERN_FOOD', 'ASIAN_FOOD', 'FLOUR_BASED_FOOD', 'MEAT', 'CHINESE_FOOD', 'CAFE', 'DESSERT', 'CONVENIENCE_FOOD', 'JAPANESE_FOOD')),
    FOREIGN KEY (restaurant_id) REFERENCES `restaurant` (id)
);