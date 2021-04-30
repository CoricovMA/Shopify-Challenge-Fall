package org.challenge;

import org.challenge.database.Database;
import org.challenge.repo.FileHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        initServices();
        SpringApplication.run(App.class, args);
    }

    public static void initServices(){
        FileHandler.init();
        Database.init();
    }
}
