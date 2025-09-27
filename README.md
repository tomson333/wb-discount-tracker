Wildberries Discount Tracker - Source bundle
-------------------------------------------
Contents:
- android-app/  - Android (Kotlin) client (same skeleton as before)
- backend/      - Flask backend integrated with Wildberries public endpoint
- .github/workflows/android-build.yml - GitHub Actions workflow to build debug APK
- Dockerfile    - for local APK build in container
- LEGAL_AND_NOTES.md - notes about API usage, rate limits, and deployment

IMPORTANT:
- I cannot build APK inside this chat environment. Use the included GitHub Actions workflow
  or Dockerfile to build the APK automatically. See instructions below.
Trigger workflow
