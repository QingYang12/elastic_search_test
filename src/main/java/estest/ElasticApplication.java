package estest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("estest.dao.*")
public class ElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticApplication.class, args);
    }
}
