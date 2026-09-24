repo: SwapnilMeshram19/cosmopedia
branch: main

## Last sync
date: 2026-09-24T03:55:24Z
commit: cbda8679941d

### Updated in this project
- No new upstream changes since last sync — repo commit still matches the initial manual push.
- Local project is currently AHEAD of the repo: manifest.json enrichment (id, description, scope, lang, dir, maskable icon) and sw.js (service worker) were added locally to satisfy PWABuilder audit warnings, but not yet pushed by user.
- Repo is still missing: quiz-en.js, and the Hindi/Spanish content only spans cosmos-hi-1..5 / cosmos-es-1..6 (no cosmos-en-*.js — English content lives in the single cosmos-data.js, which is correct/expected).

## Screen map
| Project screen | Repo files |
|---|---|
| index.html (production, no bezel/notes) | index.html |
| Cosmopedia App.dc.html (design-preview source) | not pushed — design-tool only |
| astro.js | planet/moon/sky-position math |
| iss-map.html | live ISS map (d3 + wheretheiss.at) |
| i18n.js | UI strings (en/hi/es) |
| cosmos-data.js, cosmos-hi-*.js, cosmos-es-*.js | encyclopedia content + translations |
| quiz-hi.js, quiz-es.js | quiz questions + translations (quiz-en.js missing) |
| manifest.json, sw.js, icons/ | PWA manifest, service worker, app icons (local versions ahead of repo) |
