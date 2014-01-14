SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`lang_iso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`lang_iso` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`lang_iso` (
  `lang_iso` VARCHAR(2) NOT NULL ,
  PRIMARY KEY (`lang_iso`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `login` VARCHAR(32) NOT NULL ,
  `password` VARCHAR(32) NOT NULL ,
  `is_admin` TINYINT(1) NOT NULL DEFAULT false ,
  `invite_code` VARCHAR(32) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `time_spent` BIGINT NOT NULL DEFAULT 0 ,
  `default_lang` VARCHAR(2) NOT NULL ,
  `use_subtitles` TINYINT(1) NOT NULL DEFAULT false ,
  `sub_default_lang` VARCHAR(2) NOT NULL DEFAULT 'fr' ,
  `confirmed` TINYINT(1) NOT NULL DEFAULT false ,
  `email` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `dontcare` (`default_lang` ASC) ,
  INDEX `keytwo` (`sub_default_lang` ASC) ,
  CONSTRAINT `dontcare`
    FOREIGN KEY (`default_lang` )
    REFERENCES `mydb`.`lang_iso` (`lang_iso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `keytwo`
    FOREIGN KEY (`sub_default_lang` )
    REFERENCES `mydb`.`lang_iso` (`lang_iso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`fb_kind`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`fb_kind` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`fb_kind` (
  `fb_kind` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`fb_kind`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`feedback` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`feedback` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user_id` INT NOT NULL ,
  `title` VARCHAR(100) NOT NULL ,
  `content` VARCHAR(600) NOT NULL ,
  `kind` VARCHAR(10) NOT NULL ,
  `hasBeenRead` TINYINT(1) NOT NULL ,
  `posted_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_feedback_1` (`user_id` ASC) ,
  INDEX `fk_feedback_2` (`kind` ASC) ,
  CONSTRAINT `fk_feedback_1`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feedback_2`
    FOREIGN KEY (`kind` )
    REFERENCES `mydb`.`fb_kind` (`fb_kind` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`fb_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`fb_answer` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`fb_answer` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fb_id` INT NOT NULL ,
  `posted_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `content` VARCHAR(600) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_fb_answer_1` (`fb_id` ASC) ,
  CONSTRAINT `fk_fb_answer_1`
    FOREIGN KEY (`fb_id` )
    REFERENCES `mydb`.`feedback` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`show`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`show` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`show` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(150) NOT NULL ,
  `icon_url` VARCHAR(150) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`season`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`season` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`season` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(200) NOT NULL ,
  `position` INT NOT NULL ,
  `icon_url` VARCHAR(150) NOT NULL ,
  `show_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_season_1` (`show_id` ASC) ,
  CONSTRAINT `fk_season_1`
    FOREIGN KEY (`show_id` )
    REFERENCES `mydb`.`show` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`video`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`video` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`video` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(150) NOT NULL ,
  `season_id` INT NOT NULL ,
  `lang_iso` VARCHAR(2) NOT NULL ,
  `position` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_video_1` (`lang_iso` ASC) ,
  INDEX `fk_video_2` (`season_id` ASC) ,
  CONSTRAINT `fk_video_1`
    FOREIGN KEY (`lang_iso` )
    REFERENCES `mydb`.`lang_iso` (`lang_iso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_video_2`
    FOREIGN KEY (`season_id` )
    REFERENCES `mydb`.`season` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subtitle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`subtitle` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`subtitle` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `video_id` INT NOT NULL ,
  `lang_iso` VARCHAR(2) NOT NULL ,
  `url` VARCHAR(150) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_subtitle_1` (`lang_iso` ASC) ,
  INDEX `fk_subtitle_2` (`video_id` ASC) ,
  CONSTRAINT `fk_subtitle_1`
    FOREIGN KEY (`lang_iso` )
    REFERENCES `mydb`.`lang_iso` (`lang_iso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtitle_2`
    FOREIGN KEY (`video_id` )
    REFERENCES `mydb`.`video` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
