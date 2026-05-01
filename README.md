# Internet Shop

## Требования

- Java 11+
- Maven 3.8+
- MySQL 8+
- Apache Tomcat 9

## JDBC

Параметры подключения находятся в [src/main/java/com/shop/config/DatabaseConfig.java](/root/java-project/src/main/java/com/shop/config/DatabaseConfig.java).

- `URL=jdbc:mysql://localhost:3306/internet_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
- `USERNAME=shop_user`
- `PASSWORD=change_me`

## Запуск в WSL / Linux

1. Установите MySQL и Tomcat:

```bash
apt update
apt install -y mysql-server tomcat9
```

2. Запустите MySQL:

```bash
service mysql start
```

3. Создайте базу и пользователя:

```bash
mysql -uroot -p
```

```sql
CREATE DATABASE IF NOT EXISTS internet_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'shop_user'@'localhost' IDENTIFIED BY 'change_me';
ALTER USER 'shop_user'@'localhost' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON internet_shop.* TO 'shop_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

4. Импортируйте таблицы и тестовые данные:

```bash
mysql -uroot -p internet_shop < database/init.sql
```

5. Проверьте подключение пользователя приложения:

```bash
mysql --user=shop_user --password=change_me internet_shop -e "SHOW TABLES;"
```

6. Соберите проект:

```bash
mvn clean package
```

7. Задеплойте WAR в Tomcat:

```bash
cp /root/java-project/target/internet-shop.war /var/lib/tomcat9/webapps/
service tomcat9 start
```

## Запуск в Windows

1. Запустите MySQL:

```powershell
net start MySQL80
```

2. Создайте базу и пользователя:

```powershell
mysql --user=root --password=root
```

```sql
CREATE DATABASE IF NOT EXISTS internet_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'shop_user'@'localhost' IDENTIFIED BY 'change_me';
ALTER USER 'shop_user'@'localhost' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON internet_shop.* TO 'shop_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

3. Импортируйте таблицы и тестовые данные:

```powershell
Get-Content .\database\init.sql | mysql --user=root --password=root internet_shop
mysql --user=shop_user --password=change_me internet_shop -e "SHOW TABLES;"
```

4. Соберите проект:

```powershell
mvn clean package
```

5. Задеплойте WAR в Tomcat. Используйте `startup.bat` и `shutdown.bat` из директории `tomcat/bin`:

```powershell
.\tomcat\bin\shutdown.bat
if (Test-Path "$env:CATALINA_HOME\webapps\internet-shop") { Remove-Item "$env:CATALINA_HOME\webapps\internet-shop" -Recurse -Force }
if (Test-Path "$env:CATALINA_HOME\webapps\internet-shop.war") { Remove-Item "$env:CATALINA_HOME\webapps\internet-shop.war" -Force }
Copy-Item .\target\internet-shop.war "$env:CATALINA_HOME\webapps\"
.\tomcat\bin\startup.bat
```

## URL

```text
http://localhost:8080/internet-shop/
http://localhost:8080/internet-shop/register
http://localhost:8080/internet-shop/products
```

## Полезные команды

```bash
service mysql status
service tomcat9 status
tail -f /var/log/tomcat9/catalina.out
```
