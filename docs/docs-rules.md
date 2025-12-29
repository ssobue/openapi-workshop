<!--
doc_id: guide-01-docs-rules
title: 文書管理ルール
version: 1.0.1
status: stable
-->

# 文書管理ルール

本ドキュメントは、`docs/` 配下の Markdown 文書および  
`docs/templates/` に置かれたテンプレートを **一貫性・再現性・機械可読性** をもって管理するためのルールを定義する。

---

## 0. 基本方針

- 文書は **人間が読めること** と **エージェントが解析できること** を両立する
- すべての管理対象 Markdown に **メタ情報ヘッダー** を付与する
- 文書の識別は **doc_id を唯一の正とする**
- **doc_id とファイル名は一致**させる
- Git の履歴を正とし、人的レビュー前提の属性は持たない

## 1. メタ情報ヘッダー（必須）

### 1.1 記述形式

管理対象の Markdown は、**ファイル先頭**に以下の HTML コメント形式のメタ情報を持つ。

```md
<!--
doc_id: analysis-01
title: 要求分析: リソース指向API
version: 1.0.0
status: draft
-->
````

### 1.2 採用するキー

| key               | 必須 | 説明              |
| ----------------- | -- | --------------- |
| `doc_id`          | ✓  | 文書の一意識別子        |
| `title`           | ✓  | 表示用タイトル         |
| `version`         | ✓  | 文書バージョン         |
| `status`          | ✓  | 文書状態            |
| `source_template` | 任意 | テンプレート由来の場合のみ指定 |

※ owner / reviewers / created / updated は **運用負荷削減のため採用しない**

## 2. title のルール

### 2.1 H1 との完全一致を必須とする

メタ情報の `title` は、本文の **H1 見出しと完全一致** させる。

```md
<!--
title: 要求分析: リソース指向API
-->

# 要求分析: リソース指向API
```

### 2.2 title の役割

* 人間向けの意味ラベル
* Copilot / Agent が文書内容を即座に把握するための要約軸
* doc_id の抽象性を補完するもの

※ title は **識別子としては使用しない**

## 3. doc_id 命名規則

### 3.1 基本形式

```
{category}-{NN}
```

例：

* `analysis-01`
* `analysis-01-roa`
* `design-02-authz`
* `adr-03-db-schema`

### 3.2 category（固定語彙）

| category | 意味     |
| -------- | ------ |
| analysis | 要求分析   |
| design   | 設計     |
| spec     | 仕様     |
| adr      | 意思決定記録 |
| guide    | ガイド・手順 |
| glossary | 用語集    |

---

## 4. ファイルパス規則（最重要）

### 4.1 原則

**ファイル名 = doc_id**

```
docs/{category}/{doc_id}[-{topic}].md
```

例：

```
doc_id: analysis-01
→ docs/analysis/analysis-01.md
→ docs/analysis/analysis-01-roa.md
```

### 4.2 非推奨例

```
doc_id: analysis-01
docs/analysis/ana01-roa.md
```

理由：

* doc_id とファイルパスの対応をエージェントが保証できない
* 自動リンク生成・重複検知が困難になる

## 5. version ルール（軽量 SemVer）

| 変更内容     | 変更指針  |
| -------- | ----- |
| 誤字・表現修正  | PATCH |
| 内容追加     | MINOR |
| 意味・前提の変更 | MAJOR |

補足：

* `status=draft` 中は厳密な運用を要求しない
* 「後で読んだとき意味が変わった」と感じたら MINOR 以上

## 6. status（個人開発向け）

### 6.1 使用するステータス

| status     | 意味       |
| ---------- | -------- |
| draft      | 作業中      |
| stable     | 現時点で正とする |
| deprecated | 置き換え予定   |
| archived   | 凍結（参照のみ） |

※ review / approved は採用しない

## 7. テンプレート管理（docs/templates）

### 7.1 テンプレートも文書として管理する

テンプレート自身もメタ情報を持つ。

```md
<!--
doc_id: analysis-template
title: 要求分析: {topic}
version: 1.0.0
status: stable
-->
```

### 7.2 生成文書側のルール

テンプレート由来の文書は `source_template` を記載する。

```md
<!--
doc_id: analysis-01
title: 要求分析: リソース指向API
version: 1.0.0
status: draft
source_template: docs/templates/analysis-template.md
-->
```

## 8. Agent / CI による検証項目

### 8.1 必須チェック

* メタ情報ヘッダーが存在する
* `doc_id / title / version / status` が存在する
* `doc_id` が一意
* ファイル名が `doc_id` と一致する
* H1 が 1 つだけ存在し、`title` と一致する

### 8.2 任意チェック

* `status=deprecated` の場合、後継 doc_id が本文に明記されている
* `source_template` のパスが実在する

## 9. 最小運用フロー

1. テンプレートをコピー
2. doc_id を決定
3. ファイル名を doc_id に合わせる
4. title と H1 を同期させる
5. 書く
6. 固まったら `status=stable`

## 10. 設計上の結論

本ルールは、

* 個人開発
* Copilot / エージェント主導
* 将来の再利用・再生成

を前提とし、
**doc_id・title・ファイルパスの三点一致**を軸に
最小の運用コストで最大の自動化耐性を得ることを目的とする。

この構成を守る限り、
文書は「人間にも AI にも扱いやすい状態」を保ち続けられる。
