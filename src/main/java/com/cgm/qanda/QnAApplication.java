package com.cgm.qanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"services.defaultRepository","com.cgm.qanda.dataaccessobject"})
@EntityScan("com.cgm.qanda.*")
public class QnAApplication {
  public static void main(String[] args) {
    SpringApplication.run(QnAApplication.class, args);
  }
}

