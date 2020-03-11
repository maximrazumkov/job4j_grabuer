package ru.job4j.parser;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.db.model.Vacancy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ParserSqlSite implements Function<String, List<Vacancy>> {

    private Timestamp lastRun;

    private static final Logger LOGGER = LogManager.getLogger(ParserSqlSite.class.getName());

    public ParserSqlSite(Timestamp lastRun) {
        this.lastRun = lastRun;
    }

    @Override
    public List<Vacancy> apply(String s) {
        Calendar calendar = Calendar.getInstance();
        List<Vacancy> vacancies = new LinkedList<>();
        if (lastRun == null) {
            calendar.set(calendar.get(Calendar.YEAR), 0, 1);
            lastRun = new Timestamp(calendar.getTimeInMillis());
        }
        try {
            Document doc = Jsoup.connect(s).get();
            Elements countElem = doc.select(".sort_options tbody tr td:nth-child(1) a:nth-last-child(1)");
            Integer count = Integer.valueOf(countElem.text());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy, hh:mm");
            for (int i = 1; i <= count; ++i) {
                doc = Jsoup.connect(s + "/" + i).get();
                Elements vacancyElems = doc.select(".forumTable tbody tr:not(:nth-child(1))");
                for (Element line : vacancyElems) {
                    Elements vacancyTitle = line.select("td:nth-child(2) a");
                    Elements vacancyDate = line.select("td:nth-last-child(1)");
                    String date = vacancyDate.text();
                    Timestamp parsedDate = "сегодня".equals(date.split(",")[0])
                            ? new Timestamp(System.currentTimeMillis())
                            : new Timestamp(dateFormat.parse(date).getTime());
                    if (lastRun.before(parsedDate)) {
                        String link = vacancyTitle.attr("href");
                        String title = vacancyTitle.text().toLowerCase();
                        if (title.matches(".*java.*") && !title.matches(".*script.*")) {
                            doc = Jsoup.connect(link).get();
                            String description = doc.select(".msgTable tbody tr:nth-child(2) td:nth-child(2).msgBody").text();
                            vacancies.add(new Vacancy(title, description, link));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("В процессе парсинга произошла ошибка: {}", e.getMessage());
            throw new RuntimeException("Не удалось распарсить сайт", e);
        }
        return vacancies;
    }
}
