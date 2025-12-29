# ドキュメント向けエージェントガイドライン

このディレクトリには、生成AIを用いた要件定義・設計・実装計画のテンプレートと手順が含まれています。`docs/` 配下のファイルを扱う際は、以下を遵守してください。

1. 要件ドキュメントを作成・改訂する前に、必ず `docs/templates/requirements-template.md` のテンプレートを参照してください。
2. 要求分析は `docs/templates/analysis-template.md`、設計は `docs/templates/design-template.md`、実装計画は `docs/templates/plan-template.md`、意思決定は `docs/templates/adr-template.md` に沿って作成し、それぞれを `analysis.md`、`design.md`、`plan.md`、`ADR` 系のファイルとしてプロジェクトに取り込んでください。
3. 仕様の明確さと設計規律の例として、OpenAPI Workshop のドキュメント（https://github.com/ssobue/openapi-workshop/tree/main/docs）を必ず併読し、用語を整合させてください。
4. 指示は簡潔で実行可能な形にまとめ、AI ワークフローで扱いやすいよう番号付きリストやチェックリストを優先してください。
5. テンプレートから外れる場合は、その理由を明記してください。
6. 新しいテンプレートを追加した際は、本ファイルを更新し、OpenAPI Workshop ドキュメントとの併用方法を記述してください。
