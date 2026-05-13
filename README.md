# webscraper-hn
Hacker News Web Scraper (Java)  A simple web scraper built in Java using [Jsoup](https://jsoup.org/), fetching the top stories from [Hacker News](https://news.ycombinator.com/).  This is my first Java project — part of my transition into software development, coming from a background in IT operations, Python scripting, and automation.

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 17 | Language |
| Maven | Build & dependency management |
| Jsoup 1.17 | HTML parsing / scraping |

## How to Run

**Requirements:** Java 17+, Maven 3.x

```bash
# Clone the repo
git clone https://github.com/Migdal-Eder/webscraper-hn.git
cd webscraper-hn

# Build
mvn package -q

# Run
java -jar target/webscraper-hn-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## 📋 Sample Output

```
Connecting to Hacker News...

========================================
 Hacker News — Top 10 Stories
========================================

[ 1] Some Interesting Tech Article
     https://example.com/article
     ▲ 512 points | 💬 143 comments

[ 2] ...
```

##  What I Learned

- Java OOP basics: classes, methods, inner classes
- Maven project structure and dependency management
- HTTP requests and HTML parsing with Jsoup
- CSS selectors for DOM navigation
- Building a runnable fat JAR

##  Ideas for Improvement

- [ ] Export results to CSV or JSON
- [ ] Filter stories by keyword
- [ ] Schedule periodic scraping (cron / Java Timer)
- [ ] Add pagination (scrape multiple pages)
- [ ] Unit tests with JUnit 5

##  Author

**Éder Pereira de Castro** — [GitHub](https://github.com/Migdal-Eder) | [LinkedIn](#)

---
*Built on Arch Linux, VSCode, OpenJDK 17.*
