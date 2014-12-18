INSERT INTO `givemeashow`.`lang_iso` (`lang_iso`, `lang_name`, `lang_flag_url`) VALUES ('fr', 'French', 'lol');

INSERT INTO `givemeashow`.`user_roles`
(`user_role`)
VALUES
('ROLE_ADMIN');

INSERT INTO `givemeashow`.`user_roles`
(`user_role`)
VALUES
('ROLE_USER');


INSERT INTO `givemeashow`.`user`
(`id`,
`login`,
`is_admin`,
`password`,
`time_spent`,
`default_lang`,
`use_subtitles`,
`sub_default_lang`,
`confirmed`,
`email`,
`invited`,
`user_role`)
VALUES
(0,
'ogdabou',
true,
'$2a$10$9F0aZL/z6hL1wZX.NO5vWe/Zlq8zu.9QdY0.eUFbisXNfGOg43NdW',
0,
'fr',
false,
'fr',
true,
'ogdabou@gmail.com',
0,
'ROLE_ADMIN');
