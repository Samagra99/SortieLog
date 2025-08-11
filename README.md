# Sortie Logger

Offline Android app for logging sortie data (off block, on block, airborne, touchdown times) with Excel report generation and FDTL calculation.

## Building the APK Online

You can build this app without a local PC by using **GitHub Codespaces** or **Gitpod**.

---

### Option 1: GitHub Codespaces
1. Create a new **private repository** on GitHub.
2. Upload all project files (from batches 1, 2, and 3) into the repo, keeping the folder structure intact.
3. On GitHub, click **Code** → **Open with Codespaces** → **New Codespace**.
4. In the terminal inside Codespaces, run:
   ```bash
   ./gradlew assembleDebug
