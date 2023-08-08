package com.apayo.chatgptapi.controller;

import com.apayo.chatgptapi.dto.ChatGPTRequest;
import com.apayo.chatgptapi.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {

        System.out.println("Received prompt from frontend: " + prompt);

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);

        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    /*

        '치아'가 아프고 증상은 '치통' 이야. 어느 병원에 가야 해? 대답할 수 있는 병원은 다음과 같아.
        { 치과, 외과, 내과, 정형외과, 산부인과, 비뇨기과, 이비인후과, 안과, 피부과}
        대답의 형식은, '병원 종류' 만 정확히 단답형으로 말해줘.

        프론트에 반환하는 데이터 = (변환한 도로명 주소 + 검색 결과)

        위도 경도를 주면 reverse geolocation을 백에서 돌리면 되잖아.

     */
}
