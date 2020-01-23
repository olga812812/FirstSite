create table if not exists Components(
id identity,
name varchar(25) not null,
type varchar(25) not null
);

create table if not exists Orders(
id identity,
name varchar(25) not null,
order_table varchar(25) not null,
payment_type varchar(25) not null,
created_at timestamp not null
);

create table if not exists Order_Components(
order_id bigint not null,
component_id bigint not null
);

alter table Order_Components
 add foreign key (order_id) references Orders(id);
alter table Order_Components
 add foreign key (component_id) references Components(id);

