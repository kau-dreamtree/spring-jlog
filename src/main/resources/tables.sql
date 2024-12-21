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
    member1_id  bigint      not null unique,
    member2_id  bigint unique,
    code        varchar(8)  not null unique,
    created_at  datetime(3) not null,
    modified_at datetime(3) not null,
    foreign key (member1_id) references member (id),
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
    foreign key (member_id) references member (id),
    foreign key (room_id) references room (id)
) engine = InnoDB;

create index log_created_at_idx on log (created_at);
