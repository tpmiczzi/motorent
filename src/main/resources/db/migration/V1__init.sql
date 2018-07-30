create table moto
(
  id     bigint       not null
    constraint moto_pkey
    primary key,
  name   varchar(255) not null,
  price  double precision,
  volume integer      not null
);

CREATE SEQUENCE moto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table roles
(
  id   bigserial not null
    constraint roles_pkey
    primary key,
  name varchar(60)
    constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
    unique
);

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table users
(
  id       bigserial not null
    constraint users_pkey
    primary key,
  email    varchar(40)
    constraint uk6dotkott2kjsp8vw4d0m25fb7
    unique,
  name     varchar(40),
  password varchar(100),
  username varchar(15)
    constraint ukr43af9ap4edm43mmtq01oddj6
    unique
);

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table user_roles
(
  user_id bigint not null
    constraint fkhfh9dx7w3ubf1co1vdev94g3f
    references users,
  role_id bigint not null
    constraint fkh8ciramu9cc9q3qcqiv4ue8a6
    references roles,
  constraint user_roles_pkey
  primary key (user_id, role_id)
);