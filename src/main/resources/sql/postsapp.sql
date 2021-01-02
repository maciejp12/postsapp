CREATE DATABASE  IF NOT EXISTS `postsapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `postsapp`;
-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: postsapp
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (26,10,150,NULL,'test','2020-12-29 13:07:23'),(27,10,150,NULL,'hey test','2020-12-29 13:24:33'),(28,69,150,NULL,'this is a test comment','2020-12-29 13:25:42'),(29,10,147,NULL,'34','2020-12-30 12:13:33'),(30,10,150,NULL,'34r','2020-12-30 12:13:47'),(31,10,150,NULL,'123','2020-12-30 12:32:12'),(32,10,150,NULL,'23e','2020-12-30 12:33:12'),(33,10,150,NULL,'efefef','2020-12-30 12:33:15'),(34,10,150,NULL,'w','2020-12-30 12:33:21'),(35,10,150,NULL,'ahaaahaaaha','2020-12-30 12:33:30'),(36,10,150,NULL,'34f','2020-12-30 12:34:34'),(37,10,150,NULL,'34r','2020-12-30 12:43:31'),(38,10,150,NULL,'2r4','2020-12-30 17:24:04'),(39,10,150,NULL,'34r43r34r','2020-12-30 17:24:07'),(40,10,146,NULL,'comment','2020-12-30 17:39:23'),(41,10,146,NULL,'comment2','2020-12-30 17:39:27'),(42,10,150,NULL,'45t','2020-12-30 17:57:21'),(43,10,145,NULL,'r','2020-12-30 18:12:45'),(44,10,145,NULL,'2323e','2020-12-30 18:12:50'),(45,10,81,NULL,'nceq','2020-12-30 18:13:32'),(46,10,81,NULL,'2','2020-12-30 18:13:38'),(47,10,81,NULL,'2','2020-12-30 18:13:41'),(48,10,81,NULL,'3','2020-12-30 18:13:42'),(49,10,81,NULL,'4','2020-12-30 18:13:43'),(50,10,150,NULL,'weffew','2020-12-30 18:27:15'),(51,10,150,NULL,'tette','2020-12-30 18:27:21'),(52,10,150,NULL,'pcmqawe','2020-12-30 18:28:03'),(53,10,150,52,'sa3','2020-12-30 18:29:47');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `points`
--

DROP TABLE IF EXISTS `points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `points`
--

LOCK TABLES `points` WRITE;
/*!40000 ALTER TABLE `points` DISABLE KEYS */;
INSERT INTO `points` VALUES (1,5,27,1,'2021-01-02 18:47:16'),(2,5,27,1,'2021-01-02 18:48:18'),(3,5,27,1,'2021-01-02 18:48:18'),(4,5,27,1,'2021-01-02 18:48:19'),(5,5,26,-1,'2021-01-02 18:50:02'),(6,5,26,-1,'2021-01-02 18:50:03');
/*!40000 ALTER TABLE `points` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (81,'wadawd',10,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','2020-12-19 16:12:14'),(92,'dqwddsf',10,'dwdwdewdewdewdsdfdsf','2020-12-19 16:28:07'),(139,'rg34gf',10,'3434f34f34f34f34f','2020-12-19 17:34:08'),(142,'::>?<:K:(&*U',10,'sdafdsAASASąąśćðąćźżćżźćźż„ęœæ²³€€¢←€½ə→≠²€≤≥','2020-12-20 11:38:05'),(143,'mewe23',10,'merfesdfdsfdsfcvcv','2020-12-22 13:01:09'),(145,'test12345',10,'\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ac tincidunt odio, eu vestibulum quam. Nam metus nibh, tincidunt ut sapien vel, mollis vestibulum sapien. Cras sit amet pretium magna. Fusce gravida nunc ultricies felis blandit ornare. In venenatis quam nisl. Nullam ultrices, magna sit amet mattis vulputate, felis tellus porttitor arcu, iaculis rutrum ligula orci sit amet lorem. Nulla bibendum odio libero, sit amet bibendum est lobortis sed. In hac habitasse platea dictumst. Aenean vitae placerat ipsum. Ut varius eros purus, eget condimentum mi finibus eu. Suspendisse dapibus tellus vitae tempus bibendum.\n\nNullam in nibh lectus. Aenean eget magna felis. Nam vestibulum dui ut turpis dapibus, in consequat leo porttitor. Sed ultrices molestie purus, et vulputate magna dignissim vel. Sed dictum enim et bibendum efficitur. Fusce id aliquam tortor, et ultricies tellus. Proin cursus dui et nisi scelerisque sagittis. Curabitur sed erat sed turpis viverra malesuada et ut elit. Morbi a consequat tortor, ut elementum ex. Praesent mi sem, dignissim et libero et, ultricies venenatis nunc. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin semper diam nec enim lacinia mattis. Donec sit amet mauris est. Suspendisse potenti. Nunc id imperdiet nibh.\n\nVestibulum porta felis ut ex sagittis porta. Aenean facilisis purus at urna dictum dapibus. Maecenas tristique at nibh eu viverra. Fusce a nisi tincidunt, suscipit justo at, pellentesque mauris. Nam leo odio, sagittis nec dolor et, tincidunt congue nunc. Suspendisse a nisl nulla. Pellentesque accumsan et ante non commodo. Morbi posuere dictum quam, et lacinia metus facilisis et. Nullam tellus eros, accumsan non est eu, dapibus vulputate lacus. Sed nec risus at erat rhoncus facilisis. Nullam posuere nisi nibh, vitae sagittis dolor lacinia vitae.\n\nFusce et venenatis est. Vestibulum quis erat vel tortor posuere congue. Ut et luctus eros. Mauris in ex libero. In purus lorem, luctus nec erat mollis, scelerisque commodo augue. Phasellus lacinia sem lorem, sed finibus dolor egestas sed. Proin sodales consequat fringilla. Proin lacinia porta leo, non finibus justo scelerisque ut. Ut suscipit non mauris in lacinia. Aenean ullamcorper ipsum vitae turpis pharetra dignissim. Nunc quis enim nec justo elementum facilisis.\n\nInteger nec sapien eu magna ultrices ornare id quis urna. Aenean aliquam mauris vel rutrum tempor. Vestibulum luctus accumsan cursus. Phasellus vitae feugiat ante. In facilisis et lectus in convallis. Ut nec pellentesque ex, vitae tincidunt dolor. Mauris nec tellus sem. Vestibulum varius, elit id eleifend porttitor, turpis velit molestie est, volutpat suscipit ante arcu et tortor. Mauris rutrum condimentum nisi eget gravida. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent vel erat in ante rhoncus faucibus non in neque. Maecenas egestas massa quis felis tempor auctor ut molestie dui. Aliquam erat volutpat. Aenean gravida nec diam sed posuere. ','2020-12-23 12:41:23'),(146,'test332',10,'\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vel faucibus ex. Proin nec dictum lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Proin sed accumsan lacus. Maecenas sit amet rhoncus mi, et mollis risus. Nam feugiat lorem sit amet nunc malesuada venenatis. Integer venenatis imperdiet rhoncus. Vivamus metus risus, suscipit euismod dui sit amet, ultricies fermentum nunc. Maecenas eu finibus nisi, vel fringilla sapien. Fusce magna lorem, congue a consectetur id, pretium vulputate felis. Mauris eu orci pulvinar libero lacinia efficitur. Mauris quis diam eros. Aliquam sed posuere turpis, imperdiet euismod ex. Integer at faucibus turpis. Praesent rutrum ex nec mauris vestibulum euismod sit amet at eros.\n\nNulla nec rhoncus justo. Nam eget aliquet arcu. Nulla accumsan eget nisi ut lacinia. In quis erat leo. Integer ut euismod lorem, nec laoreet purus. Praesent id varius nisl. Sed urna ligula, vehicula vitae magna aliquet, porta cursus felis. Aenean vitae malesuada risus, at vestibulum tortor. Praesent viverra erat gravida, tempor dui mattis, tincidunt felis. Phasellus consectetur neque sit amet dignissim pellentesque. Fusce dignissim, tortor non pretium accumsan, sem justo volutpat nibh, non sodales dui arcu ut sem. Maecenas gravida odio et nunc aliquet posuere. Quisque tristique, lectus a faucibus hendrerit, nulla justo dictum enim, eu tempor tortor tortor a ligula. Quisque tortor mi, lobortis et congue ac, aliquet non mi. Vestibulum quis finibus mi.\n\nNam id hendrerit nunc, eget commodo ipsum. Suspendisse rutrum semper leo mattis lacinia. Etiam ullamcorper commodo diam non venenatis. Sed vestibulum varius mauris, sit amet tempus arcu hendrerit a. Vestibulum quis condimentum ligula, eget consectetur mauris. Nulla consectetur ex ac purus ultricies, sit amet suscipit eros efficitur. Fusce vitae fermentum orci, eu iaculis sem. Donec pretium faucibus urna volutpat aliquam. Vivamus sit amet augue a diam porta fringilla ut id enim. Nam ultrices, orci nec sollicitudin placerat, est leo sodales libero, in maximus nibh nisi a lacus. Nunc vel nunc gravida, auctor nisi sed, bibendum lorem. Maecenas sit amet lobortis augue, sed ultricies velit. Suspendisse vestibulum metus in erat molestie, ut ullamcorper eros eleifend. Etiam eget nulla nec mi vehicula volutpat nec quis libero.\n\nMorbi viverra varius neque, vel bibendum est dictum suscipit. Aenean convallis ante vitae nisl faucibus faucibus. Mauris nisi sem, pulvinar in elit nec, pretium dapibus nisl. Nullam quis tortor auctor, rhoncus lorem sit amet, maximus mauris. Nullam pellentesque tincidunt est, ut efficitur lectus finibus eget. In hac habitasse platea dictumst. Fusce sodales magna in lobortis accumsan. Curabitur gravida elit eleifend enim iaculis efficitur. Duis vel est velit. Nunc molestie, quam et condimentum euismod, ipsum lacus suscipit tellus, vitae tempor eros elit id nibh. In malesuada, dui in ultrices varius, magna est efficitur metus, quis pretium quam lectus et purus. Suspendisse vulputate ex non egestas bibendum. Fusce in interdum urna. Ut sem est, rhoncus in pellentesque non, mollis et nibh. Etiam varius a magna in cursus. Donec eget imperdiet turpis, et consectetur lectus.\n\nNam euismod nunc at bibendum accumsan. Mauris lectus leo, cursus at turpis vitae, convallis vehicula augue. Curabitur eget ante pretium eros consectetur consequat. Donec condimentum massa at auctor varius. Sed viverra nisi a quam hendrerit efficitur. Phasellus ut pulvinar justo, eu dictum ex. Suspendisse interdum lectus in lacus mattis ornare. Nunc ut sagittis nunc, vel maximus libero. Nunc at interdum nisi. Morbi sagittis mi eget lobortis sollicitudin. Maecenas dignissim velit a aliquam malesuada. Proin feugiat risus lorem, et porttitor lectus porttitor porttitor. Phasellus sodales quam placerat dui efficitur consequat. Sed iaculis tellus dapibus, ullamcorper magna non, consequat justo. Sed ut turpis blandit, posuere leo id, interdum ipsum. ','2020-12-23 12:43:29'),(147,'lorem ipsum',69,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer tortor est, congue sit amet orci sit amet, tincidunt cursus diam. Morbi metus arcu, ultricies non rhoncus venenatis, accumsan ut nunc. Cras semper suscipit laoreet. Nulla vel porttitor nibh, vel dictum leo. Donec semper metus sit amet ipsum dictum dapibus. Nam cursus egestas consequat. Donec convallis sagittis velit, ut accumsan dui porttitor non. Suspendisse dictum id diam et ullamcorper.\n\nMorbi urna purus, lacinia vitae ante nec, vestibulum accumsan tortor. Suspendisse lectus nunc, tristique eget ipsum in, fringilla elementum ligula. Aliquam placerat enim eu lacus iaculis, sed iaculis ante lacinia. Fusce non vulputate nunc, sed suscipit lorem. Etiam nisi augue, fringilla et leo a, vehicula cursus tellus. Ut vel porta quam, eget varius ligula. In imperdiet tortor ut eros porttitor, eu mollis sapien imperdiet. Mauris maximus euismod arcu, a elementum libero gravida in.\n\nDonec neque leo, pretium nec laoreet a, rutrum in lorem. Aenean mauris velit, efficitur id faucibus vel, sollicitudin et ex. Nulla elementum tempor rutrum. Donec iaculis lacinia odio non semper. Morbi pulvinar elit in est ultrices, et eleifend tellus pulvinar. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla varius laoreet dui, ut sollicitudin odio dapibus condimentum. Aenean vel volutpat sapien. Suspendisse hendrerit augue vitae sem accumsan, cursus auctor nisl malesuada. Duis vel commodo magna. Nunc sit amet leo a velit pulvinar bibendum. Vivamus sed posuere erat, ornare aliquam enim. Duis nec mauris lobortis, mollis sapien eu, tincidunt ipsum. Fusce lobortis gravida rutrum.\n\nPhasellus viverra eget nibh id rutrum. Fusce ultricies erat nec orci venenatis pulvinar. Proin tempus, nisi vel consequat congue, augue erat volutpat ipsum, ut gravida odio odio eget quam. In lectus felis, cursus sit amet euismod nec, laoreet quis nulla. Etiam rutrum in justo vitae ornare. Mauris feugiat ullamcorper ligula. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Morbi ut pellentesque diam, ut tristique urna. Donec non hendrerit odio. Nam laoreet risus justo, a aliquet elit tincidunt volutpat. Aenean semper, mauris semper tempus laoreet, mi sem tincidunt nisl, fringilla eleifend odio metus eu nulla. Curabitur tincidunt ullamcorper velit, eget scelerisque libero porttitor eget. Duis consectetur mi quis ligula faucibus, et feugiat ante molestie.\n\nDonec ac massa vitae mauris faucibus convallis a quis urna. Nunc tempus fermentum arcu. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce non massa condimentum, auctor tortor ut, rhoncus ex. Proin nec quam velit. Fusce eleifend vulputate erat eu efficitur. Suspendisse efficitur, augue eu dapibus rutrum, orci mauris malesuada sapien, sit amet elementum leo enim non ex. Aliquam fringilla est eu nisl sodales, et placerat libero finibus. Aenean ut dictum odio. Aenean ut turpis ante. Curabitur ultrices sed ipsum eu lacinia. Cras diam magna, pulvinar at sollicitudin ut, dapibus vel nulla. Aenean augue lorem, mattis sed mi eu, molestie pharetra odio. Morbi tempus, nunc nec accumsan semper, nulla quam ultrices justo, quis cursus augue sapien in arcu. \n\n\n\n\nfef','2020-12-23 12:56:27'),(150,'test',163,'tests13\n\ns\n\nAAŹSAŹŁŻ×ŹŻŹ×××÷≤≥≤≥≤≥≤≥2e12312312;3.12w12≠²³≠²€²³€','2020-12-24 11:29:26');
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
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'123@wp.ed','$2a$10$8m7v/9z47IPatCwbxvT8FOLJ7d0cEUmMqlDCgNkmtdIJju/PB8BW.','test'),(6,'123@wp.23e','2d2d3','testd'),(7,'123@wp.ed4','UJrfEvjThc3BiWF','34t34rt'),(8,'testuser3@wp.qx','$2a$10$/WxKKx2w4QpXV5IT04Usr.FvcPy0hiZJYnIFR45LMh2bRNgXAPccK','testuser3'),(9,'dqwd23d@f32.23d','$2a$10$xqlq6vkFsSH1lBPIcCDMreovJW29h6FMn.thQuztdPotflM9Q/66.','23d23d'),(10,'qwe@ert.ty','$2a$10$RjHlB3D5BGJJwfVCLEzK2uw/RbnAbQkuTc5ZuTI7QKO8i7wEIufim','ppp'),(11,'123@456.7','$2a$10$//35kBKrWcnCUJH4iLylpOEmJEiU9bElUr/hMV56WoORsTnqwyswy','hey'),(69,'qqq@qqq.sss','$2a$10$tsJwOTlcyuqCU3HfMy.GUOxgBsnAIIJx6woQsc9npXBT6qdpGBhdW','qqq'),(70,'www@www.www','$2a$10$GhGHjV8zP98icm8zCGFNfuv823qAe0vvDb8thcY6StjMmRCYFYkTq','www'),(75,'23e23f2d3','$2a$10$tw0qGoNHuGOZA0yeLwJlZupRct1DM6KeInn5t5M4dlcePBK.K3ZH.','2e323e'),(122,'a@e.sdd','$2a$10$g00SoejoAHr1Oln6XYAMyOTWR4mp75hGtuOTcRS0ZL8sELmGBv936','123'),(123,'34f34f@wr2.hr','$2a$10$QEscVZAQoLGn/Nw84gbp8egXyP50dAd/QF61MaVsuIwp2lPyvl9qq','34f34f34f'),(124,'123@wp.edd','$2a$10$v8cpfyhB8BAPFVcvMbl8BuV9SO0T8SFqjXTKu0EgfAIrhoKhU0dXq','ffw'),(125,'34f34f@d23.ee','$2a$10$LAKX830s9NukSOzDW1zy6.OyYC9KZKPgmjVGwLq5nPzVg5O3EC2Pa','wedewd'),(146,'ogu@tf.pk','$2a$10$aqqbvXPz1U6HbXBC1uIs6OC3AdcU/s/ZxWSGsqzTHIr/jMlQpdWfy','pppk'),(152,'we@f.dt','$2a$10$pUX4QpViNqd04oKBotdRwemel6SOovtTKQGQ9V1wC7RGpFxVTqtTe','ppp3'),(163,'wata@rsdf.dg','$2a$10$urqSvOiaJmvFAVuUNmkLHuLcwnQHqUDwAan2YVpExC8LA0L4y3oGm','dam');
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

-- Dump completed on 2021-01-02 19:01:18