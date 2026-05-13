package dev.migdaleder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HackerNewsScraper
 *
 * A simple web scraper that fetches the top stories from Hacker News.
 * Built with Jsoup for HTML parsing.
 *
 * Author: Éder Pereira de Castro
 * GitHub: https://github.com/Migdal-Eder
 */
public class App {

    // --- Inner class to represent a single news story ---
    static class Story {
        int    rank;
        String title;
        String url;
        int    points;
        int    comments;

        Story(int rank, String title, String url, int points, int comments) {
            this.rank     = rank;
            this.title    = title;
            this.url      = url;
            this.points   = points;
            this.comments = comments;
        }

        @Override
        public String toString() {
            return String.format(
                "[%2d] %s%n     %s%n     ▲ %d points | 💬 %d comments%n",
                rank, title, url, points, comments
            );
        }
    }

    // --- Scraping logic ---
    public static List<Story> scrapeTopStories(int maxStories) throws IOException {
        List<Story> stories = new ArrayList<>();

        System.out.println("Connecting to Hacker News...");

        Document doc = Jsoup.connect("https://news.ycombinator.com/")
                .userAgent("Mozilla/5.0 (compatible; Java-Scraper/1.0)")
                .timeout(10_000)   // 10 seconds timeout
                .get();

        // Each story row has class "athing"
        Elements storyRows = doc.select("tr.athing");

        for (Element storyRow : storyRows) {
            if (stories.size() >= maxStories) break;

            // --- Rank ---
            Element rankEl = storyRow.selectFirst("span.rank");
            if (rankEl == null) continue;
            String rankText = rankEl.text().replace(".", "").trim();
            int rank;
            try {
                rank = Integer.parseInt(rankText);
            } catch (NumberFormatException e) {
                continue;
            }

            // --- Title and URL ---
            Element titleEl = storyRow.selectFirst("span.titleline > a");
            if (titleEl == null) continue;
            String title = titleEl.text();
            String url   = titleEl.attr("href");

            // Some links are relative (e.g. Ask HN posts)
            if (url.startsWith("item?")) {
                url = "https://news.ycombinator.com/" + url;
            }

            // --- Points and Comments (in the next sibling row) ---
            Element subRow = storyRow.nextElementSibling();
            int points   = 0;
            int comments = 0;

            if (subRow != null) {
                Element scoreEl = subRow.selectFirst("span.score");
                if (scoreEl != null) {
                    points = Integer.parseInt(
                        scoreEl.text().replace(" points", "").replace(" point", "").trim()
                    );
                }

                // The last link in the subtext row is the comments link
                Elements links = subRow.select("a");
                if (!links.isEmpty()) {
                    Element lastLink = links.last();
                    String linkText  = lastLink.text();
                    if (linkText.contains("comment")) {
                        comments = Integer.parseInt(
                            linkText.replace("\u00a0comments", "")
                                    .replace("\u00a0comment", "")
                                    .trim()
                        );
                    }
                }
            }

            stories.add(new Story(rank, title, url, points, comments));
        }

        return stories;
    }

    // --- Entry point ---
    public static void main(String[] args) {
        int maxStories = 10; // Change this to scrape more stories

        try {
            List<Story> stories = scrapeTopStories(maxStories);

            System.out.println("\n========================================");
            System.out.println("  🔥 Hacker News — Top " + stories.size() + " Stories");
            System.out.println("========================================\n");

            for (Story s : stories) {
                System.out.println(s);
            }

            System.out.println("Scraped " + stories.size() + " stories successfully.");

        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }
}
