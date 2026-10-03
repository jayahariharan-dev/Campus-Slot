# CAMPUS SLOT 🎓

### College Smartboard Booking & Conflict Detection System

CAMPUSSLOT is a Java-based desktop application that enables college staff to efficiently book and manage smartboards while preventing scheduling conflicts.

## ✨ Features

- 📅 Period-based smartboard booking across **4 floors and 8 smartboards**
- 🚫 Automatic **conflict detection** for occupied slots
- 📝 **Waitlist management** for booked periods
- ❌ Booking cancellation with waitlist notifications
- 📱 **Telegram integration** for staff registration and notifications
- 🔔 Automated **15-minute and exact-time booking reminders**
- 🗄️ PostgreSQL-based persistent booking and resource management

## 🛠️ Tech Stack

**Java 21** • **Java Swing** • **PostgreSQL** • **JDBC** • **Telegram Bot API**

## 🏗️ Architecture

The application follows a **DAO-based layered architecture** separating the UI, business logic, database operations, and Telegram services.

## 📂 Core Modules

- `ui` — Application interface and booking screens
- `dao` — Database access and booking operations
- `model` — Data models
- `database` — PostgreSQL connection
- `telegram` — Registration and notification services

## 🔐 Security

Sensitive credentials such as the **Telegram Bot Token and database password are loaded through environment variables** and are not stored in the source code.

## 👨‍💻 Project

**CAMPUSSLOT — College Smartboard Booking & Conflict Detection System**

Built as a college project to simplify smartboard resource management and eliminate booking conflicts.
