# Multi-agent contract (pointer)

The full **merge order, `AppRoute` rules, nav ownership, and per-feature packages** for this repo are documented in [`AGENTS.md`](AGENTS.md).

**Source of truth for route strings:** the KDoc and `sealed class` in `app/src/main/java/edu/utap/life_church_app/navigation/AppRoute.kt` (use the `pattern` field for `navController` and `composable(...)`). The web reference is `src/app/routes.tsx` in the Life.Church web app when you have it checked out.

**Nav graph owner:** `app/src/main/java/edu/utap/life_church_app/navigation/AppNavigation.kt` (root `NavHost` only; do not add a second `NavController` in `MainActivity`).

This file stays short so it does not diverge from `AGENTS.md`.
