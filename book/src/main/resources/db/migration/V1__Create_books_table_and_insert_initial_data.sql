CREATE TABLE books (
                       id integer PRIMARY KEY,
                       title varchar(100),
                       author varchar(100),
                       publisher varchar(100),
                       price integer
);

CREATE SEQUENCE IF NOT EXISTS BOOK_ID_SEQ
    INCREMENT BY 1
    MAXVALUE 9999999999
    MINVALUE 1
    START WITH 1
;

INSERT INTO books (id, title, author, publisher, price) VALUES (nextval('BOOK_ID_SEQ'), 'テスト駆動開発', 'Kent Beck', 'オーム社', 3080);
INSERT INTO books (id, title, author, publisher, price) VALUES (nextval('BOOK_ID_SEQ'), 'アジャイルサムライ', 'Jonathan Rasmusson', 'オーム社', 2860);
INSERT INTO books (id, title, author, publisher, price) VALUES (nextval('BOOK_ID_SEQ'), 'エクストリームプログラミング', 'Kent Beck', 'オーム社', 2420);
INSERT INTO books (id, title, author, publisher, price) VALUES (nextval('BOOK_ID_SEQ'), 'Clean Agile', 'Robert C. Martin', 'ドワンゴ', 2640);
