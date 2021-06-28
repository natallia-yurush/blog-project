DELETE FROM `user_role`;
DELETE FROM `user`;

INSERT INTO `user_role` (name) VALUES ('ROLE_USER');
INSERT INTO `user_role` (name) VALUES ('ADMIN_USER');

INSERT INTO `user` (`active`, `created_at`, `email`, `first_name`, `last_name`, `password`)
VALUES ('1', '2021-06-23', 'nika.yurush@gmail.com', 'Veronika', 'Yurush', '$2a$10$2qObGV8F72Zb/qjQXGPMPOA8jPwZ24vswpOB0jFxN/k/oFQAUSJKm');
