package ru.netology.helpers;


import org.aeonbits.owner.Config;

@Config.Sources({"file:application.properties"})
public interface AppConfig extends Config {
    @Key("spring.datasource.url")
    String dataBaseUrl();
}
