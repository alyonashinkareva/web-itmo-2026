-- Схема БД проекта. Выполняется автоматически один раз — при создании пустой базы.
-- Каждое изменение структуры (CREATE TABLE, ALTER TABLE, индексы) выполните на своей базе
-- (Adminer или консоль) и допишите тот же SQL в конец этого файла.

CREATE TABLE `User` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `login`        VARCHAR(255) NOT NULL,
    `passwordSha`  VARCHAR(255) NOT NULL,
    `creationTime` DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_User_login` (`login`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

ALTER TABLE `User`
    ADD COLUMN `email` VARCHAR(255) NULL,
    ADD UNIQUE KEY `uk_User_email` (`email`),
    ADD COLUMN `admin` BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE `Event` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` BIGINT NOT NULL,
    `type` ENUM('ENTER', 'LOGOUT') NOT NULL,
    `creationTime` DATETIME NOT NULL,
    CONSTRAINT `fk_Event_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `Talk` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sourceUserId` BIGINT NOT NULL,
    `targetUserId` BIGINT NOT NULL,
    `text` TEXT NOT NULL,
    `creationTime` DATETIME NOT NULL,
    CONSTRAINT `fk_Talk_sourceUserId` FOREIGN KEY (`sourceUserId`) REFERENCES `User` (`id`),
    CONSTRAINT `fk_Talk_targetUserId` FOREIGN KEY (`targetUserId`) REFERENCES `User` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `Article` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` BIGINT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `text` TEXT NOT NULL,
    `creationTime` DATETIME NOT NULL,
    `hidden` BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT `fk_Article_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
