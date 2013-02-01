INSERT INTO CATEGORIES (name,description) VALUES
('Ortaggi','Gli ortaggi'),
('Legumi','I legumi'),
('Frutta','la frutta'),
('Frutta Secca','La frutta secca');

INSERT INTO USERS (username,password,address,mail,admin_role) VALUES 
('samuele','samuele','via delle seghe','samuele.malavasi@gmail.com',FALSE),
('simone','simone','via delle teglie','hipersimon@gmail.com',TRUE),
('elettra','elettra','via delle pompe','hipersimon@gmail.com',FALSE),
('aldo','aldo','via delle prese','hipersimon@gmail.com',FALSE),
('gina','gina','via delle maree','hipersimon@gmail.com',FALSE),
('marco','marco','via delle maree','samuele.malavasi@gmail.com',FALSE),
('nicola','nicola','via delle castagne','hipersimon@gmail.com',FALSE);

INSERT INTO AUCTIONS (USER_ID,DESCRIPTION,CATEGORY_ID,INITIAL_PRICE,MIN_INCREMENT,ACTUAL_PRICE,URL_IMAGE,DELIVERY_PRICE,DUE_DATE) VALUES
(1,'Zucchine',1,10.6,1.20,10.6,'zucchine.jpg',5,'2013-02-12 21:03:20'),
(1,'Cavolfiore',1,15,1.30,15,'cavolfiore.jpg',4.20,'2013-02-11 18:03:20'),
(1,'Finocchio',1,3,1.20,3,'finocchio.jpg',5,'2013-02-10 14:01:20'),
(3,'Pomodoro',1,9.3,1.60,9.3,'pomodoro.jpg',4.30,'2013-02-9 12:43:20'),
(3,'Radicchio',1,4.3,2,4.3,'radicchio.jpg',12,'2013-02-8 19:23:20'),
(3,'Rucola',1,7.6,1.20,7.6,'rucola.jpg',9,'2013-02-7 12:33:10'),
(4,'Fagioli',2,4,2.20,4,'fagioli.jpg',5,'2013-02-7 23:33:10'),
(4,'Lenticchie',2,12,1.2,12,'na.jpg',7,'2013-02-11 13:03:20'),
(4,'Farro',2,7,0.34,7,'na.jpg',9,'2013-02-9 14:43:20'),
(5,'Mele',3,9,0.50,9,'mela.jpg',12,'2013-02-11 12:03:20'),
(5,'Arance',3,6,0.70,6,'arancia,jpg',34,'2013-02-11 19:03:20'),
(5,'Fragole',3,4,0.45,4,'fragole.jpg',12,'2013-02-7 11:43:20'),
(6,'Kiwi',3,9,1,9,'kiwi.jpg',6,'2013-02-11 23:03:20'),
(6,'Banane',3,3,1.23,3,'banane.jpg',7,'2013-02-12 18:03:20'),
(6,'Arachidi',4,13,0.20,13,'arachidi.jpg',9,'2013-02-8 12:43:20'),
(7,'Mandorle',4,34,3,34,'mandorle.jpg',3,'2013-02-10 18:03:20'),
(7,'Nocciole',4,12,0.67,12,'nocciole.jpg',0,'2013-02-9 23:33:10'),
(7,'Pistacchi',4,12,0.45,12,'pistacchi.jpg',0,'2013-02-10 23:33:10'),
(7,'Noci',4,13,2.07,13,'noci.jpg',3,'2013-02-8 19:23:20');
 
