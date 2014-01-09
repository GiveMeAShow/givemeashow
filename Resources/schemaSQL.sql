CREATE SCHEMA givemeashow;

CREATE TABLE givemeashow.feedback_answer ( 
 );

CREATE TABLE givemeashow.feedback_kind ( 
	kind                 varchar(10)  NOT NULL  ,
	CONSTRAINT pk_feedback_kind PRIMARY KEY ( kind )
 ) engine=InnoDB;

CREATE TABLE givemeashow.lang_iso ( 
	iso                  varchar(2)  NOT NULL  ,
	CONSTRAINT pk_lang_iso PRIMARY KEY ( iso )
 ) engine=InnoDB;

CREATE TABLE givemeashow.show ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(300)  NOT NULL  ,
	icon_url             varchar(500)    ,
	CONSTRAINT pk_show PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE TABLE givemeashow.table_0 ( 
 );

CREATE TABLE givemeashow.user ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	login                varchar(32)  NOT NULL  ,
	password             varchar(32)  NOT NULL  ,
	is_admin             bool  NOT NULL DEFAULT 0 ,
	invite_code          varchar(32)  NOT NULL  ,
	time_spent           bigint UNSIGNED NOT NULL DEFAULT 0 ,
	default_lang         varchar(2)   DEFAULT 'fr' ,
	use_subtitles        bool   DEFAULT 'false' ,
	subtitle_default_lang varchar(2)  NOT NULL DEFAULT 'fr' ,
	confirmed            bool   DEFAULT 'false' ,
	email                varchar(32)  NOT NULL  ,
	CONSTRAINT pk_user PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_user ON givemeashow.user ( default_lang );

CREATE INDEX idx_user_0 ON givemeashow.user ( subtitle_default_lang );

CREATE TABLE givemeashow.feedback ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	user_id              int  NOT NULL  ,
	title                varchar(100)    ,
	kind                 varchar(10)    ,
	content              varchar(600)    ,
	read                 bool   DEFAULT 'false' ,
	CONSTRAINT pk_feedback PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_feedback ON givemeashow.feedback ( user_id );

CREATE INDEX idx_feedback_0 ON givemeashow.feedback ( kind );

CREATE TABLE givemeashow.season ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(300)  NOT NULL  ,
	position             int  NOT NULL DEFAULT 0 ,
	icon_url             varchar(500)    ,
	show_id              int  NOT NULL  ,
	CONSTRAINT pk_season PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_season ON givemeashow.season ( show_id );

CREATE TABLE givemeashow.video ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	title                varchar(150)  NOT NULL  ,
	season_id            int   DEFAULT 0 ,
	lang_iso             varchar(2)  NOT NULL DEFAULT 'fr' ,
	position             int  NOT NULL DEFAULT 0 ,
	CONSTRAINT pk_video PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_video ON givemeashow.video ( season_id );

CREATE INDEX idx_video_0 ON givemeashow.video ( lang_iso );

CREATE TABLE givemeashow.subtitle ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	video_id             int  NOT NULL  ,
	lang_iso             varchar(2)  NOT NULL  ,
	url                  varchar(300)  NOT NULL  ,
	CONSTRAINT pk_subtitle PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_subtitle ON givemeashow.subtitle ( lang_iso );

CREATE INDEX idx_subtitle_0 ON givemeashow.subtitle ( video_id );

ALTER TABLE givemeashow.feedback ADD CONSTRAINT fk_feedback_user FOREIGN KEY ( user_id ) REFERENCES givemeashow.user( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.feedback ADD CONSTRAINT fk_feedback_feedback_kind FOREIGN KEY ( kind ) REFERENCES givemeashow.feedback_kind( kind ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.season ADD CONSTRAINT fk_season_show FOREIGN KEY ( show_id ) REFERENCES givemeashow.show( id ) ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE givemeashow.subtitle ADD CONSTRAINT fk_subtitle_lang_iso FOREIGN KEY ( lang_iso ) REFERENCES givemeashow.lang_iso( iso ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.subtitle ADD CONSTRAINT fk_subtitle_video FOREIGN KEY ( video_id ) REFERENCES givemeashow.video( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.user ADD CONSTRAINT fk_user_lang_iso FOREIGN KEY ( default_lang ) REFERENCES givemeashow.lang_iso( iso ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.user ADD CONSTRAINT fk_user_default_sub_lang_iso FOREIGN KEY ( subtitle_default_lang ) REFERENCES givemeashow.lang_iso( iso ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE givemeashow.video ADD CONSTRAINT fk_video_season FOREIGN KEY ( season_id ) REFERENCES givemeashow.season( id ) ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE givemeashow.video ADD CONSTRAINT fk_video_lang_iso FOREIGN KEY ( lang_iso ) REFERENCES givemeashow.lang_iso( iso ) ON DELETE NO ACTION ON UPDATE NO ACTION;

