package org.abondar.experimental.quarkusdemo.service;


import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class DemoService {

    public String generateHello(String name) {
        return "Hello " + name;
    }
}
