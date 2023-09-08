CREATE TABLE orders (
                           id bigserial NOT NULL PRIMARY KEY,
                           user_id int8 NOT NULL,
                           film_id int8 NOT NULL,
                           sent_price int4 NOT NULL,
                           rent_price int4 NOT NULL,
                           type varchar NOT NULL,
                           created_by varchar NULL,
                           created_when timestamp NULL,
                           update_by varchar NULL,
                           update_when timestamp NULL,
                           is_deleted bool NULL DEFAULT false,
                           deleted_by varchar NULL,
                           deleted_when timestamp NULL,
                           rent_start timestamp NULL,
                           rent_end timestamp NULL
);




