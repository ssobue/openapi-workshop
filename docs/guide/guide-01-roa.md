<!--
doc_id: guide-01
title: 教材: リソース指向設計
version: 1.0.0
status: stable
-->

# 教材: リソース指向設計

## 1. 目的とスコープ
1. **目的:** RESTful Web API の設計原則を理解し、OpenAPI 仕様に落とし込める状態にする。
2. **スコープ:** `openapi/openapi.yaml` の Products / Orders / OrderLines を題材に、HTTP セマンティクスとエラー設計を説明する。
3. **非ゴール:** 認証・認可、永続 DB、外部 API 連携は扱わない。

## 2. 参照 RFC と前提
1. **RFC 9110 (HTTP Semantics):** メソッドの安全性/冪等性、ステータスコード運用の基準。
2. **RFC 9112 (HTTP/1.1):** リクエスト/レスポンスのメッセージ構造の前提。
3. **RFC 9457 (Problem Details for HTTP APIs):** `application/problem+json` でのエラー応答形式。
4. **教材内の扱い:** 仕様の根拠は必ず RFC に対応付けて説明する。

## 3. 学習構成
1. **概念説明:**
   1) リソースとコレクションの違い。
   2) メソッドの意味と安全性・冪等性 (RFC 9110)。
2. **設計例:**
   1) `GET /products` は一覧取得。
   2) `POST /orders` は作成 (201 Created)。
3. **演習:**
   1) 既存のパス設計をレビューし、問題点を指摘する。
   2) Problem Details を含むエラー応答を設計する。

## 4. Problem Details の説明 (RFC 9457)
1. **必須フィールド:** `type` / `title` / `status` / `detail` / `instance`。
2. **メディアタイプ:** `application/problem+json` を使用する。
3. **拡張メンバー:** 追加情報が必要な場合は標準フィールドと衝突しない名前で付与する。

## 5. 非 RESTful な例と指摘
1. **例 1: `GET /getProducts`**
   1) 問題点: 動詞をパスに含め、リソース指向ではなく RPC 風になる。
   2) 推奨: `GET /products` に置き換える。
2. **例 2: `POST /orders/123/cancel`**
   1) 問題点: 操作をサブリソースとして扱わず、操作名がパスに露出する。
   2) 推奨: `PATCH /orders/123` で `status=cancelled` を更新する。
3. **例 3: `DELETE /products/123/delete`**
   1) 問題点: DELETE の意味と重複し、メソッドのセマンティクスを崩す。
   2) 推奨: `DELETE /products/123` を使用する。

## 6. 演習チェックリスト
- [ ] メソッド選択が RFC 9110 の安全性/冪等性と一致している。
- [ ] ステータスコードが RFC 9110 の定義に沿っている。
- [ ] エラー応答が RFC 9457 の必須フィールドを含む。
- [ ] 非 RESTful な例を 2 つ以上指摘できる。

## 7. テンプレート逸脱理由
1. 教材テンプレートが `docs/templates/` に存在しないため、本ドキュメントは独自構成とした。

## 8. 関連ドキュメント
1. `docs/requirements/requirements-01-roa.md`
2. `docs/design/design-01-roa.md`
3. `docs/adr/adr-01-rfc9457.md`
4. `docs/adr/adr-03-http-rfc9110-9112.md`
5. `openapi/openapi.yaml`
