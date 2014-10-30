INSERT INTO `givemeashow`.`lang_iso` (`lang_iso`, `lang_name`, `lang_flag_url`) VALUES ('fr', 'French', 'lol');

INSERT INTO `givemeashow`.`user_roles`
(`user_role`)
VALUES
('ROLE_ADMIN');

INSERT INTO `givemeashow`.`user_roles`
(`user_role`)
VALUES
('ROLE_VISITOR');

INSERT INTO `givemeashow`.`user_roles`
(`user_role`)
VALUES
('ROLE_USER');


INSERT INTO `givemeashow`.`user`
(`id`,
`login`,
`is_admin`,
`invite_code`,
`password`,
`time_spent`,
`default_lang`,
`use_subtitles`,
`sub_default_lang`,
`confirmed`,
`email`,
`user_role`)
VALUES
(0,
'ogdabou',
true,
'inviinvi',
'password_01',
0,
'fr',
false,
'fr',
true,
'ogdabou@gmail.com',
'ROLE_ADMIN');

INSERT INTO `givemeashow`.`user`
(`id`,
`login`,
`is_admin`,
`invite_code`,
`password`,
`time_spent`,
`default_lang`,
`use_subtitles`,
`sub_default_lang`,
`confirmed`,
`email`,
`user_role`)
VALUES
(2,
'visitor',
false,
'inviinvi',
'i_like_trains_0720',
0,
'fr',
false,
'fr',
true,
'dsqd',
'ROLE_VISITOR');

INSERT INTO `givemeashow`.`user`
(`id`,
`login`,
`is_admin`,
`invite_code`,
`password`,
`time_spent`,
`default_lang`,
`use_subtitles`,
`sub_default_lang`,
`confirmed`,
`email`,
`user_role`)
VALUES
(3,
'user',
false,
'inviinvi',
'i_like_trains_0720',
0,
'fr',
false,
'fr',
true,
'dsqd',
'ROLE_USER');


