drop table t_wizards if exists;
drop table t_items if exists;
drop table t_orders if exists;
create table t_wizards (
    wizard_name varchar(255) not null, 
    wizard_dexterity integer, 
    wizard_person varchar(255), 
    primary key (wizard_name));
create table t_items (
    item_id bigint generated by default as identity, 
    item_name varchar(255) not null, 
    item_quality integer, 
    item_type varchar(255),
    primary key (item_id),
    CONSTRAINT item_order_fk UNIQUE (item_id));
create table t_orders (
    ord_id bigint generated by default as identity, 
    ord_wizard varchar(255), 
    ord_item BIGINT(20), 
    primary key (ord_id));
alter table t_orders 
    add constraint ord_wizard_fk
    foreign key (ord_wizard) references t_wizards (wizard_name)
        ON DELETE SET NULL;
alter table t_orders
    add constraint ord_item_fk 
    foreign key (ord_item) references t_items (item_id)
        ON DELETE SET NULL;
insert into t_wizards 
    (wizard_name, wizard_dexterity, wizard_person) 
    values 
        ('Marius Black', 15, 'SQUIB'),
        ('Hermione', 100, 'MUDBLOOD');
insert into t_items 
    (item_id, item_name, item_quality, item_type) 
    values 
        (1L, '+5 Dexterity Vest', 20, 'MagicalItem'),
        (2L, 'Elixir of the Mongoose', 7, 'MagicalItem'),
        (3L, 'Aged Brie', 10, 'MagicalItem'),
        (4L, 'Aged Brie', 0, 'MagicalItem'),
        (5L, '+5 Dexterity Vest', 40, 'MagicalItem'),
        (6L, '+5 Dexterity Vest', 60, 'MagicalItem');
insert into t_orders 
    (ord_id, ord_wizard, ord_item) 
    values 
        (1L, 'Marius Black', 2L),
        (2L, 'Marius Black', 3L),
        (3L, 'Hermione', 5L);