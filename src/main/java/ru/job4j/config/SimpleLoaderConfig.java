package ru.job4j.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;
import java.util.function.Function;

public class SimpleLoaderConfig implements Function<String, Config> {

    private static final Logger LOGGER = LogManager.getLogger(SimpleLoaderConfig.class.getName());

    @Override
    public Config apply(String s) {
        try (InputStream is = SimpleLoaderConfig.class.getClassLoader().getResourceAsStream(s)) {
            Properties properties = new Properties();
            properties.load(is);
            return new Config(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password"),
                    properties.getProperty("jdbc.driver"),
                    properties.getProperty("cron.time")
            );
        } catch (Exception e) {
            LOGGER.error("В процессе чтения файла с настройками произошла ошибка {}", e.getMessage());
            throw new RuntimeException("Не удалось прочитать настройки", e);
        }
    }
}
