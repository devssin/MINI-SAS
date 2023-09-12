
CREATE DATABASE library;

USE library;

CREATE TABLE `book` (
  `isbn` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`isbn`)
);



CREATE TABLE `client` (
  `membership_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`membership_id`)
);


CREATE TABLE `loan` (
  `isbn` bigint(20) DEFAULT NULL,
  `membership_id` bigint(20) DEFAULT NULL,
  `loan_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `returned` tinyint(1) DEFAULT 0,
  KEY `isbn` (`isbn`),
  KEY `membership_id` (`membership_id`),
  CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`) ON DELETE CASCADE,
  CONSTRAINT `loan_ibfk_2` FOREIGN KEY (`membership_id`) REFERENCES `client` (`membership_id`) ON DELETE CASCADE
);


CREATE TRIGGER loan_trigger AFTER INSERT
ON `loan`
FOR EACH ROW
BEGIN 
  DECLARE qte INT;
  UPDATE book SET quantity = quantity - 1 WHERE book.isbn = NEW.isbn;

  SELECT quantity INTO qte FROM book WHERE isbn = NEW.isbn;

  IF(qte = 0) THEN
    UPDATE book SET status = FALSE WHERE book.isbn = NEW.isbn;
  END IF;
END;


CREATE TRIGGER before_update_book_quantity
BEFORE UPDATE ON book
FOR EACH ROW
BEGIN
    IF NEW.quantity > 0 THEN
        SET NEW.status = TRUE;
    ELSE
        SET NEW.status = FALSE;
    END IF;
END;


CREATE TRIGGER update_quantity_after_return AFTER UPDATE 
ON loan
FOR EACH ROW  
BEGIN
  UPDATE book SET quantity = quantity + 1 WHERE book.isbn = NEW.isbn;
END



SELECT COUNT(*) FROM loan WHERE returned = false AND return_date > DATE(NOW());




INSERT INTO loan VALUES(2, 1, '2023-07-07' , '2023-08-09');




