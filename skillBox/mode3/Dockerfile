# базовый образ, содержащий Java 18
FROM openjdk:18-oracle

# рабочая директория приложения внутри контейнера
WORKDIR /app

# копирование JAR-файла (созданого gradle/build/bootJar) приложения в контейнер и переименование его
COPY build/libs/skillbox-third-task-0.0.1-SNAPSHOT-plain.jar contacts-app.jar

## определение переменной среды, по умолчанию будет запуск со свойством из properties - true
#ENV CREATE_ON_STARTUP=true

# команда для запуска приложения
CMD ["java", "-jar", "contacts-app.jar"]
