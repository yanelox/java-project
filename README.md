# Internet Shop

## Требования

- Java 11+
- Maven 3.8+
- MySQL 8+
- Apache Tomcat 9

## MySQL

```bash
apt update
apt install -y mysql-server
service mysql start
mysql -uroot
```

```sql
CREATE DATABASE IF NOT EXISTS internet_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'shop_user'@'localhost' IDENTIFIED BY 'shop_pass_123';
GRANT ALL PRIVILEGES ON internet_shop.* TO 'shop_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

```bash
cd /root/java-project
mysql -uroot internet_shop < database/init.sql
mysql -ushop_user -pshop_pass_123 -e "USE internet_shop; SHOW TABLES;"
```

## Web

```bash
apt update
apt install -y tomcat9
cd /root/java-project
mvn clean package
mkdir -p /var/lib/tomcat9/bin
cat > /var/lib/tomcat9/bin/setenv.sh <<'EOF'
export DB_URL="jdbc:mysql://localhost:3306/internet_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
export DB_USERNAME="shop_user"
export DB_PASSWORD="shop_pass_123"
EOF
chmod +x /var/lib/tomcat9/bin/setenv.sh
service tomcat9 stop
rm -rf /var/lib/tomcat9/webapps/internet-shop
rm -f /var/lib/tomcat9/webapps/internet-shop.war
cp /root/java-project/target/internet-shop.war /var/lib/tomcat9/webapps/
service tomcat9 start
```

## URL

```text
http://localhost:8080/internet-shop/
http://localhost:8080/internet-shop/register
http://localhost:8080/internet-shop/products
```
