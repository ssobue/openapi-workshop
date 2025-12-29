<!--
doc_id: design-01
title: 設計: リソース指向設計
version: 1.0.0
status: stable
source_template: docs/templates/design-template.md
-->

# 設計: リソース指向設計

## 1. アーキテクチャ概要
1. **コンテキスト:** 学習者がサンプル Web API と教材を通じて RESTful 設計を学ぶ。外部システム連携は行わず、OpenAPI 仕様 (`openapi/openapi.yaml`) が唯一の契約となる。
2. **原則:**
   1) HTTP セマンティクスは RFC 9110、HTTP/1.1 は RFC 9112 に準拠する。
   2) エラー応答は RFC 9457 (Problem Details for HTTP APIs) を採用する。
   3) 1 日完結の学習スコープに収まるよう構成を最小化する。
3. **トレードオフ:**
   1) 単一アプリ構成 (Spring Boot WebMVC) を採用し、マイクロサービス構成は説明コストが高いため採用しない。
   2) HTTP/2 以降はスコープ外とし、HTTP/1.1 に限定する。
   3) 永続 DB は導入せず、学習の焦点を API 設計に絞る。

## 2. コンポーネント設計
1. **モジュール/クラス構成:**
   1) Controller: HTTP リクエスト受付とバリデーション。
   2) Service: ユースケースの実行とドメインルールの適用。
   3) Repository: データ保持 (インメモリ) と取得。
   4) ErrorHandler: RFC 9457 の Problem Details 生成。
2. **主要フロー:**
   1) リクエスト受信 → 形式/必須項目の検証。
   2) Service で処理 → Repository から取得/更新。
   3) 正常応答は JSON を返却。
   4) 例外発生時は ErrorHandler が `application/problem+json` を返却。
3. **データ設計:**
   1) リソース表現は `id` を必須とし、他の属性は教材で定義する (TBD)。
   2) 後方互換性は「フィールド追加のみ許容」を基本方針とする。
   3) Problem Details は `type` / `title` / `status` / `detail` / `instance` を必須とする。

## 3. API / インターフェース
1. **契約:**
   1) API は OpenAPI 仕様で定義し、入出力・バリデーション・エラー応答を明示する。
   2) ステータスコードとメソッドの意味は RFC 9110 に準拠する。
   3) エラー応答は RFC 9457 形式とし、`application/problem+json` を返す。
2. **互換性:**
   1) 既存クライアントへの影響は OpenAPI の差分で管理する。
   2) 破壊的変更は避け、必要な場合は設計変更の ADR を追加する。

## 4. 可観測性・運用
1. **計測ポイント:**
   1) リクエストのメソッド/パス/ステータス/処理時間をログに記録する。
   2) エラー時は Problem Details の `type` と `status` を記録する。
2. **運用フロー:**
   1) ローカル実行を前提とし、デプロイ手順は最小限とする。
   2) ロールバックは Git の履歴を用いて行う。

## 5. セキュリティ・プライバシー
1. **脅威モデル:**
   1) 入力不正に対する検証不足。
   2) 意図しない情報露出。
2. **データ保護:**
   1) 実データや個人情報は扱わない。
   2) すべての入力は型と範囲の検証を行う。
3. **コンプライアンス:**
   1) ローカル実行のみで外部公開は行わない。

## 6. 依存関係と制約
1. **外部依存:**
   1) Java 21 以上。
   2) Spring Boot (Servlet / WebMVC)。
   3) RFC 9110 / RFC 9112 / RFC 9457。
   4) OpenAPI 仕様 (`openapi/openapi.yaml`)。
2. **制約:**
   1) 学習目的で 1 日以内に完了できる構成とする。
   2) 認証・認可、永続 DB、外部 API 連携はスコープ外とする。
   3) Mavenサブモジュールとし、`pom.xml`に直接Dependencyを追加しないものとする。

## 7. テスト戦略
1. **レベル別テスト:**
   1) ユニットテスト: Service と ErrorHandler の挙動検証。
   2) 統合テスト: Controller からの入出力と RFC 9457 形式を検証。
   3) 契約テスト: OpenAPI のレスポンス定義と実装の整合性を確認。
2. **環境:**
   1) ローカル環境で完結し、外部依存を持たない。
   2) テストデータはインメモリで管理する。

## 8. 関連ドキュメント
1. `docs/analysis/analysis-01-roa.md`
2. `docs/requirements/requirements-01-roa.md`
3. `docs/adr/adr-01-rfc9457.md`
4. `docs/adr/adr-02-java21-spring-boot.md`
5. `docs/adr/adr-03-http-rfc9110-9112.md`
6. `docs/plan/plan-01-roa.md`（予定）
