3.5 Homework Specification

Source: https://skyengpublic.notion.site/3-5-aaa05bcb8ab1470e9990df789be7c955

# 3.5. Потоки данных. Работа с файлами

> Привет! На связи домашнее задание урока 3.5. Потоки данных. Работа с файлами.

На прошлом уроке мы углубились в изучение SQL-запросов: попрактиковались с SELECT-запросами, а также настроили связи
OneToMany и ManyToOne.

Цель сегодняшнего домашнего задания — научиться работать с файлами (загружать их и отдавать в запросах) и со связями
OneToOne.

*Среднее время выполнения: 120 минут.*
>

**Шаг 1**

Создать модель Avatar. В ней будем хранить аватарки студентов. В модель добавить следующие поля: Long id, String
filePath, long fileSize, String mediaType, byte[] data, Student student.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dee928eb-31ed-47ff-9908-585276e39070/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dee928eb-31ed-47ff-9908-585276e39070/Рисунок41.png" width="40px" /> **Критерии оценки:** Создана модель с необходимыми полями. У модели есть аннотации: @Entity, @Id, @GeneratedValue.

</aside>

**Шаг 2**

Настроить связь OneToOne между моделями Student и Avatar. Для этого к полю student в модели Avatar добавить аннотацию
@OneToOne. Добавить контроллер, сервис и репозиторий для работы с моделью Avatar.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1969cc95-f5ec-4b24-999e-db0b70f974b2/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1969cc95-f5ec-4b24-999e-db0b70f974b2/Рисунок41.png" width="40px" /> **Критерии оценки:** Аннотация @OneToOne добавлена. После запуска приложения в таблице Avatar должна появиться колонка student_id. Созданы контроллер, сервис и репозиторий для модели Avatar.

</aside>

**Шаг 3**

Добавить три эндпоинта. Первый для загрузки картинки. При загрузке должно происходить сохранение данных как в БД, так и
на локальный диск. Второй эндпоинт должен возвращать картинку из БД. Третий должен возвращать картинку из директории.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/abada8b5-1958-43aa-88f7-751da7689a2d/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/abada8b5-1958-43aa-88f7-751da7689a2d/Рисунок41.png" width="40px" /> **Критерии оценки:** Созданы три эндпоинта. Первый позволяет загружать картинки как в БД, так и на локальный диск. Остальные два эндпоинта позволяют получать данные из БД или директории, где они хранятся. Все три эндпоинта должны работать корректно в Swagger Ui.

</aside>