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

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);

        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    /*
    public ChatGPTRequest chat(@RequestParam("prompt") String prompt) {

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);

        return chatGPTResponse;
    }
     */

    // 주소는 http://localhost:8080/chat/bot?prompt=/ 와 같은 형태를 띌 것

    /*
        일단 코드 부분에 대해서 정리하자면, prompt 라는 string 타입의 query 파라미터를 받아오는 것.
        그래서 주소가 /bot?prompt= 이 된다.
        prompt에 아무 문장이나 할당하면 자동으로 = 뒤가 채워진다.
        public String chat으로 되어있는데, 리턴값이 String 타입이라는 뜻.
        String 타입으로 리턴을 하기 위해 출력값을 좀 가공할 필요가 있는데, 이는 코드 마지막 줄 참고.

        만약 JSON 형태로 리턴하고 싶다면 public ChatGPTResponse chat으로 바꿔주고,
        마지막 줄도 return chatGPTResponse; 로 수정해주면 된다.

        코드를 돌리기 위한 기초 절차 중 하나인데,
        https://platform.openai.com/ 에 방문해서 API key 발급받고 application.properties에서 수정하면 된다.
        나도 API key 중간에 한번 수정해서 지금 올라가있는 key로는 안될거야 아마.

        그리고 혹시나 이해 안될까봐 적어두는건데 API key 밑에 내가
        openai.api.url=https://api.openai.com/v1/chat/completions 라고 적은 부분 있을거야.
        그게 우리가 API를 이용해서 통신하는 url에 대한 부분이야. 없으면 안돼

        대부분의 오류 코드는 아마 500일텐데, 읽어보면 알겠지만 대부분 usage limit 문제이다.
        한번 가입하고 나면 5$ 정도의 free trial usage가 생기는데, 이게 적용이 좀 걸리는 걸로 앎.
        이 API 한번 불러올때마다 0.005$였나 그정도 밖에 안들더라. 암튼 적게 들었음.

        그 외 DTO Class들은 매우 간단하게 구현되어 있으니 보면 이해가 어렵진 않을거 같고
        아마 이정도만 해도 Controller 주요 부분은 다 설명 한거 같아.
        별도로 페이지를 구현하지 않았기 때문에 가급적이면 postman으로 돌려보는게 제일 좋을거 같아!

        그리고 프론트랑 백 연동 문제로 지우한테 잠깐 설명했던 부분인데, 우리가 문장 형식을 정해놨잖아?
        그걸 백에서 prompt에 고정해놓고 프론트에서 detail_part랑 증상만 넘겨줘서 그걸 다룰수도 있고,
        아예 프론트에서 넘겨줄 때 String 타입의 prompt라는 이름의 파라미터로 넘겨줘서
        그걸 백에서 받고 바로 돌리는 방법도 있어. 이 두 개 중에 더 좋은 방향을 택하는게 맞을거 같고
        우리가 다시 프론트로 넘겨줄 때도 JSON으로 넘겨줄지 아니면 String으로 text를 넘겨줄지 정해야 하는데
        지금 작성된 코드는 text를 넘겨주는거고 JSON으로 넘겨주는건 위에 내가 설명해둔거 보면 돼.
        내가 최하단에 따로 코드 넣어둘게.

     */

    /*
    public ChatGPTRequest chat(@RequestParam("prompt") String prompt) {

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);

        return chatGPTResponse;
    }
     */
}
