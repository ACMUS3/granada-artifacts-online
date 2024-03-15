package io.acmus.granadaartifactsonline;

import io.acmus.granadaartifactsonline.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GranadaArtifactsOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GranadaArtifactsOnlineApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);

    }

}
