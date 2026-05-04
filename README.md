Name: Perry Ehimuh  
EID: pee279  
Email: perryehimuh@gmail.com  

---

# Life.Church App
Elegant and modern Android experience for Life.Church

---

## Overview
The Life.Church App is a high-performance, unofficial Android client built with Jetpack Compose.  
It provides a seamless experience for engaging with the community, watching services, and managing your giving.

The app is currently in active development. Contributions for improvements and bug fixes are welcome.

---

## Highlights
- Jetpack Compose Native UI  
- Dynamic Giving Experience (with Google Pay integration)  
- Location-Aware Campus Selection  
- Dark & Light Theme Support  
- Material 3 Design  
- Interactive Calendar for scheduling gifts  
- Real-time Campus Distance Calculation  
- Global & Local Missions Support  

---

## Screenshots

| Prayer Page | Giving Screen | Campus Selection |
|----------|-------------|-----------------|
| ![Home](images/prayer.png) | ![Giving](images/gpay.png) | ![Campuses](images/campus_selector.png) |

---

## Features

### Smart Giving
Powered by Google Pay, the giving experience is designed to be frictionless.  
Whether it’s a one-time gift or a scheduled tithe, the app handles the complexity so you don't have to.

---

### Campus Locator
The app uses your GPS location (optional) to suggest the Life.Church campus closest to you.  
If you are traveling or out of state, it intelligently defaults to Life.Church Online.

---

### Giving Funds
Easily direct your generosity toward specific areas:
- **Tithe:** The first 10%  
- **New Locations:** Fueling growth  
- **Local & Global Missions:** Crisis relief  
- **YouVersion:** Sharing the Bible everywhere  

---

### Modern Navigation
A robust navigation system based on the web app’s route model, ensuring a consistent experience across platforms.

---

## Development

### Prerequisites
- Android Studio Iguana or newer  
- JDK 17  
- Android device or emulator with Google Play Services (for Google Pay testing)  

---

### Run
1. Clone the repository  
2. Open the project in Android Studio  
3. Sync Gradle  
4. Click **Run 'app'**  

---

### Build
To generate an APK or App Bundle:

```bash
./gradlew assembleDebug
```

---

## Technical Architecture
- **Language:** 100% Kotlin  
- **UI Framework:** Jetpack Compose (Material 3)  
- **Navigation:** Compose Navigation  
- **Image Loading:** Coil  
- **Payments:** Google Pay API (GMS Wallet)  
- **Location:** Google Play Services Location / LocationManager  

---

## Project Structure
- `ui/giving/` — Giving flows and payment logic  
- `ui/navigation/` — Route definitions and NavHost  
- `ui/theme/` — Material 3 design tokens and colors  

---

## FAQ

#### How does the giving process work?The app uses the **Google Pay API** (via `play-services-wallet`) to ensure your financial data never touches our servers directly. When you initiate a gift, the app requests a secure payment token from Google. This token is then passed to our processing provider, ensuring a high-security, encrypted transaction that supports credit cards, debit cards, and saved Google account methods.

#### How is my "Current Location" determined?
The app uses a hybrid location strategy to find your closest campus:
1. **GPS/Fine Location**: If granted, we use high-accuracy GPS.
2. **Network/Coarse Location**: A fallback that uses cell towers and Wi-Fi for lower battery impact.
3. **Smart Defaulting**: If you are outside our campus regions (e.g., testing from California), the app automatically defaults to **Life.Church Online** to ensure you can still participate in the community from anywhere.

#### What happens if the payment sheet fails to open?
Our giving engine includes robust error handling for system-level issues:
- **Resource Management**: The app monitors for system resource exhaustion (like low device storage) which can sometimes cause Google Play Services to disconnect.
- **Automatic Recovery**: In the event of a "DeadObjectException" (a common system communication error), the app attempts to re-initialize the connection automatically so you don't have to restart the app.

#### Is this an official Life.Church product?
This is an unofficial, high-performance client built to demonstrate modern Android development practices using Jetpack Compose. While it mirrors the functionality of the official site, it is a separate project focused on speed and native UI/UX.

#### Can I schedule recurring gifts?
Yes! The app features a custom-built **Interactive Calendar** in the Giving section. You can select specific dates, and the UI will dynamically update the "Process Date" to show exactly when your gift will be initiated.

---

## Maintainers
- Perry (Lead Developer)

---

## Disclaimer
Life.Church App is a third-party application and is not officially affiliated with Life.Church Operations, LLC.
