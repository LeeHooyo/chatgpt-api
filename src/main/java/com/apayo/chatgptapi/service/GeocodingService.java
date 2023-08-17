package com.apayo.chatgptapi.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Service;

@Service
public class GeocodingService {

    private final GeoApiContext geoApiContext;

    public GeocodingService() {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyAF4y3HVpHlJ5lT4k6rDufAmXKIKUvnLXw")
                .build();
    }

    /*
    public String reverseGeocode(double lat, double lng) throws Exception {

        GeocodingResult[] results =
                GeocodingApi.reverseGeocode(geoApiContext, new com.google.maps.model.LatLng(lat, lng))
                        .language("ko") // 출력 언어를 한글로 변환
                        .await();

        if (results.length > 0) {
            return results[0].formattedAddress;
        } else {
            return "주소를 찾을 수 없습니다.";
        }
    }

     */

    public String reverseGeocode(double latitude, double longitude) {
        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(geoApiContext,
                            new com.google.maps.model.LatLng(latitude, longitude))
                    .language("ko") // 언어 파라미터 추가하여 한국어로 요청
                    .await();

            if (results.length > 0) {
                StringBuilder addressBuilder = new StringBuilder();

                for (int i = 4; i >= 1; i--) {
                    addressBuilder.append(results[0].addressComponents[i].longName);
                    if (i < 6 && i > 1) {
                        addressBuilder.append(" "); // 요소 사이에 공백 추가
                    }
                }

                return addressBuilder.toString();
            } else {
                return "주소를 찾을 수 없습니다.";
            }
        } catch (Exception e) {
            return "주소 변환 중 오류가 발생했습니다.";
        }
    }
}
