# RGB Color Picker

## 1) Project Overview

RGB Color Picker is a Jetpack Compose Android app that lets users mix red, green, and blue channels (0-255), preview the resulting color, and copy its HEX value.

### Technical goals
- Keep the app simple and responsive with a Compose-first UI.
- Persist color state across process recreation using `SavedStateHandle`.
- Establish a base architecture that can evolve to a multi-feature app.

### High-level architecture
- **Single-module app** (`:app`) with clear package boundaries.
- **Presentation state holder** via `ColorPickerViewModel`.
- **Unidirectional state flow** from ViewModel to UI (`StateFlow` + immutable UI state).

---

## 2) Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- AndroidX Lifecycle ViewModel + SavedStateHandle
- Kotlin Coroutines + StateFlow
- JUnit4
- Turbine (Flow testing)
- AndroidX Instrumentation Test + Espresso (template)
- Compose UI Test dependencies (available for instrumentation UI tests)

---

## 3) Architecture

This project uses a **lightweight MVVM-style presentation architecture**:

- `MainActivity` hosts Compose and wires ViewModel state to screen callbacks.
- `ColorPickerViewModel` owns and updates screen state.
- `ColorPickerUiState` is immutable and represents the single source of truth for the screen.
- `RGBColorPickerScreen` is a UI function that renders state and emits user actions.

### Why this architecture
- Strong separation between UI rendering and state/business decisions.
- Easier unit testing for state transitions.
- Better maintainability than embedding logic directly in composables.
- Good stepping stone toward full Clean Architecture when domain/data grow.

### Current benefits
- Separation of concerns
- Testability
- Predictable state handling
- Simple scalability for additional screens/features

---

## 4) Project Structure

Current structure:

```text
app
 └── src
     ├── main
     │   ├── java/com/example/rgbcolorpicker
     │   │   ├── MainActivity.kt
     │   │   ├── presentation
     │   │   │   ├── state
     │   │   │   │   └── ColorPickerUiState.kt
     │   │   │   └── viewmodel
     │   │   │       └── ColorPickerViewModel.kt
     │   │   └── ui
     │   │       ├── screens
     │   │       │   └── ColorPickerScreen.kt
     │   │       └── theme
     │   │           ├── Color.kt
     │   │           ├── Theme.kt
     │   │           └── Type.kt
     │   └── AndroidManifest.xml
     ├── test/java/com/example/rgbcolorpicker
     │   └── ExampleUnitTest.kt
     └── androidTest/java/com/example/rgbcolorpicker
         └── ExampleInstrumentedTest.kt
```

### Layer explanation

#### Presentation Layer
- **UI**: Compose screen and reusable composable pieces for rendering.
- **ViewModel**: screen-level state holder and action handler.
- **UI State**: immutable `ColorPickerUiState` consumed by composables.

#### Domain Layer
- Not present yet (expected for complex business rules/use cases).

#### Data Layer
- Not present yet (no remote/local data source in this app version).

---

## 5) Navigation

There is currently **one screen only**, so Navigation Compose is not yet required.

If a second feature is added, the recommended next step is:
- Introduce `navigation/` package.
- Add `NavHost` + typed routes.
- Keep screen registration centralized in one navigation graph.

---

## 6) State Management

State is managed using:

- `ColorPickerViewModel` as the state owner.
- `MutableStateFlow` internally.
- `StateFlow<ColorPickerUiState>` exposed to UI.
- Immutable `ColorPickerUiState` as the single source of truth.
- `SavedStateHandle` to restore RGB values after process recreation.

Data flow:
1. User interaction in UI (slider drag).
2. Callback triggers ViewModel action (`updateRed/Green/Blue`).
3. ViewModel updates `uiState`.
4. Compose recomposes the screen with new state.

---

## 7) Testing Strategy

### Current tests
- **Unit tests** (JVM):
  - ViewModel state transition tests.
  - Saved state restoration test.
  - RGB bounds clamping test.
  - Flow emission assertions with Turbine.
- **Instrumentation tests**:
  - Default template test exists (`ExampleInstrumentedTest`).

### Tools in use
- JUnit4
- Turbine
- AndroidX test runner / Espresso (basic setup)
- Compose UI Test dependencies configured for future UI tests

### Recommended next tests
- Compose UI tests for:
  - Slider interaction behavior
  - HEX rendering correctness
  - Copy button action feedback
- Integration test between Activity, ViewModel, and screen.

---

## 8) How to Run the Project

```bash
git clone <repository-url>
cd RGBColorPicker
./gradlew build
./gradlew test
```

Then run from Android Studio on an emulator/device.

---

## 9) Future Improvements

- Add a formal **domain layer** (`usecase`, domain models, repository contracts) when business logic grows.
- Add a **data layer** with local persistence (DataStore/Room) for saved palettes/history.
- Introduce **feature modularization** when new features are added (`:feature-colorpicker`, `:core-ui`, `:core-domain`).
- Add **Navigation Compose** with dedicated navigation graph once multi-screen flow exists.
- Expand testing with **Compose UI tests** and CI execution.
- Add CI/CD pipeline (lint, tests, static checks on pull requests).

---

## Architecture Notes for New Contributors

- This codebase intentionally keeps architecture lightweight for a small app.
- Current boundaries are enough for one feature and are designed to evolve incrementally.
- Do not introduce domain/data abstractions prematurely; add them when complexity justifies it.
