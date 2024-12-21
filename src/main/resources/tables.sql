create table member
(
    id          bigint      not null auto_increment primary key,
    name        varchar(16) not null,
    expense     bigint      not null,
    created_at  datetime(3) not null,
    modified_at datetime(3) not null
) engine = InnoDB;

create table room
(
    id          bigint      not null auto_increment primary key,
    member1_id  bigint      not null,
    member2_id  bigint,
    code        varchar(8)  not null,
    created_at  datetime(3) not null,
    modified_at datetime(3) not null,

    constraint UKarevbfcloncxciyi0vbx1m4he
        unique (code),

    constraint UKlanf6uofbre66l90r7v0k9lnp
        unique (member1_id),

    constraint UKm177vw66ammbkdwbjadchyikk
        unique (member2_id),

    constraint FKtc3ijuiwm8xemup8jubbupxcl
        foreign key (member1_id) references member (id),

    constraint FKbijvnohee2ey7eyo42l1ccque
        foreign key (member2_id) references member (id)

) engine = InnoDB;

create table log
(
    id          bigint      not null auto_increment primary key,
    expense     bigint      not null,
    member_id   bigint      not null,
    room_id     bigint      not null,
    memo        varchar(255),
    created_at  datetime(3) not null,
    modified_at datetime(3) not null,

    constraint FK51ek4bis8a60qtmopf329fiev
        foreign key (member_id) references member (id),

    constraint FKa1guu6yj072mudb66ytc7voee
        foreign key (room_id) references room (id)

) engine = InnoDB;

create index log_created_at_idx on log (created_at);
