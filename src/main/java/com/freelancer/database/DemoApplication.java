package com.freelancer.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@RestController
@SpringBootApplication
public class DemoApplication {

    @RequestMapping("/demo")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
