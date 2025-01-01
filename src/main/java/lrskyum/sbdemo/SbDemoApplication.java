package lrskyum.sbdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@Slf4j
public class SbDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbDemoApplication.class, args);
    }
}
