1. Собираем прокт командой:
mvn clear package

2. Для запуска используется команды:
[Пример:]
java -jar TestProjectForAikam-1.0.0-jar-with-dependencies.jar search input.json output.json
java -jar TestProjectForAikam-1.0.0-jar-with-dependencies.jar stat input.json output.json

3. В файле application.properties должны быть определены поля для подключения к базе данных
db.url=jdbc:postgresql://127.0.0.1:5432/dbtest
db.user=postgres
db.pass=admin
