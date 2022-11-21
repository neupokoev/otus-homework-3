<h1 align="center">OTUS курс для продвинутых тестировщиков</h1>
<h3 align="left">Домашняя работа №3</h3>

---
Проект содержит 4 теста, реализующих задание 3 домашки.\
Для запуска тестов необходимо выполнить несколько шагов:
1. Скачать данный репозиторий, запустив команду в терминале
```bash
git clone https://github.com/neupokoev/otus-homework-3.git
```
2. Перейти в каталог `otus-homework-3`
```bash
cd otus-homework-3
```
3. Запустить тесты
```bash
mvn clean -Dbase.url="https://petstore.swagger.io/v2" test
```
Тест кейсы находятся в репозиторий в файле [tests.xlsx](https://github.com/neupokoev/otus-homework-3/blob/main/src/test/resources/tests.xlsx) 