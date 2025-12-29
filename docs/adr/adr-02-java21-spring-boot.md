<!--
doc_id: adr-02
title: ADR: Java 21 / Spring Boot WebMVC の採用
version: 1.1.0
status: stable
source_template: docs/templates/adr-template.md
-->

# ADR: Java 21 / Spring Boot WebMVC の採用

## 1. タイトル
- Java 21 と Spring Boot WebMVC を学習用の実装基盤として採用する。

## 2. ステータス
- 採用

## 3. 背景
1. `docs/analysis/analysis-01-roa.md` で、実行環境は Java 21 以降・Spring Boot（Servlet / WebMVC）とする制約が定義された。
2. `docs/requirements/requirements-01-roa.md` の意思決定ログでも、同条件が前提として記載されている。
3. 学習目的かつ 1 日で完結する教材として、安定した環境と再現性が必要である。

## 4. 決定
1. サンプル実装と演習は Java 21 以上で実行できることを必須とする。
2. Web フレームワークは Spring Boot（Servlet / WebMVC）を採用する。
3. WebFlux など非同期スタックや別言語による実装はスコープ外とする。

## 5. 根拠
1. **安定性:** Java 21 は LTS として長期利用に適している。
2. **学習価値:** RESTful な設計と HTTP の理解に焦点を当てるため、広く普及した同期スタックが適合する。
3. **実装効率:** Spring Boot WebMVC は教材のセットアップと説明が簡潔である。
4. **代替案:**
   1) Java 17 を採用する案は、最新 LTS の機能差と将来性を考慮して却下。
   2) Spring WebFlux を採用する案は、非同期モデルの学習負荷が高いため却下。
   3) Node.js / Python など他言語は既存の制約と整合しないため却下。

## 6. 影響
- **ポジティブ:**
  1) 受講者が一般的な環境で再現しやすい。
  2) 既存の Spring Boot 資産を活用できる。
- **ネガティブ:**
  1) Java 21 の実行環境がない場合、事前準備が必要になる。
  2) 他言語利用者には直接的な移植性が低い。
- **モニタリング:**
  1) 受講環境で JDK 21 が正しくセットアップできているか確認する。
  2) 演習のビルド/実行成功率を記録する。

## 7. 実施計画
1. `pom.xml` を Java 21 に合わせて整備する。
2. Spring Boot WebMVC 前提のサンプルコードと演習を整備する。
3. セットアップ手順と必要な JDK バージョンを教材に明記する。
4. 変更が必要な場合は ADR を更新し、代替案へ移行する。
5. 実装計画は `docs/plan/plan-01-roa.md` に追記する。

## 8. ステータス更新履歴
1. YYYY-MM-DD: 採用。判断根拠: `docs/analysis/analysis-01-roa.md` の実行環境制約に準拠するため。
