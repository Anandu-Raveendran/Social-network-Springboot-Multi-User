create DATABASE IF NOT EXISTS poster;

USE poster;
DROP TABLE IF EXISTS `likes`;
DROP TABLE IF EXISTS `comments`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `follow`;
DROP TABLE IF EXISTS `verification_code`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255),
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL UNIQUE,
  `email` varchar(255) UNIQUE,
  `phone` varchar(10) unique,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` varchar(3) NOT NULL,
  `image_location` varchar(500)default NULL,
  `region` varchar(255),
  `date_of_joining` Long,
  `last_active_on` Long,
  `email_verified` bool default false,
  `phone_verified` bool default false,
  `status` int(1) not null default 0,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
);


-- LOCK TABLES artshop.ROLE WRITE;
INSERT INTO role VALUES (1,'ROLE_USER');
INSERT INTO role VALUES (2,'ROLE_CELEBRITY');
INSERT INTO role VALUES (3,'ROLE_ADMIN');
INSERT INTO role VALUES (4,'ROLE_GOD');
-- UNLOCK TABLES;

CREATE TABLE if not exists verification_code(
 v_code_id varchar(255) not null,
 user_id varchar(255) NOT NULL ,
 code varchar(50) not null unique,
 mode int(1) not null,
 created_on Long NOT NULL,
 primary key (v_code_id),
 FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE if not exists follow(
 follow_id varchar(255) not null,
 user_id varchar(255) NOT NULL,
 followed_by_user_id varchar(255) NOT NULL not null,
 primary key(follow_id),
 CONSTRAINT FK_followingUser FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
 CONSTRAINT FK_followerUser FOREIGN KEY (`followed_by_user_id`) REFERENCES `user` (`user_id`)
);


CREATE TABLE if not exists post(
 post_id varchar(255) not null,
 user_id varchar(255) NOT NULL,
 img_location varchar(500),
 post_title varchar(100),
 post_text varchar(1000),
 likes_count int(11) default 0,
 post_status int(1) default 0,
 created_on   Long not null,
 primary key(post_id),
 foreign key (user_id) references user(user_id)
);

CREATE TABLE if not exists likes(
 like_id varchar(255) not null,
 user_id varchar(255) NOT NULL,
 post_id varchar(255) not null,
 primary key(like_id),
 FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
 FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE if not exists comments(
 comment_id varchar(255) not null,
 reply_to_comment_id varchar(255) default NULL,
 user_id varchar(255) NOT NULL,
 post_id varchar(255) not null,
 comment varchar(1000),
 created_on   Long not null,
 primary key(comment_id),
 FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
 FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
);


select* from user;
select* from verification_code;