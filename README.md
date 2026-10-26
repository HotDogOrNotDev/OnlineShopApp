# Доска объявлений

REST API площадки для перепродажи вещей.
Пользователи регистрируются, публикуют объявления с фотографиями, оставляют комментарии и меняют профиль. Администратор может править и удалять любые объявления и комментарии.

## Стек

- Java 17, Spring Boot 2.7
- Spring Security: HTTP Basic, BCrypt, роли `USER` и `ADMIN`
- Spring Data JPA, PostgreSQL, Liquibase
- MapStruct, Bean Validation
- Swagger UI (springdoc)

## Как запустить

Нужны JDK 17 и Docker.

```bash
docker compose up -d
mvn spring-boot:run
```

API: `http://localhost:8080`  
Swagger: `http://localhost:8080/swagger-ui/index.html`

Локальная база из `docker-compose.yml` уже совпадает со значениями по умолчанию: база `onlineShopAppDb`, пользователь и пароль `onlineShopApp`. Свои параметры задаются переменными `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AD_IMAGE_PATH`, `SERVER_PORT`.

Картинки объявлений пишутся в `./uploads/ads`. Аватары хранятся в базе.

## Что умеет API

| Метод | Путь | Кто |
| --- | --- | --- |
| POST | `/register`, `/login` | все |
| GET | `/ads` | все |
| GET | `/images/{id}` | все |
| POST, PATCH, DELETE | `/ads/**` | автор или администратор |
| GET, POST, PATCH, DELETE | `/ads/{id}/comments/**` | автор комментария или администратор |
| GET, PATCH | `/users/me` | текущий пользователь |
| POST | `/users/set_password` | текущий пользователь |
| GET | `/avatars/{id}` | авторизованный пользователь |

Список объявлений и картинки открыты без входа, чтобы лента работала до авторизации. Остальные запросы требуют заголовок `Authorization: Basic ...`.

## Как устроен код

`controller` принимает HTTP и отдаёт DTO. `service` держит правила доступа и транзакции. `db` — сущности и репозитории. `mapper` собирает DTO через MapStruct. Схема базы накатывается Liquibase из `src/main/resources/db.changelog`.

Роль при регистрации берётся из запроса. Пароль в базу попадает только как BCrypt-хеш.

## Проверка

```bash
mvn test
```

Тест контекста поднимается на H2 и не требует PostgreSQL.

## Авторы

- Денис Силюков (@ViajerodeRusia)
- Дмитрий Хань (@xpycteam88)
- Максим Максимов (@maximovmaxim100500)

Лицензия MIT, см. [LICENSE](LICENSE).
