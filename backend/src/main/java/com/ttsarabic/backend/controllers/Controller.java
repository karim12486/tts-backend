package com.ttsarabic.backend.controllers;

import jdk.jfr.ContentType;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


record TTSRequest(String buckw, float rate, float denoise) {
}
record TTSDTO(String text) {
}


@RestController
@RequestMapping("/api")
public class Controller {
    private final RestTemplate restTemplate = new RestTemplate();

    @CrossOrigin
    //@RequestBody String text
    @PostMapping(path = "/tts")
    public ResponseEntity<?> tts(@RequestBody TTSDTO text) throws IOException {
        String url = "http://localhost:8000/api/tts";
        TTSRequest ttsRequest = new TTSRequest(text.text(), 1f, 0.01f);
        restTemplate.postForObject(url, ttsRequest, List.class);
        byte[] audio = Files.readAllBytes(Path.of(
                "C:\\Users\\Administrator\\Downloads\\tts-arabic-pytorch\\tts-arabic-pytorch\\app\\static\\wave0.wav"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(audio);
    }
}
