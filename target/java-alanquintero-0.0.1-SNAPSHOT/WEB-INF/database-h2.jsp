<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jdbc="http://www.springframework.org/schema/jdbc"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/jdbc
           http://www.springframework.org/schema/jdbc/spring-jdbc.xsd">

    <!-- H2 in-memory datasource -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property userName="driverClassName" value="org.h2.Driver"/>
        <property userName="url" value="jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"/>
        <property userName="username" value="sa"/>
        <property userName="password" value=""/>
    </bean>

</beans>
