insert into Components (id, name, type) values (1,'Apple', 2) ON DUPLICATE KEY UPDATE name=name;
insert into Components (id, name, type) values (2,'Orange', 2) ON DUPLICATE KEY UPDATE name=name;
insert into Components (id, name, type) values (3,'Latte',0) ON DUPLICATE KEY UPDATE name=name;
insert into Components (id, name, type) values (4,'Tea', 0) ON DUPLICATE KEY UPDATE name=name;
insert into Components (id, name, type) values (5,'Chips', 1) ON DUPLICATE KEY  UPDATE name=name;
insert into Components (id, name, type) values (6,'KitKat', 1) ON DUPLICATE KEY UPDATE name=name;

