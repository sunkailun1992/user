# GitHub Copilot Instructions

This repository is the `user` Java/Spring Boot user-center service. Before suggesting or changing code, read `AGENTS.md` and `docs/ai-coding/README.md`.

Follow these project rules:

- Follow `docs/ai-coding/AI_DIRECTORY_STRUCTURE_GUIDE.md` before adding, moving, or deleting directories.
- Keep Java code under `src/main/java/com/kellen`; tests belong under `src/test/java/com/kellen`.
- Do not nest sibling repositories such as `utils`, `message`, `gateway`, `admin-web`, or `ai` inside this repository.
- Do not change existing secrets, Nacos addresses, database URLs, default accounts, or production configuration values. Report file paths and line numbers only.
- Backend authorization, tenant isolation, role permissions, and data scope checks must stay in backend code, not frontend assumptions.
