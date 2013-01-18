INSERT INTO CATEGORIES (name,description) VALUES
('Ortaggi','Gli ortaggi'),
('Legumi','I legumi'),
('Frutta','la frutta'),
('Frutta Secca','La frutta secca');

INSERT INTO USERS (username,password,address,mail,admin_role) VALUES 
('samuele','samuele','via delle seghe','samuele.malavasi@gmail.com',FALSE),
('simone','simone','via delle teglie','hipersimon@gmail.com',TRUE),
('elettra','elettra','via delle pompe','blablah@gmail.com',FALSE);

INSERT INTO AUCTIONS (USER_ID,DESCRIPTION,CATEGORY_ID,INITIAL_PRICE,MIN_INCREMENT,ACTUAL_PRICE,URL_IMAGE,DELIVERY_PRICE,DUE_DATE) VALUES
(1,'Zucchine',1,10.6,1.20,10.6,'zucchine.jpg',5,'2013-01-20 23:03:20');

