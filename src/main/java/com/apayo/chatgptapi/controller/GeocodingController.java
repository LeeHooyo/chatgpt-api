package com.apayo.chatgptapi.controller;

import com.apayo.chatgptapi.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String reverseGeocode(@RequestParam double lat, @RequestParam double lng) {

        return geocodingService.reverseGeocode(lat, lng);
    }
}
