-- ----------------------------------
-- 사용자 테이블
-- ----------------------------------
CREATE TABLE users (
	`id` VARCHAR(20) NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	`password` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;