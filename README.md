# Дипломная работа

Автоматизация покупки тура

### Начало работы

Откройте проект, в файле application.properties выберите тестируемую базу, закомментировав неиспользуемую

### Запуск контейнеров приложения и базы 

```
docker-compose up
```

### Запуск SUT

для postgresql
```
DB_URL=jdbc:postgresql://localhost:5432/app java -jar artifacts/aqa-shop.jar
```
для mysql
```
DB_URL=jdbc:mysql://localhost:3306/app java -jar artifacts/aqa-shop.jar
``` 

### Запуск Teстов 

# Запуск автоматических тестов
1. Склонировать проект **https://github.com/frantzev/diploma.git**
2. Перейти в папку _diploma/artifacts_
3. Выполнить команду `docker-compose up -d`
4. Выполнить команду `java -jar aqa-shop.jar`
5. В новом окне терминала запустить `java -jar -Dspring.profiles.active=postgres aqa-shop.jar`
6. В новом окне терминала перейти в папку _diploma/artifacts/gate-simulator_
7. Выполнить команду `npm start`
8. В новом окне терминала перейти в папку _diploma_
9. Выполнить команду `./gradlew clear test`
10. Выполнить команду после завершения шага 9 `./gradlew allureReport`

для postgresql
```
DB_URL=jdbc:postgresql://localhost:5432/app gradle test 
```

для mysql
```
DB_URL=jdbc:mysql://localhost:3306/app gradle test 
```

### Формирование allure отчета

1 раз выполнить 
```
gradle allureReport
```

после каждого прогона тестов
```
gradle allureServe
```
