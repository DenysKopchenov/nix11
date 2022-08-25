create SCHEMA IF NOT EXISTS shop
    AUTHORIZATION postgres;

create TABLE IF NOT EXISTS shop.invoice
(
    id character varying(100) COLLATE pg_catalog."default" NOT NULL,
    sum double precision,
    "time" timestamp without time zone,
    CONSTRAINT invoice_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

alter table IF EXISTS shop.invoice
    OWNER to postgres;

create TABLE IF NOT EXISTS shop.ball
(
    id character varying(100) COLLATE pg_catalog."default" NOT NULL,
    count integer,
    price bigint,
    size character varying(20) COLLATE pg_catalog."default",
    title character varying(50) COLLATE pg_catalog."default",
    invoice_id character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT ball_pkey PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES shop.invoice (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete SET NULL
        NOT VALID
)

TABLESPACE pg_default;

alter table IF EXISTS shop.ball
    OWNER to postgres;


create TABLE IF NOT EXISTS shop.laptop
(
    id character varying(100) COLLATE pg_catalog."default" NOT NULL,
    title character varying(50) COLLATE pg_catalog."default",
    count integer,
    price bigint,
    invoice_id character varying(100) COLLATE pg_catalog."default",
    cpu character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT laptop_pkey PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES shop.invoice (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete SET NULL
        NOT VALID
)

TABLESPACE pg_default;

alter table IF EXISTS shop.laptop
    OWNER to postgres;


create TABLE IF NOT EXISTS shop.phone
(
    id character varying(100) COLLATE pg_catalog."default" NOT NULL,
    title character varying(50) COLLATE pg_catalog."default",
    count integer,
    price bigint,
    manufacturer character varying(50) COLLATE pg_catalog."default",
    invoice_id character varying(100) COLLATE pg_catalog."default",
    model character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT phone_pkey PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES shop.invoice (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete SET NULL
        NOT VALID
)

TABLESPACE pg_default;

alter table IF EXISTS shop.phone
    OWNER to postgres;