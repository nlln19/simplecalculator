# SimpleCalculator

A small calculator app built with **JavaFX**.  
It supports common arithmetic operations and evaluates expressions using **exp4j**.

## Features
- Basic operations: `+`, `-`, `×`, `÷`
- Brackets `(` `)` and decimal input
- Additional operators: power, roots, percent, π
- Keyboard input support

## Requirements
- **Java 17**
- For development/build: **Gradle Wrapper** is included (`gradlew`)

> Note: JavaFX is not bundled with the JDK. This repo uses Gradle to fetch JavaFX automatically.

---

## Run

### Run the GitHub Release (Windows)
If you downloaded the release ZIP from GitHub:
1. Unzip the file (e.g. `simplecalculator-1.0.0-windows-x64.zip`)
2. Run `run.bat` (double-click or via terminal)
> `java` must be available in your PATH

### Run from Source
```bash
./gradlew run
