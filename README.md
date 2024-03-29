
# Описание
Портал для взаимодействия между студентами и преподавателями в университете. В университете есть различные предметы (Subject),
к которым прикрекплены преподаватели и группы студентов. Преподаватель имеет возможность
создавать задания (Task) для студентов. Студенты отправляют свои ответы на задания (TaskAnswer)
преподавателю и получают оценки (Rating). Студенты объединены в группы (Group). Если появляется новое 
задание, студенты группы уведомляются о нем с помощью RabbitMQ. Преподаватели оповещаются о появлении нового ответа на их задание.
# О проекте
Язык программирования: **Java**

Сборщик: **Maven**

Используемые технологии:

- **Spring Boot**
- **PostgreSql**
- **Hibernate**
- **FlyWay**
- **RabbitMQ**

**Настройки подключения к БД** находятся по пути: src/main/resources/application.properties. По умолчанию там выставлены настройки для подключения к локальной БД, которой можно изменить при необходимости.

Рабочая зона (src/main/java/ru.university.portal/) содержит 6 java каталогов:

- **config** - файлы конфигурации rabbitMQ;
- **controller** - набор классов контроллеров;
- **DTO** - объектов, для передачи данных;
- **model** - модели объектов Student, Teacher, Task, TaskAnswer, Rating, Subject и Group;
- **repository** - репозитории к моделям;
- **service** - имплементация репозиториев с дополнительными методами.
