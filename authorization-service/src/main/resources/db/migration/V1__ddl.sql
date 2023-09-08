CREATE TABLE roles (
                       id bigserial NOT NULL PRIMARY KEY,
                       title varchar NOT NULL
);


CREATE TABLE users (
                       id bigserial NOT NULL PRIMARY KEY,
                       username varchar NOT NULL,
                       password varchar NOT NULL,
                       first_name varchar NOT NULL,
                       last_name varchar NOT NULL,
                       email varchar NOT NULL,
                       phone varchar NULL,
                       address varchar NULL,
                       role_id int8 NOT NULL,
                       created_by varchar NULL,
                       created_when timestamp NULL,
                       update_by varchar NULL,
                       update_when timestamp NULL,
                       is_deleted bool NULL DEFAULT false,
                       deleted_by varchar NULL,
                       deleted_when timestamp NULL,
                       CONSTRAINT users_roles_fk FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE attempts (
                          user_id bigserial NOT NULL PRIMARY KEY,
                          created_when timestamp NULL,
                          is_verified bool NULL DEFAULT false,
                          verification_code varchar NOT NULL,
                          CONSTRAINT attempts_users_fk FOREIGN KEY (user_id) REFERENCES users(id)
);



INSERT INTO roles (title) VALUES
                              ('ROLE_ADMIN'),
                              ('ROLE_USER'),
                              ('ROLE_MANAGER');

INSERT INTO users (username, password, first_name,
                   last_name, email, phone, address, role_id,
                   created_by, created_when, update_by, update_when,
                   is_deleted, deleted_by, deleted_when) VALUES
             ('admin', '$2a$10$2/ADJRmO6PwcURKpH0in7.9wmayZdrktHb/NvyJEQv4Nmy9QoIyr6',
              'Default', 'Admin', 'admin@mail.ru', NULL, NULL, 1,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('customer', '$2a$10$QuakOKczPKpUZQm63AK7cuxXEn4pMDsYL4BgG0UaTckFb0duLVsqq',
              'Default', 'Customer', 'customer@mail.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('manager', '$2a$10$oVCrirqnU63KMRhF4EFcEutoQ7CfmhR68pDLi62qok1TJ1q525bOC',
              'Default', 'Manager', 'manager@mail.ru', NULL, NULL, 3,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('daniil', '$2a$10$5dF69g0cZmm6jkcLIh6VuubzKHu862YcRrIezecPCrZoTkJytePnu',
              'Даниил', 'Сысоев', 'daniil@mail.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('kristina', '$2a$10$o2DX5usC1GMUAfnnpNHxOufBNaviYpXM.0iIu/sRepjo2uCmfwcy6',
              'Кристина', 'Гурьева', 'kristina@yandex.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('bogdan', '$2a$10$cMwlQOfdXE8cZu6B1ulFq.z9xyWfeaiDbOX4CSossZUlFeagbzyma',
              'Богдан', 'Королёв', 'bogdan@gmail.com', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('elena', '$2a$10$OXlbG8BHWzjzEX3woVLeWuLtrcZ9PcexA1CMo8245KyE4KrRnzddi',
              'Елена', 'Дьячкова', 'elena@mail.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('igorpanov', '$2a$10$Mpwwrp0e50Hv1ruKTmhK2.06nmejdaAV22vA49TY6PpG.FNTtGc96',
              'Игорь', 'Панов', 'igor@yandex.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL);