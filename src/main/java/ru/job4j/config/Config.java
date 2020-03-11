package ru.job4j.config;

public class Config {
    private String urlDb;
    private String urlLogin;
    private String urlPassword;
    private String driver;
    private String time;

    public Config(String urlDb, String urlLogin, String urlPassword, String driver, String time) {
        this.urlDb = urlDb;
        this.urlLogin = urlLogin;
        this.urlPassword = urlPassword;
        this.driver = driver;
        this.time = time;
    }

    public String getUrlDb() {
        return urlDb;
    }

    public void setUrlDb(String urlDb) {
        this.urlDb = urlDb;
    }

    public String getUrlLogin() {
        return urlLogin;
    }

    public void setUrlLogin(String urlLogin) {
        this.urlLogin = urlLogin;
    }

    public String getUrlPassword() {
        return urlPassword;
    }

    public void setUrlPassword(String urlPassword) {
        this.urlPassword = urlPassword;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
