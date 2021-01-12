CREATE DATABASE  IF NOT EXISTS `postsapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `postsapp`;

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `author` int NOT NULL,
  `parent` int NOT NULL,
  `parent_comment` int DEFAULT NULL,
  `comment_text` varchar(256) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `fk_comment_author_idx` (`author`),
  KEY `fk_comments_parent_idx` (`parent`),
  KEY `fk_comments_parent_comment_idx` (`parent_comment`),
  CONSTRAINT `fk_comment_author` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_comments_parent` FOREIGN KEY (`parent`) REFERENCES `posts` (`post_id`),
  CONSTRAINT `fk_comments_parent_comment` FOREIGN KEY (`parent_comment`) REFERENCES `comments` (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `points`;

CREATE TABLE `points` (
  `points_id` int NOT NULL AUTO_INCREMENT,
  `author` int NOT NULL,
  `comment` int NOT NULL,
  `value` int NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`points_id`),
  KEY `fk_points_author_idx` (`author`),
  KEY `fk_points_comment_idx` (`comment`),
  CONSTRAINT `fk_points_author` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_points_comment` FOREIGN KEY (`comment`) REFERENCES `comments` (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `author` int NOT NULL,
  `content` varchar(4096) NOT NULL,
  `creation_date` varchar(45) DEFAULT NULL,
  `visits` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`post_id`),
  KEY `fk_author_idx` (`author`),
  CONSTRAINT `fk_author` FOREIGN KEY (`author`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
