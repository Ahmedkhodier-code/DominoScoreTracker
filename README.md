# Domino Score Tracker (عداد الدومينو) 🎲☕

A modern **Kotlin Multiplatform (KMP)** application built with **Jetpack Compose Multiplatform**, designed to track domino game scores, manage matches, competitors, bets & prizes, and review game history with an offline-first architecture.

---

## 🌟 Key Features (المميزات الرئيسية)

- **Kotlin Multiplatform (KMP)**: Shared logic (`sharedLogic`) and shared UI (`sharedUI`) targeting Android and iOS.
- **Jetpack Compose Multiplatform & Material 3**: Beautiful, fluid, and responsive UI with custom styling matching Domino Cafe aesthetics.
- **Offline-First & Local Storage**: 
  - **Room 3** database for relational persistence (Games, Teams, Scores).
  - **Preferences DataStore** for app settings and theme preferences.
- **Dependency Injection**: Powered by **Koin 4.x** for seamless shared and platform DI.
- **Advanced Game Management**:
  - **Create Game**: Setup challenge names, Team 1 vs Team 2 (Head-to-Head), prize names & costs, and target winning scores.
  - **Active Game Screen**: Live score tracking with round-point adding, dynamic team score cards, leading/trailing indicators, and progress bars.
  - **Winner Announcement (`WinnerDialog`)**: Celebratory dialog with pulsing glow animations, final scores, and earned prize details.
  - **Games List**: Filter matches by All, Active, or Completed with statistics summary.
  - **Settings**: Appearance & Theme (Dark, Light, System), language preferences, and data management.

---

## 🏗️ Project Architecture (هيكلية المشروع)

```text
DominoScoreTracker/
├── androidApp/          # Android platform entry point & MainActivity
├── iosApp/              # iOS platform entry point
├── sharedLogic/         # Domain models, repositories, Room database, Use Cases, Koin DI
└── sharedUI/            # Compose Multiplatform UI, Screens, ViewModels, Navigation, Theme
```

---

## 🛠️ Tech Stack (التقنيات المستخدمة)

- **Language**: Kotlin 2.4+
- **UI Framework**: Jetpack Compose Multiplatform (Material 3)
- **Database**: Room 3 (SQLite bundled)
- **Local Preferences**: AndroidX Preferences DataStore
- **Dependency Injection**: Koin 4.x
- **Navigation**: Jetpack Navigation Compose for KMP
- **Lifecycle & State**: Jetpack Lifecycle ViewModel & Kotlin Coroutines / StateFlow

---

## 🚀 Getting Started (البدء السريع)

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Run the `:androidApp` run configuration to launch on an Android device or emulator.
