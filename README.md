# コーディング試験 1
## 概要

下記のエンドポイントを持つ、書籍管理 API サーバーを実装してください。

- GET /books -> 全ての書籍情報の一覧を返します
- POST /books -> 指定した書籍情報を登録します

その他、明示されていない詳細については
[RESTful Web API の設計](https://learn.microsoft.com/ja-jp/azure/architecture/best-practices/api-design)
を参考にして実装してください。

データベースのスキーマについては、sql/create.sql に用意されています。
docker-compose を使用して、PostgreSQL と API サーバのコンテナを起動し、ローカル環境で実行可能な環境を構築してください。

# 基本仕様
## GET /books エンドポイント

データベースの全ての書籍情報を返します。

### 期待するリクエスト形式:

```bash
GET /books
```

### 期待するレスポンス形式:

- Header
```bash
HTTP/1.1 200
```

- Body
```bash
{
  "books": [
    {
      "id": "1",
      "title": "テスト駆動開発",
      "author": "Kent Beck",
      "publisher": "オーム社",
      "price": 3080
    },
    {
      "id": "2",
      "title": "アジャイルサムライ",
      "author": "Jonathan Rasmusson",
      "publisher": "オーム社",
      "price": 2860
    }
  ]
}
```

## POST /books エンドポイント

書籍情報を新規に登録します。

### 期待するリクエスト形式:

```bash
POST /books
```

- Body
```bash
{
  "title": "Clean Agile",
  "author": "Robert C. Martin",
  "publisher": "ドワンゴ",
  "price": 2640
}
```

### 期待するレスポンス形式:

** 成功レスポンス: **

- Header
```bash
HTTP/1.1 201
Location: http://host.domain:port/books/3
```

** 失敗レスポンス: **
- Header
```bash
HTTP/1.1 400
```

- Body
```bash
{
  "type": "about:blank",
  "title": "request validation error is occurred.",
  "detail": "title must not be blank.",
  "instance": "/books"
}
```
