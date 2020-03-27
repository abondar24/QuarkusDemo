package org.abondar.experimental.quarkusdemo.conf;

import io.quarkus.test.Mock;
import org.abondar.experimental.quarkusdemo.conf.TestQualifier;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Properties;

@Mock
@ApplicationScoped
public class SqlSessionFactoryProviderMock {

    @ConfigProperty(name = "quarkus.datasource.test.jdbc.url")
    private String testUrl;

    @ConfigProperty(name = "quarkus.datasource.test.username")
    private String testUsername;

    @ConfigProperty(name = "quarkus.datasource.test.password")
    private String testPassword;

    @ApplicationScoped
    @Produces
    @SessionFactoryProvider
    @TestQualifier
    public SqlSessionFactory produceFactory() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("configuration.xml");
        var properties = new Properties();

        properties.put("url", testUrl);
        properties.put("username", testUsername);
        properties.put("password", testPassword);
        return new SqlSessionFactoryBuilder().build(inputStream, "test", properties);

    }


}
