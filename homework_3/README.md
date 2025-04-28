# User Service - консольное CRUD приложение с Hibernate и PostgreSQL

#### Описание проекта
Консольное приложение user-service разработано на Java с использованием Hibernate в качестве ORM для взаимодействия с PostgreSQL. Приложение предоставляет базовые CRUD-операции (Create, Read, Update, Delete) для сущности User через консольный интерфейс.

#### Требования
+ Java 17
+ Maven 3.6+
+ PostgreSQL 9.5+

#### Функциональные возможности
+ Создание нового пользователя
+ Просмотр информации о пользователе по ID
+ Просмотр списка всех пользователей
+ Обновление данных пользователя
+ Удаление пользователя

#### Сущность User
Приложение работает с сущностью User, которая содержит следующие поля:
+ id - уникальный идентификатор
+ name - имя пользователя
+ email - электронная почта (уникальное поле)
+ age - возраст
+ created_at - дата и время создания (устанавливается автоматически)

#### Технические особенности
+ Hibernate в качестве ORM
+ PostgreSQL как СУБД
+ Настройка Hibernate через hibernate.cfg.xml
+ DAO-паттерн для отделения логики работы с БД
+ Транзакционность операций
+ Логирование с помощью Log4j2
+ Управление зависимостями через Maven
+ Обработка исключений Hibernate и PostgreSQL

#### Логирование
Приложение ведет логи в файл logs/user-service.log и выводит их в консоль. Уровень логирования можно настроить в файле src/main/resources/log4j2.xml.

#### Установка и запуск:

1. Клонируйте репозиторий:

`git clone https://github.com/ваш-репозиторий/user-service.git`

`cd user-service`

2. Создайте базу данных в PostgreSQL:

`CREATE DATABASE user_db;`

3. Настройте подключение к БД в файле `src/main/resources/hibernate.cfg.xml`:

```java
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/user_db</property>
<property name="hibernate.connection.username">ваш_пользователь</property>
<property name="hibernate.connection.password">ваш_пароль</property>
```

4. Соберите проект:

`mvn clean package`

5. Запустите приложение:

`java -jar target/user-service-1.0-SNAPSHOT.jar`

#### Использование
После запуска приложения откроется консольное меню с возможностью выбора операций:

```java
Меню User Service:
1. Создать пользователя
2. Найти пользователя по ID
3. Показать всех пользователей
4. Обновить пользователя
5. Удалить пользователя
6. Выход
Выберите действие:
```