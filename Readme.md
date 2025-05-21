# Инструкция по запуску проекта

## Технологии
- Java 21
- Spring Boot 3.x
- PostgreSQL 16.2
- Maven
- Docker

## Предварительные требования
1. Установленный Docker ([инструкция по установке](https://docs.docker.com/engine/install/))
2. Установленный Docker Compose
3. Git (для клонирования репозитория)

## Запуск проекта с помощью Docker

### 1. Клонирование репозитория
```bash
git clone https://github.com/abdimajidov/finTech.git
```

### 2. Сборка и запуск контейнеров
```bash
docker-compose up --build
```

### 3. Остановка контейнеров
```bash
docker-compose down
```

### 4. Остановка с удалением томов (данные БД будут удалены)
```bash
docker-compose down -v
```

## Доступ к сервисам
- Приложение: http://localhost:8282
- База данных:
    - Хост: `localhost`
    - Порт: `5433`
    - Пользователь: `postgres`
    - Пароль: `postgres`
    - Название БД: `postgres`

## Настройки окружения
Основные настройки можно изменить через переменные окружения в файле `docker-compose.yml`:

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
  - SPRING_DATASOURCE_USERNAME=postgres
  - SPRING_DATASOURCE_PASSWORD=postgres
  - SPRING_JPA_HIBERNATE_DDL_AUTO=create
```

## Логирование
Логи приложения сохраняются в папке `./log/finTech` на хосте.

## Работа с базой данных
Для подключения к БД извне Docker можно использовать:
- pgAdmin
- DBeaver
- IntelliJ IDEA Database Tools

Настройки подключения:
- Host: `localhost`
- Port: `5433`
- Database: `postgres`
- Username: `postgres`
- Password: `postgres`

## Дополнительные команды
- Просмотр логов приложения:
```bash
docker-compose logs app
```

- Просмотр логов БД:
```bash
docker-compose logs db
```

- Пересборка только приложения:
```bash
docker-compose build app
```

- Запуск в фоновом режиме:
```bash
docker-compose up -d
```

## Решение проблем
Если возникают ошибки подключения к БД:
1. Убедитесь, что порт 5433 не занят другим процессом
2. Проверьте, что контейнер с БД запущен:
```bash
docker-compose ps
```
3. Проверьте логи БД:
```bash
docker-compose logs db
```