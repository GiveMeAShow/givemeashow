CREATE DATABASE  IF NOT EXISTS `givemeashow` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `givemeashow`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: givemeashow
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `fb_answer`
--

DROP TABLE IF EXISTS `fb_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fb_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fb_id` int(11) NOT NULL,
  `posted_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(600) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fb_answer_1_idx` (`fb_id`),
  CONSTRAINT `fk_fb_answer_1` FOREIGN KEY (`fb_id`) REFERENCES `feedback` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fb_answer`
--

LOCK TABLES `fb_answer` WRITE;
/*!40000 ALTER TABLE `fb_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `fb_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fb_kind`
--

DROP TABLE IF EXISTS `fb_kind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fb_kind` (
  `fb_kind` varchar(10) NOT NULL,
  PRIMARY KEY (`fb_kind`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fb_kind`
--

LOCK TABLES `fb_kind` WRITE;
/*!40000 ALTER TABLE `fb_kind` DISABLE KEYS */;
/*!40000 ALTER TABLE `fb_kind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(600) NOT NULL,
  `kind` varchar(10) NOT NULL,
  `hasBeenRead` tinyint(1) NOT NULL,
  `posted_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_feedback_1_idx` (`user_id`),
  KEY `fk_feedback_2_idx` (`kind`),
  CONSTRAINT `fk_feedback_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_feedback_2` FOREIGN KEY (`kind`) REFERENCES `fb_kind` (`fb_kind`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lang_iso`
--

DROP TABLE IF EXISTS `lang_iso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lang_iso` (
  `lang_iso` varchar(2) NOT NULL,
  PRIMARY KEY (`lang_iso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lang_iso`
--

LOCK TABLES `lang_iso` WRITE;
/*!40000 ALTER TABLE `lang_iso` DISABLE KEYS */;
/*!40000 ALTER TABLE `lang_iso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season`
--

DROP TABLE IF EXISTS `season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `position` int(11) NOT NULL,
  `icon_url` varchar(150) NOT NULL,
  `show_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_season_1_idx` (`show_id`),
  CONSTRAINT `fk_season_1` FOREIGN KEY (`show_id`) REFERENCES `show` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season`
--

LOCK TABLES `season` WRITE;
/*!40000 ALTER TABLE `season` DISABLE KEYS */;
INSERT INTO `season` VALUES (1,'season1',0,'azea',5),(2,'azeaeazeazeazeaeze',1,'',5),(3,'qqqqqqqqqqqqqqqq',3,'',5);
/*!40000 ALTER TABLE `season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `show`
--

DROP TABLE IF EXISTS `show`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `icon_url` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `show`
--

LOCK TABLES `show` WRITE;
/*!40000 ALTER TABLE `show` DISABLE KEYS */;
INSERT INTO `show` VALUES (5,'BABAR','aezae'),(6,'BABAR2','azeaz'),(7,'azeazeazeazeaz',''),(8,'aaaaaaaaaa','');
/*!40000 ALTER TABLE `show` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subtitle`
--

DROP TABLE IF EXISTS `subtitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subtitle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` int(11) NOT NULL,
  `lang_iso` varchar(2) NOT NULL,
  `url` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subtitle_1_idx` (`lang_iso`),
  KEY `fk_subtitle_2_idx` (`video_id`),
  CONSTRAINT `fk_subtitle_1` FOREIGN KEY (`lang_iso`) REFERENCES `lang_iso` (`lang_iso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtitle_2` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subtitle`
--

LOCK TABLES `subtitle` WRITE;
/*!40000 ALTER TABLE `subtitle` DISABLE KEYS */;
/*!40000 ALTER TABLE `subtitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `invite_code` varchar(32) NOT NULL,
  `password` varchar(45) NOT NULL,
  `time_spent` bigint(20) NOT NULL DEFAULT '0',
  `default_lang` varchar(2) NOT NULL,
  `use_subtitles` tinyint(1) NOT NULL DEFAULT '0',
  `sub_default_lang` varchar(2) NOT NULL DEFAULT 'fr',
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dontcare_idx` (`default_lang`),
  KEY `keytwo_idx` (`sub_default_lang`),
  CONSTRAINT `dontcare` FOREIGN KEY (`default_lang`) REFERENCES `lang_iso` (`lang_iso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `keytwo` FOREIGN KEY (`sub_default_lang`) REFERENCES `lang_iso` (`lang_iso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `season_id` int(11) NOT NULL,
  `lang_iso` varchar(2) NOT NULL,
  `position` int(11) NOT NULL,
  `is_transition` tinyint(1) NOT NULL,
  `relative_path` varchar(500) NOT NULL,
  `viewed` mediumtext NOT NULL,
  `url` varchar(250) NOT NULL,
  `show_id` int(11) NOT NULL,
  `end_intro_time` int(11) DEFAULT NULL,
  `start_outro_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `relative_path_UNIQUE` (`relative_path`),
  KEY `fk_video_1_idx` (`lang_iso`),
  KEY `fk_video_2_idx` (`season_id`),
  KEY `fk_video_show1_idx` (`show_id`),
  CONSTRAINT `fk_video_1` FOREIGN KEY (`lang_iso`) REFERENCES `lang_iso` (`lang_iso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_video_2` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_video_show1` FOREIGN KEY (`show_id`) REFERENCES `show` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-15  1:24:11
