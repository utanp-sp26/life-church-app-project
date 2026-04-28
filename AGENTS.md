# Multi-agent contract (Life.Church Android / Compose)

This document is the shared contract for parallel work: **merge the foundation (theme + `NavHost` + `AppShell`) before** feature agents add or complete individual screens.

## Route model: `AppRoute`

- **Source of truth** is the web app’s `src/app/routes.tsx` (sibling project `Life.church app`). The Android `sealed` hierarchy in `AppRoute` must stay in **1:1** agreement with those URL paths (use the `pattern` strings in `navigation/AppRoute.kt`).
- **New screen** = new `AppRoute` case (if it is a new destination) + one `composable(...)` line in [AppNavigation](app/src/main/java/edu/utap/life_church_app/navigation/AppNavigation.kt) (or a single well-known nav module if the project is later split).
- **Path parameters** (e.g. `stories/{id}`) use the same names as the web route; args are defined in the `composable` `arguments` list and read in the screen composable.

## Nav graph ownership

- **Owner file:** `app/src/main/java/edu/utap/life_church_app/navigation/AppNavigation.kt`  
  It contains the `AppNavigation` composable, the root `NavHost`, and all `composable` destinations. **One** workstream should own adding the first `NavHost` and `AppRoute` set; others add **stubs** or **one** new `composable` block in agreement to avoid merge conflicts.
- **Entry point:** `MainActivity` only calls `setContent { Life_church_appTheme { AppNavigation() } }` (or equivalent). It should not grow a second `NavController` or second `NavHost`.
- **Stubs:** For parallel work, the nav owner can add `composable(Route) { PlaceholderScreen(...) }` and feature agents replace the body in a follow-up.

## Per-feature packages

Use feature-silo packages so agents rarely touch the same files:

| Area        | Package (under `edu.utap.life_church_app`)   | Examples                          |
|------------|-----------------------------------------------|-----------------------------------|
| Shell / nav | `navigation/`, `ui/shell/`                    | `AppShell`, bottom bar, menu     |
| Home       | `ui/pages/home/`                              | `HomePage` and home-only UI      |
| Media      | `ui/media/`                                   | media stack, players, worship     |
| Life Groups| `ui/lifegroups/`                              | groups, find, view all            |
| Serving    | `ui/serving/`                                 | `Serving` screen                  |
| Giving     | `ui/giving/`                                  | `Giving` screen                   |
| Shared     | `ui/common/` or `ui/components/`              | reused composables only as needed |

**Rule:** Prefer **one** shared component owner; do not duplicate the same port in two feature trees.

## Foundation merge order

1. Land **theme tokens** (`ui/theme/Color.kt`, `Theme.kt`, `Type.kt`) and set `dynamicColor = false` while matching Figma / `theme.css`.
2. Land **Navigation Compose** + `AppRoute` + `AppNavigation` (scaffold, bottom bar, full-screen menu).
3. After that, **parallel** feature work: one package per agent, register new routes in `AppNavigation.kt` in a short, agreed order (or use stub pattern above).

## Done when (per feature screen)

- Compiles; screen is reachable from the graph; uses `MaterialTheme` and design tokens; images use Coil where applicable.
