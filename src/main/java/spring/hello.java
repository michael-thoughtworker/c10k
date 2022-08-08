package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;


@RestController

public class hello {

@Autowired
WebClient webClient;

    @GetMapping("/")
    public Resp index() throws URISyntaxException, IOException, InterruptedException {
        Mono<String> result = webClient.get().uri("https://deelay.me/500/https://testaad.free.beeceptor.com/my/api/path")
                .retrieve()
                .bodyToMono(String.class);
        String response = result.block();
        Resp resp = new Resp("success");
        return resp;
    }

}

class Resp{
    private final String result;

    public String getResult() {
        return result;
    }

    Resp(String result) {
        this.result = result;
    }
}