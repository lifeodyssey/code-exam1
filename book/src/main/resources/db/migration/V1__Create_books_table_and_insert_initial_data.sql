CREATE TABLE books (
                       id SERIAL PRIMARY KEY ,
                       title VARCHAR(100),
                       author VARCHAR(100),
                       publisher VARCHAR(100),
                       price INTEGER
);

INSERT INTO books (title, author, publisher, price) VALUES
                                                        ('テスト駆動開発', 'Kent Beck', 'オーム社', 3080),
                                                        ('アジャイルサムライ', 'Jonathan Rasmusson', 'オーム社', 2860),
                                                        ('エクストリームプログラミング', 'Kent Beck', 'オーム社', 2420),
                                                        ('Clean Agile', 'Robert C. Martin', 'ドワンゴ', 2640);