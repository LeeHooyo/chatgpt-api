package com.apayo.chatgptapi.controller;

import com.apayo.chatgptapi.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/maps")
public class GeocodingController {

    private final GeocodingService geocodingService;

    @Autowired
    public GeocodingController(GeocodingService geocodingService) {

        this.geocodingService = geocodingService;
    }

    /*
    @GetMapping(value = "/rev-geo", produces = "text/plain;charset=UTF-8")
    public String reverseGeocoding(@RequestParam double lat, @RequestParam double lng) {

        try {
            return geocodingService.reverseGeocode(lat, lng);
        } catch (Exception e) {
            return "변환 중 오류가 발생하였습니다.";
        }
    }
     */

    @GetMapping(value = "/rev-geo", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reverseGeocode(@RequestParam double lat, @RequestParam double lng) {
        try {
            
            String result = geocodingService.reverseGeocode(lat, lng);
            return ResponseEntity.ok(result);
            
        } catch (RestClientException e) {
            
            // API 사용 중 네트워크 오류 발생
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing the request.");
            
        } catch (IllegalArgumentException e) {
            
            // 적절하지 않은 파라미터 사용
            return ResponseEntity.badRequest().body("Invalid request parameter(s).");
        }
    }
}
