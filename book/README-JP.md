# Bookサービス

## 必要なもの

以下のツールをインストールしてください：

- gradle
- Java 17
- Docker

## ローカルでの実行方法

1. プロジェクトのルートディレクトリに`.env`ファイルを作成し、`.env.example`ファイルを参考にしてください。
2. 以下のコマンドを実行してください：

```bash
export $(cat .env | xargs)
./gradlew clean build
docker-compose -f docker-compose-local.yml up --build -d
```

## ローカルでのテスト方法

1. すべての本を取得する

```bash
curl -X GET http://localhost:8081/books/ -H "Accept: application/json"
```

注意：レスポンスには4冊の本があります。詳細は
`code-exam1/book/src/main/resources/db/migration/V1__Create_books_table_and_insert_initial_data.sql`
で確認できます。

2. 本を作成する

```bash
curl -X POST http://localhost:8081/books \
     -H "Content-Type: application/json" \
     -v \
     -d '{
           "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
           "author": "Robert C. Martin",
           "publisher": "Pearson",
           "price": 6430
         }'
        
```

3. タイトルが空の本を作成する（エラーケース）

```bash
curl -X POST http://localhost:8081/books \
     -H "Content-Type: application/json" \
     -v \
     -d '{
           "title": "",
           "author": "Robert C. Martin",
           "publisher": "Pearson",
           "price": 6430
         }'
        
```

## 全体的な構造

これは本を作成したり取得したりする簡単なサービスです。以下の技術を使っています：

- Java 17
- Spring Boot 3
- Docker
- PostgreSQL
- Flyway
- JUnit 5
- Mockito
- Testcontainers
- Gradle

このサービスは、コントローラー、サービス、リポジトリの3層で構成されています。

「Keep It Simple and Stupid」ルールに従って、複雑なDDDは使っていません。

## 特徴

- コードの品質を確保するための包括的なビルドステップ。ビルドステップには以下が含まれます：
    - テスト
    - Checkstyle：spotlessを使用
    - Spotbugs
    - Jacoco：テストカバレッジは90%以上（現在は100%）。以下のコマンドで確認できます：
      ```bash
      ./gradlew clean build jacocoTestReport
      ```
      そして、`book/build/jacocoHtml/index.html`ファイルをブラウザで開きます。

- テスト：テスト駆動開発（TDD）のアプローチでテストを実装しました。テストには以下のタイプがあります：
    - ユニットテスト：サービス層のテストはJUnit 5とMockitoを使用。コントローラー層のテストは`@WebMvcTest`とmockMVCを使用。
    - 統合テスト：リポジトリ層のテストはTestcontainersとJUnit 5を使用。
    - アプリケーションテスト（またはAPIテスト）：SpringアプリケーションのテストはTestcontainersを使用。
- 設定：`.env`ファイルを使って設定を外部化しています。そのため、コードを変更せずに異なる環境にDockerイメージをデプロイできます。
- データベース：Flywayでデータベースを管理しています。スキーマはバージョン管理され、アプリケーション起動時に自動的にマイグレーションが行われます。
- クリーンコード：いくつかのクリーンコードの実践を行いました。例えば、共通のテストコードのために`ApplicationTestBase`
  を抽出したり、`BookService`インターフェースと`BookServiceImpl`実装のようなSOLID原則、`Book`エンティティのビルダーパターンなどです。
- グローバル例外処理：アプリケーションレベルで例外を処理するグローバル例外ハンドラを実装しました。

## 改善点

- ログ：まだログ機能を実装していませんが、本番環境では重要です。
- セキュリティ：この2つのAPIには認証がありません。実際の環境では、インフラレベルまたはアプリケーションレベルで認証を実装できます。そのため、空のままにしています。
- コミットフック：チーム開発では重要です。
-

さらなるリファクタリング：コードをさらにリファクタリングできます。例えば、エラーメッセージとグローバル例外は、ハードコードされた文字列の代わりに列挙型や定数を使ってリファクタリングできます。さらなるリファクタリングは、コードのコミットを増やすことで可能です。

- CI/CD：まだCI/CDパイプラインを実装していません。現在のコンテナ化とGradle設定により、CI/CDパイプラインへの導入が容易になっています。
-

APIドキュメント：まだAPIドキュメントを書いていません。今はSwaggerを追加しました。``http://localhost:8081/swagger-ui/index.html``
でアクセスできます。