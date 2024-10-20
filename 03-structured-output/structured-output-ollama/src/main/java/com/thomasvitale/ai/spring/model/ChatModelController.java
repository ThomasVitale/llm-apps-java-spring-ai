package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.ArtistInfo;
import com.thomasvitale.ai.spring.MusicQuestion;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModelService chatModelService;

    ChatModelController(ChatModelService chatModelService) {
        this.chatModelService = chatModelService;
    }

    @PostMapping("/chat/bean")
    ArtistInfo chatWithBeanOutput(@RequestBody MusicQuestion question) {
        return chatModelService.chatWithBeanOutput(question);
    }

    @PostMapping("/chat/map")
    Map<String,Object> chatWithMapOutput() {
        return chatModelService.chatWithMapOutput();
    }

    @PostMapping("/chat/list")
    List<String> chatWithListOutput(@RequestBody MusicQuestion question) {
        return chatModelService.chatWithListOutput(question);
    }

}
