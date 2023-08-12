package com.apayo.chatgptapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/combined")
public class CombinedController {

    @Autowired
    private RestTemplate restTemplate;

    private final String botUrl = "http://localhost:8080/bot/chat";
    private final String geoUrl = "http://localhost:8080/maps/rev-geo";

    @GetMapping
    @Async("taskExecutor")
    public CompletableFuture<ResponseEntity<String>> getCombinedData(@RequestParam("prompt") String prompt,
                                                                     @RequestParam("latitude") double lat,
                                                                     @RequestParam("longitude") double lng) {
        try {
            // 사용할 URL과 파라미터들
            String botRequestUrl = botUrl + "?prompt=" + prompt;
            String geoRequestUrl = geoUrl + "?lat=" + lat + "&lng=" + lng;

            // 병렬처리
            CompletableFuture<String> botResponseFuture = CompletableFuture.supplyAsync(() ->
                    restTemplate.getForObject(botRequestUrl, String.class));

            CompletableFuture<String> addressFuture = CompletableFuture.supplyAsync(() ->
                    restTemplate.getForObject(geoRequestUrl, String.class));

            // 요청에 대한 처리가 끝나면 응답을 합친다.
            return CompletableFuture.allOf(botResponseFuture, addressFuture)
                    .thenApplyAsync(Void -> {
                        try {

                            String botResponse = botResponseFuture.get();
                            String address = addressFuture.get();
                            String combinedData = address + " " + botResponse;
                            return ResponseEntity.ok(combinedData);

                        } catch (Exception e) {

                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing the request.");
                        }
                    });
        } catch (Exception e) {

            // 별도로 발생할 수 있는 예외를 처리한다.
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred."));
        }
    }
}