//package org.abondar.experimental.quarkusdemo.conf;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.eclipse.microprofile.config.inject.ConfigProperty;
//import org.mybatis.cdi.SessionFactoryProvider;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.inject.Produces;
//import java.util.Properties;
//
//@ApplicationScoped
//public class SqlSessionFactoryProvider {
//
//
//    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
//    private String devUrl;
//
//    @ConfigProperty(name = "quarkus.datasource.username")
//    private String devUsername;
//
//    @ConfigProperty(name = "quarkus.datasource.password")
//    private String devPassword;
//
//
//    @ApplicationScoped
//    @Produces
//    @SessionFactoryProvider
//    @DevQualifier
//    public SqlSessionFactory produceFactory() {
//        var inputStream = getClass().getClassLoader().getResourceAsStream("configuration.xml");
//        var properties = new Properties();
//
//        properties.put("url", devUrl);
//        properties.put("username", devUsername);
//        properties.put("password", devPassword);
//        return new SqlSessionFactoryBuilder().build(inputStream, "dev", properties);
//    }
//
//
//}
