package com.gerivansantos.azureonfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    @Autowired
    private AzureConfiguration azureConfiguration;

    @GetMapping
    public void getMessage() {
        Map<String, String> env = azureConfiguration.getAllVariablesEnviroment();
        System.out.println(env.get("PORT"));
    }
}
