

## **GitHub-Optimized README for CompassApp**

```markdown
# CompassApp 🧭
  **A Modern, Accurate Compass App built with Kotlin + Jetpack Compose**


---

## ✨ Features

- 🧭 **Real-time Compass** – Accurate readings with sensor fusion  
- 🎨 **Modern UI** – Gradient + glassmorphism  
- 🎯 **High Accuracy** – Accelerometer + magnetometer  
- 🔄 **Smooth Animations** – Needle & transitions  
- 🌐 **Dual Modes** – Magnetic & True North  
- 📊 **Live Data** – Bearing & accuracy display  
- 🛠 **Calibration Support**  

---

## 🛠 Tech Stack

- **Kotlin** + **Jetpack Compose**  
- **MVVM** + Repository Pattern  
- **Android Sensor API**  
- **Coroutines** + **StateFlow**  
- **Custom Canvas Drawing**  

---

## 📂 Project Structure

```

data/         # Repository & sensor handling
domain/       # Models & use cases
presentation/ # UI & ViewModel
di/           # Dependency injection
MainActivity.kt

````

---

## 🚀 Getting Started

```bash
git clone https://github.com/yourusername/CompassApp.git
cd CompassApp
````

* Open in **Android Studio** (API 24+, Kotlin 1.9+)
* Run on device/emulator

---

## 🎯 Key Code

**Sensor Fusion**

```kotlin
val success = SensorManager.getRotationMatrix(
    rotationMatrix, null, accelerometerReading, magnetometerReading
)
val azimuth = ((Math.toDegrees(orientationAngles[0].toDouble()) + 360) % 360).toFloat()
```

**Custom Compass Needle**

 <img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/7d780f49-03cc-403d-be2d-e0f0f7a31256" />

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/3148dbff-870d-42c1-bd2f-6cc15140293b" />

```kotlin
Canvas(Modifier.size(280.dp)) {
    rotate(degrees = animatedBearing, pivot = center) {
        drawCompassNeedle(center, radius)
    }
}
```

---

## 📈 Future Plans

* GPS integration for True North
* Light/Dark themes
* Offline maps
* Home screen widget

---

## 👨‍💻 Author

**Shubham** – Android Developer
[LinkedIn](https://www.linkedin.com/in/shubham-khandeparker-448238359/) • [Twitter](https://x.com/shubhamsk0424)

---

**⭐ Star this repo if you find it useful!**

````

---

### **About Adding Animation**
Yes — GitHub supports **animated GIFs** in READMEs, which is the easiest way to add animation.  

**How to do it:**
1. Record your screen (e.g., with OBS, ScreenToGif, or LiceCap).  
2. Save/export it as a **GIF**.  
3. Place the GIF in your repo’s `/screenshots` folder.  
4. Reference it in your README like this:  
   ```markdown
   <img src="screenshots/compass_demo.gif" alt="Compass Demo" width="300"/>
````

The GIF will auto-play when the README is viewed on GitHub.

---

