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

## Команды SQL:

```sql
-- Sequence for ID generation
CREATE SEQUENCE entity_id_seq START 1000 INCREMENT 1;

-- USERS table
CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('entity_id_seq'),
    name VARCHAR(255),
    surname VARCHAR(255),
    patronym VARCHAR(255),
    phone_number VARCHAR(255),
    address VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    balance NUMERIC(19,2) NOT NULL DEFAULT 0,
    is_account_non_expired BOOLEAN,
    is_active BOOLEAN,
    is_credentials_non_expired BOOLEAN,
    is_enabled BOOLEAN,
    role_id BIGINT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role(id),
    CONSTRAINT fk_user_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- ROLE table
CREATE TABLE role (
    id BIGINT PRIMARY KEY DEFAULT nextval('entity_id_seq'),
    name VARCHAR(255) UNIQUE,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_role_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_role_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- PERMISSION table
CREATE TABLE permission (
    id BIGINT PRIMARY KEY DEFAULT nextval('entity_id_seq'),
    name VARCHAR(255) UNIQUE NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_permission_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_permission_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- ROLES_PERMISSIONS join table
CREATE TABLE roles_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_rp_role FOREIGN KEY (role_id) REFERENCES role(id),
    CONSTRAINT fk_rp_permission FOREIGN KEY (permission_id) REFERENCES permission(id)
);

-- TRANSACTION table
CREATE TABLE transaction (
    id BIGINT PRIMARY KEY DEFAULT nextval('entity_id_seq'),
    sender_id BIGINT,
    receiver_id BIGINT,
    amount NUMERIC(19,2) NOT NULL,
    description TEXT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_transaction_sender FOREIGN KEY (sender_id) REFERENCES users(id),
    CONSTRAINT fk_transaction_receiver FOREIGN KEY (receiver_id) REFERENCES users(id),
    CONSTRAINT fk_transaction_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_transaction_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);
```