# yClientsV1-SaaS — Spring Boot + PostgreSQL

## 🚀 Описание
SaaS-платформа для салонов красоты, аналог yClients.  
Поддерживает:  
- Фирмы (Company)  
- Мастера, клиенты, админы  
- Услуги и ресурсы (инструменты)  
- Перерывы и отпуска  
- Календарь с проверкой занятости  

## 🛠 Запуск
1. Установите **PostgreSQL 15+**
2. Создайте базу `yclientsv1`
3. Запустите `YclientsV1Application.java`
4. Откройте: `http://localhost:8080/api/appointments/master/1?date=2025-04-10`
5. Попробуйте создать запись на 14:00 — она **откажет**, потому что занята!

## 🔧 Технологии
- Java 25
- Spring Boot 3.3
- PostgreSQL
- JPA/Hibernate
- Lombok

## 📞 Поддержка
Если нужно добавить JWT, React-фронтенд или оплату — напишите:  
👉 “Добавь JWT” или “Сделай React-календарь”