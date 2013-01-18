DROP TABLE USERS;
create table USERS (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
address VARCHAR(200) NOT NULL,
mail VARCHAR(200) NOT NULL,
registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
admin_role BOOLEAN NOT NULL,
CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE CATEGORIES;
create table CATEGORIES (
id INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
name VARCHAR(100) NOT NULL,
description VARCHAR(200) NOT NULL,
CONSTRAINT pk_categories PRIMARY KEY (id)
);

DROP TABLE AUCTIONS;
create table AUCTIONS (
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
user_id INT NOT NULL,
description VARCHAR(100) NOT NULL,
category_id INT NOT NULL,
initial_price DOUBLE NOT NULL,
min_increment DOUBLE NOT NULL,
actual_price DOUBLE NOT NULL,
closed BOOLEAN NOT NULL DEFAULT FALSE,
url_image VARCHAR(255),
delivery_price DOUBLE NOT NULL,
due_date TIMESTAMP NOT NULL,
insertion_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_auctions PRIMARY KEY (id),
CONSTRAINT fk_categories FOREIGN KEY (category_id) REFERENCES CATEGORIES(id),
CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES USERS(id)
);

DROP TABLE SELLS;
create table SELLS (
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
seller_id INT NOT NULL,
buyer_id INT,
auction_id INT NOT NULL,
sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
final_price DOUBLE NOT NULL,
tax DOUBLE NOT NULL,
CONSTRAINT pk_sells PRIMARY KEY (id),
CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES USERS(id),
CONSTRAINT fk_auction FOREIGN KEY (auction_id) REFERENCES AUCTIONS(id)
);

DROP TABLE AUCTIONS_BIDS;
create table AUCTIONS_BIDS(
id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
INCREMENT BY 1),
auction_id INT NOT NULL,
seller_id INT NOT NULL,
bid_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
offer DOUBLE NOT NULL,
CONSTRAINT pk_auction_bid PRIMARY KEY (id),
CONSTRAINT fk_auction_bid FOREIGN KEY (auction_id) REFERENCES AUCTIONS(id)
);