INSERT INTO CATEGORIES (name,description) VALUES
('Ortaggi','Gli ortaggi'),
('Legumi','I legumi'),
('Frutta','la frutta'),
('Frutta Secca','La frutta secca');

INSERT INTO USERS (username,password,address,mail,admin_role) VALUES 
('samuele','samuele','via delle seghe','samuele.malavasi@gmail.com',FALSE),
('simone','simone','via delle teglie','hipersimon@gmail.com',TRUE),
('elettra','elettra','via delle pompe','blablah@gmail.com',FALSE),
('nicola','nicola','via delle castagne','blebleh@gmail.com',FALSE);

INSERT INTO AUCTIONS (USER_ID,DESCRIPTION,CATEGORY_ID,INITIAL_PRICE,MIN_INCREMENT,ACTUAL_PRICE,URL_IMAGE,DELIVERY_PRICE,DUE_DATE) VALUES
(1,'Zucchine',1,10.6,1.20,10.6,'zucchine.jpg',5,'2013-01-23 23:03:20'),
(1,'Cavolfiore',1,15,1.30,15,'cavolfiore.jpg',4.20,'2013-01-21 23:03:20'),
(1,'Finocchio',1,3,1.20,3,'finocchio.jpg',5,'2013-01-29 23:03:20'),
(3,'Pomodoro',1,9.3,1.60,9.3,'pomodoro.jpg',4.30,'2013-01-24 23:03:20'),
(3,'Radicchio',1,4.3,2,4.3,'radicchio.jpg',12,'2013-01-22 23:03:20'),
(3,'Rucola',1,7.6,1.20,7.6,'rucola.jpg',9,'2013-01-28 23:03:20');

