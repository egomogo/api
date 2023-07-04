DROP TABLE IF EXISTS `menu`;
DROP TABLE IF EXISTS `restaurant_categories`;
DROP TABLE IF EXISTS `restaurant`;

CREATE TABLE `restaurant` (
    restaurant_id varchar(40) not null primary key ,
    restaurant_name varchar(255) not null ,
    address varchar(255) not null ,
    x float(53) default 0.0 not null ,
    y float(53) default 0.0 not null ,
    naver_shop_id varchar(50) not null ,
    created_at datetime not null ,
    updated_at datetime
);

CREATE TABLE `menu` (
    menu_id varchar(40) not null primary key ,
    menu_name varchar(255) not null ,
    price varchar(255) ,
    restaurant_id varchar(40) not null ,
    created_at datetime not null ,
    updated_at datetime,
    FOREIGN KEY (restaurant_id) REFERENCES `restaurant` (restaurant_id)
);

CREATE TABLE `restaurant_categories` (
    restaurant_restaurant_id varchar(40) not null ,
    categories ENUM('KOREAN_FOOD', 'WESTERN_FOOD', 'ASIAN_FOOD', 'FLOUR_BASED_FOOD', 'MEAT', 'CHINESE_FOOD', 'CAFE', 'DESSERT', 'CONVENIENCE_FOOD', 'JAPANESE_FOOD') not null ,
    FOREIGN KEY (restaurant_restaurant_id) REFERENCES `restaurant` (restaurant_id)
);
