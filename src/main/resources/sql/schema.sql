drop database if exists frozen_project;
create database frozen_project;
use frozen_project;
create table user(
                     u_id varchar(10) primary key ,
                     u_name varchar(15) ,
                     u_address varchar(15) ,
                     u_position varchar(15) ,
                     u_password varchar(10) ,
                     u_gmail varchar(20)
);
show tables ;
create table payment(
                        payment_id varchar(10) primary key ,
                        amont double(8,2),
    date date,
    user_id varchar(10),
    foreign key (user_id) references user(u_id) on delete cascade on update cascade
);
create table customer(
                         cus_id varchar(10) primary key ,
                         cus_name varchar(15) ,
                         cus_address varchar(15) ,
                         cus_gmail varchar(20),
                         contact_num int(10)
);
create table driver(
                       driver_id varchar(10) primary key ,
                       driver_name varchar(15) ,
                       contact_num int(10) ,
                       driver_address varchar(20)
);
create table vehicle(
                        v_id varchar(10) primary key ,
                        model varchar(15) ,
                        driver_id varchar(10),
                        foreign key (driver_id) references driver(driver_id) on delete cascade on update cascade
);
create table delivery(
                         deli_id varchar(10) primary key ,
                         deli_address varchar(20) ,
                         driver_id varchar(10),
                         foreign key (driver_id) references driver(driver_id) on delete cascade on update cascade
);
create table orders(
                       o_id varchar(10) primary key ,
                       date date,
                       payment_id varchar(10),
                       deli_id varchar(10),
                       cus_id varchar(10),
                       foreign key (payment_id) references payment(payment_id) on delete cascade on update cascade,
                       foreign key (deli_id) references delivery(deli_id) on delete cascade on update cascade,
                       foreign key (cus_id) references customer(cus_id) on delete cascade on update cascade
);
create table products(
                         p_id varchar (10) primary key ,
                         p_name varchar(15),
                         price double(5,2)
    );
create table order_product_details(
                                      p_id varchar (10),
                                      o_id varchar(10),
                                      foreign key (p_id) references products(p_id) on delete cascade on update cascade,
                                      foreign key (o_id) references orders(o_id) on delete cascade on update cascade
);
create table inventory(
                          i_id varchar(10) primary key ,
                          i_name varchar(15),
                          qty double(5,2)
    );
create table products_inventory_details(
                                           p_id varchar (10),
                                           i_id varchar(10),
                                           foreign key (p_id) references products(p_id) on delete cascade on update cascade,
                                           foreign key (i_id) references inventory(i_id) on delete cascade on update cascade

);
create table supplier(
                         sup_id varchar(10) primary key ,
                         name varchar(15),
                         address varchar(20),
                         contact_num int(10),
                         gmail varchar(20)
);
create table inventory_supplier_details(
                                           i_id varchar(10),
                                           sup_id varchar(10),
                                           foreign key (i_id) references inventory(i_id) on delete cascade on update cascade,
                                           foreign key (sup_id) references supplier(sup_id) on delete cascade on update cascade

);
show tables ;
