package com.campers.camfp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.campers.camfp.config.type.CampingType;
import com.campers.camfp.config.type.TableType;
import com.campers.camfp.dto.camp.CampDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.service.camp.CampService;


@SpringBootTest
@TestPropertySource(locations = "/application-API-KEY.properties")
public class CampAPITests {

	@Value("${camp-api}")
	private String API_KEY;

	@Autowired
	private CampService campService;

	@Test
	public void getCamp() {
		
        String api_key = API_KEY;
        
        //반복(총 몇개의 캠프 정보를 넣을지)
        IntStream.range(0, 100).forEach(i -> {
        	
	    	String requestHeaders = "";
	    	//페이지 결과 수(캠핑장 개수)
	    	int row = 10;
	    	//페이지 번호(무려 페이징 처리가 되어 있음)
	    	int pageNo = i;
	    	//OS 구분, 서비스명(별로 신경 안써도 될듯)
	    	String mobileOS = "WIN";
	    	String mobileApp = "camfp";
	    	//serviceKey(API KEY) -> 위에서 받아옴
	    	//응답메세지 형식(기본값이 XML이라 JSON형식 입력)
	    	String type = "json";
	    	
	    	requestHeaders += "?numOfRows="+row;
	    	requestHeaders += "&pageNo="+pageNo;
	    	requestHeaders += "&MobileOS="+mobileOS;
	    	requestHeaders += "&MobileApp="+mobileApp;
	    	requestHeaders += "&serviceKey="+api_key;
	    	requestHeaders += "&_type="+type;
	    	
	        String apiURL = "http://apis.data.go.kr/B551011/GoCamping/basedList" + requestHeaders;    // JSON 결과
	        String responseBody = get(apiURL);
	        System.out.println(responseBody);
	        
			double dValue = Math.random();
			int iValue = (int) (dValue * 10) + 1;
	        
	        try {
				JSONObject object = new JSONObject(responseBody);
				JSONObject response = object.getJSONObject("response").getJSONObject("body").getJSONObject("items");
				JSONArray camps = response.getJSONArray("item");
				for(int j = 0; j < camps.length(); j++) {
					
					JSONObject camp = camps.getJSONObject(j);
					System.out.println(camp);
					String data;
					String campType = camp.getString("induty").replace("야영장", "").trim();
					
					if (camp.getString("intro").length() > 495) {						
						data = camp.getString("intro").substring(0, 495);
						data = data+ "...";
					}else {						
						data = camp.getString("intro");
					}
					System.out.println(campType);
					CampDTO campDTO = CampDTO.builder()
									.member(Member.builder().mno(1L).build())
									.name(camp.getString("facltNm"))
									.thumbnail(camp.getString("firstImageUrl"))
									.country(camp.getString("doNm"))
									.campintroduce(data)
									.address(camp.getString("addr1") + camp.getString("addr2"))
									.heart(iValue)
									.camptype(campType)
									.build();
					campService.register(TableType.CAMP, campDTO);
				}
			} catch (JSONException e) {
				throw new RuntimeException("JSON 변환 실패", e);
			}
        });
	}

    private static String get(String apiUrl){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");

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
