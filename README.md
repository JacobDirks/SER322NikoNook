# Database Setup Instructions
1. Open a terminal and startup MySQL from this directory
2. Run: ```SOURCE ./creationScript.sql```
3. Run: ```SOURCE ./dataInputScript.sql```

# Compile Instructions
~~~
javac -cp libs/mysql-connector-j-9.5.0.jar -d out src/*.java
~~~

# Run Instructions
To run NikoNook (On Mac):
~~~
java -cp "out:libs/mysql-connector-j-9.5.0.jar" Client <url> <user> <pwd> <driver>
~~~

To run NikoNook (On Windows):
~~~
java -cp "out;libs/mysql-connector-j-9.5.0.jar" Client <url> <user> <pwd> <driver>
~~~

Example (Mac Syntax):
~~~
java -cp "out:libs/mysql-connector-j-9.5.0.jar" Client 'jdbc:mysql://localhost:3306/NIKONOOK?autoReconnect=true&useSSL=false' root password com.mysql.cj.jdbc.Driver
~~~