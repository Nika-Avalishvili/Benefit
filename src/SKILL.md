---
name: arxiv-researcher
description: Use when conducting academic literature research, searching for papers on a topic, reading specific arXiv papers, surveying a field, finding related work, or tracking an author's publications
---

# arXiv Researcher

## Overview

Use Claude Code's built-in tools to search arXiv, download papers, and extract text. No external servers needed — just WebFetch, Bash (curl + pdftotext), and Read.

**Core principle:** Scan abstracts before committing to full reads. Use page ranges — never read 30-page papers end-to-end when you need only intro and conclusions.

**Dependency:** `poppler-utils` must be installed (`sudo apt-get install poppler-utils`). Check with `which pdftotext` before first use.

## Tool Workflow

### 1. Search: WebFetch → arXiv API

```
WebFetch(
  url = "https://export.arxiv.org/api/query?search_query=all:{query}&start=0&max_results={n}&sortBy=relevance",
  prompt = "Extract a numbered list of papers. For each: arXiv ID, title, authors, abstract (first 2 sentences), published date, and PDF URL."
)
```

- Default `max_results`: 10 (max 50)
- `sortBy` options: `relevance` (default), `lastUpdatedDate`, `submittedDate`
- Add `&sortOrder=descending` for newest-first

### 2. Download: Bash curl

```bash
curl -sL -o /tmp/arxiv-{id}.pdf https://arxiv.org/pdf/{id}.pdf
```

### 3. Extract text: Bash pdftotext

```bash
# Full paper
pdftotext -layout /tmp/arxiv-{id}.pdf /tmp/arxiv-{id}.txt

# Specific page range (e.g., pages 1-5)
pdftotext -layout -f 1 -l 5 /tmp/arxiv-{id}.pdf /tmp/arxiv-{id}-p1-5.txt
```

The `-layout` flag preserves spatial positioning — critical for tables and equations.

### 3a. Check paper length

```bash
wc -l /tmp/arxiv-{id}.txt
```

- Short (<500 lines): read fully
- Medium (500-1500): use page ranges (intro + conclusion + targeted sections)
- Long (>1500): intro + conclusion + targeted sections only

### 4. Read: Read tool on extracted text

```
Read(file_path = "/tmp/arxiv-{id}.txt")
```

Use `offset` and `limit` for long papers.

## arXiv Query Syntax

Build the `search_query` URL parameter using these patterns:

| Syntax | URL parameter | Reliability |
|--------|--------------|------------|
| Plain keywords | `all:large+language+models` | Always works |
| Author | `au:Vaswani` | Usually works |
| Title words | `ti:attention+mechanism` | Usually works |
| Category | `cat:cs.LG` | Usually works |
| Exact arXiv ID | Skip search — go straight to download | Always works |
| Combined | `au:Vaswani+AND+ti:attention` | Use AND/OR/ANDNOT |

URL-encode spaces as `+`. Combine fields with `+AND+`, `+OR+`, `+ANDNOT+`.

**When field prefixes fail:** Fall back to plain keywords — include author name and topic terms together (e.g., `all:Shazeer+mixture+of+experts`).

## Research Workflow

1. **Search broadly** — run 2-3 queries with different keyword combinations
2. **Filter by abstract** — scan returned abstracts before downloading any paper
3. **Download and extract selectively** — only download papers worth reading
4. **Read strategically** — Read first 3 pages (intro). Check total with `wc -l`. Read last 2-3 pages (conclusion). Read method/results selectively if needed.
5. **Track IDs** — record arXiv IDs; reference lists in papers are a source for related work
6. **Pin versions when citing** — use `1706.03762v5` syntax for reproducible references

## Report Output

**When to save a report:** Multi-paper research sessions (3+ papers read), topic surveys, or literature reviews. Skip for quick single-paper lookups or one-off abstract checks.

**Directory:** `/home/luser/IdeaProjects/local-projects/workspace/science/`
**Filename:** `YYYY-MM-DD-<topic-slug>.md`

**Report template (lean, findings-first):**

```markdown
# [Research Topic]
**Date:** YYYY-MM-DD | **Papers read:** N | **Scope:** [one-line]

## Key Findings (actionable)
- Finding 1: [principle] — [evidence from paper X]
- ...

## Detailed Evidence
### [Topic cluster 1]
...

## Implications for [relevant domain]
[Direct mapping of findings to practical use]

## References
| # | Paper | arXiv ID | Year | Focus |
|---|-------|----------|------|-------|
```

**Index management:** Append one row to `science/index.md` per report:
```
| YYYY-MM-DD | [topic] | [filename] | [paper count] | [one-sentence key insight] |
```
Create `science/index.md` with header row if it does not exist.

## Rate Limiting

arXiv asks for a 3-second delay between API requests. Space out WebFetch calls accordingly. Bulk downloads should also be paced.

## Common Mistakes

| Mistake | Fix |
|---------|-----|
| Reading entire 30-page paper at once | Use page ranges with `-f`/`-l` flags |
| Downloading before checking abstract | Search returns abstracts — read those first |
| Single query producing poor results | Try alternate terminology, different keyword combinations |
| Citing with bare ID | Use version-pinned ID (`1706.03762v5`) for stable references |
| Forgetting `-layout` flag | Always use `pdftotext -layout` — without it, tables and equations lose structure |
| pdftotext not installed | Run `sudo apt-get install poppler-utils` |
