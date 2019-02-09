drop table if exists user_info;
create table user_info (
  	user_info_id bigint not null,
    user_info_active smallint not null,
    user_info_password varchar(60) not null,
    user_info_role varchar(255),
    user_info_username varchar(255) not null,
    primary key (user_info_id, user_info_username )
);