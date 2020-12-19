-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: postsapp
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `postsapp`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `postsapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `postsapp`;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_id` int NOT NULL,
  `author` int NOT NULL,
  `parent` int NOT NULL,
  `points` int NOT NULL,
  `parent_comment` int DEFAULT NULL,
  `comment_text` varchar(256) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `fk_comment_idx` (`parent_comment`),
  CONSTRAINT `fk_comment` FOREIGN KEY (`parent_comment`) REFERENCES `comments` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `author` int NOT NULL,
  `content` varchar(4096) NOT NULL,
  `creation_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `fk_author_idx` (`author`),
  CONSTRAINT `fk_author` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (4,'rewr',5,'ffew','2020-12-18 12:28:19'),(64,'wrf',10,'123f','2020-12-18 12:32:00'),(65,'my title',10,'my content','2020-12-18 12:33:41'),(66,'testtest',10,'testcontent','2020-12-18 16:21:14'),(67,'testx',10,'testy','2020-12-18 16:26:05'),(68,'testh',10,'testn','2020-12-18 16:28:09'),(73,'get',10,'getsa','2020-12-18 17:45:51'),(74,'ąąąą',10,'ęęęę','2020-12-18 18:00:05');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'123@wp.ed','$2a$10$8m7v/9z47IPatCwbxvT8FOLJ7d0cEUmMqlDCgNkmtdIJju/PB8BW.','test'),(6,'123@wp.23e','2d2d3','testd'),(7,'123@wp.ed4','UJrfEvjThc3BiWF','34t34rt'),(8,'testuser3@wp.qx','$2a$10$/WxKKx2w4QpXV5IT04Usr.FvcPy0hiZJYnIFR45LMh2bRNgXAPccK','testuser3'),(9,'dqwd23d@f32.23d','$2a$10$xqlq6vkFsSH1lBPIcCDMreovJW29h6FMn.thQuztdPotflM9Q/66.','23d23d'),(10,'qwe@ert.ty','$2a$10$RjHlB3D5BGJJwfVCLEzK2uw/RbnAbQkuTc5ZuTI7QKO8i7wEIufim','ppp'),(11,'123@456.7','$2a$10$//35kBKrWcnCUJH4iLylpOEmJEiU9bElUr/hMV56WoORsTnqwyswy','hey'),(69,'qqq@qqq.sss','$2a$10$tsJwOTlcyuqCU3HfMy.GUOxgBsnAIIJx6woQsc9npXBT6qdpGBhdW','qqq'),(70,'www@www.www','$2a$10$GhGHjV8zP98icm8zCGFNfuv823qAe0vvDb8thcY6StjMmRCYFYkTq','www'),(75,'23e23f2d3','$2a$10$tw0qGoNHuGOZA0yeLwJlZupRct1DM6KeInn5t5M4dlcePBK.K3ZH.','2e323e'),(76,'','$2a$10$ypyPj8QxCLrnU5YEyJ1J5OGo9d4rZGVqHJVGRGyOAigRMeI53qUMW','');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-19 13:34:52
