# 📰 Daily News Aggregator

A Spring Boot application that automatically collects news articles from selected sources on a schedule and stores them in a database for further analysis or display.

---

## ⚙️ Tech Stack
- ☕ **Java 17+**
- 🚀 **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Scheduler
- 🗄️ **MySQL / PostgreSQL**
- 🧩 **Jsoup** — HTML parser for web scraping
- 🧱 **Liquibase** — database migration management

---

## 🧠 Overview
The application periodically (via a built-in scheduler) fetches the latest articles from a configured news website, parses the content using **Jsoup**, and saves structured news entries (title, description, date) to the SQL database.

Each record contains:
- 🗞️ Title  
- 🧾 Description  
- ⏰ Publication date  
- 🔗 Source URL  

---

## 🕒 Scheduler Logic
The scheduler runs automatically based on the configured CRON expression (default — every hour):
```java
@Scheduled(cron = "0 0 * * * *")
