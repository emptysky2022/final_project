package com.campers.camfp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.campers.camfp.dto.item.ItemDTO;
import com.campers.camfp.service.item.ItemService;

@TestPropertySource(locations = "/application-API-KEY.properties")
@SpringBootTest
public class ItemAPITests {
	
	@Value("${naver-id}")
	private String CLIENT_ID;
	
	@Value("${naver-secret}")
	private String CLIENT_SECRET;

	@Autowired
	private ItemService itemService;

	@Test
	public void getItem() {
		
        String clientId = CLIENT_ID; //애플리케이션 클라이언트 아이디
        String clientSecret = CLIENT_SECRET; //애플리케이션 클라이언트 시크릿

        IntStream.range(0, 10).forEach(i -> {
        	String text = null;
        	try {
        		text = URLEncoder.encode("캠핑", "UTF-8");
        	} catch (UnsupportedEncodingException e) {
        		throw new RuntimeException("검색어 인코딩 실패",e);
        	}
        	
	        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=100&start=1+"+i*100;    // JSON 결과
	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("X-Naver-Client-Id", clientId);
	        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	        String responseBody = get(apiURL,requestHeaders);
	        System.out.println(responseBody);
	        
	        try {
				JSONObject object = new JSONObject(responseBody);
				JSONArray jarray = object.getJSONArray("items");
				for(int j = 0; j < jarray.length(); j++) {
					JSONObject items = jarray.getJSONObject(j);
					ItemDTO itemDTO = ItemDTO.builder()
									.mno(1L)
									.name(items.getString("title"))
									.thumbnail(items.getString("image"))
									.brand(items.getString("brand"))
									.maker(items.getString("maker"))
									.category1(items.getString("category3"))
									.category2(items.getString("category4"))
									.price(items.getInt("lprice"))
									.link(items.getString("link"))
									.type(i)
									.count(0)
									.build();
					itemService.register(itemDTO);
				}
			} catch (JSONException e) {
				throw new RuntimeException("JSON 변환 실패", e);
			}
        });
	}

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
