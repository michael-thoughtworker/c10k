package spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class main {

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("https://deelay.me/500/");
        Mono<String> result = webClient.get()
                .retrieve()
                .bodyToMono(String.class);
        String response = result.block();
        System.out.println(response);
        SpringApplication.run(main.class, args);
    }

}
