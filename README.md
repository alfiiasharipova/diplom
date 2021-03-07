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
