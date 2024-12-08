create table log
(
    id            bigint not null auto_increment,
    expense       bigint not null,
    member_id     bigint not null,
    room_id       bigint not null,
    memo          varchar(255),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table member
(
    id            bigint not null auto_increment,
    name          varchar(16),
    expense       bigint not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table room
(
    id            bigint     not null auto_increment,
    member1_id    bigint     not null,
    member2_id    bigint,
    code          varchar(8) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create index log_created_date_idx on log (created_date);

create index log_modified_date_idx on log (modified_date);

alter table if exists room
    add constraint UKarevbfcloncxciyi0vbx1m4he unique (code);

alter table if exists room
    add constraint UKlanf6uofbre66l90r7v0k9lnp unique (member1_id);

alter table if exists room
    add constraint UKm177vw66ammbkdwbjadchyikk unique (member2_id);

alter table if exists log
    add constraint FK51ek4bis8a60qtmopf329fiev
        foreign key (member_id)
            references member (id);

alter table if exists log
    add constraint FKa1guu6yj072mudb66ytc7voee
        foreign key (room_id)
            references room (id);

alter table if exists room
    add constraint FKtc3ijuiwm8xemup8jubbupxcl
        foreign key (member1_id)
            references member (id);

alter table if exists room
    add constraint FKbijvnohee2ey7eyo42l1ccque
        foreign key (member2_id)
            references member (id);
