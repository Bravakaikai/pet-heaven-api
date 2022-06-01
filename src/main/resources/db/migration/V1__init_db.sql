-- Create Sequence
CREATE SEQUENCE kelly.user_id_seq;
CREATE SEQUENCE kelly.equipment_id_seq;

--Create User Table
CREATE TABLE IF NOT EXISTS kelly."user"
(
    id bigint NOT NULL DEFAULT nextval('kelly.user_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    role character varying COLLATE pg_catalog."default" NOT NULL,
    wallet integer NOT NULL,
    pet character varying COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp with time zone NOT NULL DEFAULT now(),
    updated_date timestamp with time zone NOT NULL DEFAULT now(),
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_email_key UNIQUE (email),
    CONSTRAINT user_name_key UNIQUE (name)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS kelly."user"
OWNER to postgres;

-- Create Equipment Table
CREATE TABLE IF NOT EXISTS kelly.equipment
(
    id bigint NOT NULL DEFAULT nextval('kelly.equipment_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    img_url character varying COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp with time zone NOT NULL DEFAULT now(),
    updated_date timestamp with time zone NOT NULL DEFAULT now(),
    CONSTRAINT equipment_pkey PRIMARY KEY (id),
    CONSTRAINT equipment_name_key UNIQUE (name)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS kelly.equipment
OWNER to postgres;

-- Create User Equipment Table
CREATE TABLE IF NOT EXISTS kelly.user_equipment
(
    user_id bigint NOT NULL,
    equipment_id bigint NOT NULL,
    amount integer NOT NULL,
    created_date timestamp with time zone NOT NULL DEFAULT now(),
    updated_date timestamp with time zone NOT NULL DEFAULT now(),
    CONSTRAINT user_equipment_pkey PRIMARY KEY (user_id, equipment_id),
    CONSTRAINT user_equipment_equipment_id_fkey FOREIGN KEY (equipment_id)
        REFERENCES kelly.equipment (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT user_equipment_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES kelly."user" (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS kelly.user_equipment
OWNER to postgres;

-- Create Shop View
CREATE OR REPLACE VIEW kelly.shop
AS
SELECT eq.id,
   eq.name,
   eq.description,
   eq.price,
   eq.img_url
FROM kelly.equipment eq
ORDER BY eq.id;

-- Insert Admin User Data
INSERT INTO kelly."user"(name, email, password, role, wallet, pet)
SELECT 'kelly', 'kelly@gmail.com', '123456', 'Admin', 1000, 'Dog'
FROM (select 1) tmp WHERE NOT EXISTS (SELECT 1 FROM kelly."user" WHERE name = 'kelly' AND role = 'Admin');