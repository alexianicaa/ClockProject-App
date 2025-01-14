# ClockProject App

## Description
The *ClockProject App* is an Android application designed to integrate seamlessly with the Raspberry Pi Pico-based clock system. The app offers functionalities such as clock management, calendar viewing, and a focus mode. It also features a quote-fetching service that retrieves motivational quotes from an external API, enriching the user's experience.

### Features:
- *Clock Management*: Navigate to a detailed clock interface.
- *Calendar Integration*: View and interact with calendar events.
- *Focus Mode*: Dedicated page to help users concentrate.
- *User Registration and Login*: Secure user management using shared preferences.
- *Quote Fetching Service*: Background service that retrieves quotes periodically from an external API.

---

## Components Used

### 1. *Foreground Services*
   - *QuoteFetchService*: Fetches motivational quotes periodically from an external API. This service operates in the foreground to keep the user updated without interruptions.

### 2. *Background Services*
   - *QuoteFetchService (dual role)*: Operates in the background when the app is not actively used to continue fetching quotes.

### 3. *Bound Services*
   - The quote service is bound to the main activity to allow real-time access to the latest quotes and authors.

### 4. *Intents*
   - Used extensively for navigation between activities such as:
     - MainPageActivity to ClockActivity.
     - MainPageActivity to CalendarActivity.
     - MainPageActivity to FocusActivity.
     - RegisterActivity to LoginActivity.

### 5. *Activities*
   - *MainPageActivity*: Acts as the dashboard, allowing users to access other app features.
   - *RegisterActivity*: Handles user registration with form validation.
   - *LoginActivity*: Facilitates user login.
   - *ClockActivity, CalendarActivity, FocusActivity*: Dedicated pages for respective functionalities.

### 6. *Shared Preferences*
   - Used in RegisterActivity to store user details (username, email, and password) securely.

### 7. *Usage of External APIs*
   - *Quotes API*: Fetches inspirational quotes in JSON format from https://quotes-api-self.vercel.app/quote.

### 8. *Notifications*
   - Displayed to inform users about the ongoing background service (quote fetching).

---

## Additional Details
- The app implements robust validation for user input during registration.
- The quote fetching service ensures efficient and timely retrieval of data using multi-threading.
- We addead a zip with the project in case it doesn't work

---

## GitHub Repository
[ClockProject GitHub Repository](https://github.com/AnaAdascalitei/Clock_Calendar_Project/tree/main)

---
