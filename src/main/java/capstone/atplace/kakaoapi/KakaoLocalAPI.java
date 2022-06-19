package capstone.atplace.kakaoapi;

import capstone.atplace.PrivateInfo;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@AllArgsConstructor
@Service
public class KakaoLocalAPI {
    static PrivateInfo info = new PrivateInfo();
    private static String API_URL =info.getAPI_URL();
    private static String API_KEY = info.getAPI_KEY();

    /*public static void main(String[] args) {
        String[] longitudes = {"127.423084873712", "127.423084873712", "127.423084873712", "127.423084873712"};
        String[] latitudes = {"37.0789561558879", "37.0789561558879", "37.0789561558879"};
        //System.out.println(getAddressInfoByAddress("경기도 군포시 금산로91 123동 1001호"));
       // System.out.println(getAddressInfoByCoordinate(findMidPoint(longitudes, latitudes)));
        //System.out.println(findMidPoint(longitudes, latitudes));
    }*/


    // 주소로 주소정보 불러오기
    public JSONObject getAddressInfoByAddress(String addressStr) {
        String addressInfo = new String();
        URL obj;
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            String address = URLEncoder.encode(addressStr, "UTF-8");

            obj = new URL(API_URL + address);
            addressInfo = requestToKakaoApi(obj);

            //response 객체를 출력해보자
            //System.out.println(response);
            //JSONParser jsonParser = new JSONParser();
            //JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(response));
            //JSONArray jsonArray = (JSONArray) jsonObject.get("documents");

           /* for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                System.out.println(object.get("address"));
            }*/
            System.out.println("addressInfo = " + addressInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringToJson(addressInfo);
    }
    // 좌표로 주소 정보 불러오기
    public JSONObject getAddressInfoByCoordinate(String coordinate) {
        String[] s = coordinate.split(" ");
        String x = s[0];
        String y = s[1];
        String addressInfo = new String();
        URL obj;
        try {
            obj = new URL( "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+x+"&y="+y);
            addressInfo = requestToKakaoApi(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringToJson(addressInfo);
    }
    //카테고리로 장소 검색
    public JSONObject getPlaceByCategory(String category,String x,String y)  {
        String addressInfo = new String();
        URL obj;
        //검색할 범위 ->반경 몇m?
        int radius = 2000;
        // 이후 추가할 부분: 사용자가 입력한 카테고리를 카카오에서 제공하는 카테고리 코드와 매칭하여 적절한 결과가 나오도록 추가
        // 검색결과 나오지 않으면 radius 를 증가시켜 결과값이 최소 1개 이상 나오도록 설정
        //카테고리 태그
        //FD6	음식점
        //CE7	카페
        //AT4	관광명소
        String tag = new String ();
        if(category.equals("데이트")){
            tag = "CE7";
        }else if(category.equals("미팅")){
            tag = "FD6";
        }else if(category.equals("여행")){
            tag = "AT4";
        }
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            obj = new URL("https://dapi.kakao.com/v2/local/search/category.json?"
                    +"y="+y+"&x="+x+"&category_group_code=" +tag + "&radius="+ radius);
            addressInfo = requestToKakaoApi(obj);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringToJson(addressInfo);
    }
    public JSONObject getPlaceByKeyword(String keyword) {
        String addressInfo = new String();
        URL obj;
        try {
            //인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있다.
            String address = URLEncoder.encode(keyword, "UTF-8");
            
            obj = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?query=" + address);
            System.out.println("keyword = " + keyword);
            addressInfo = requestToKakaoApi(obj);

            System.out.println("addressInfo = " + addressInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringToJson(addressInfo);
    }

    //kakaoAPI 에서 제공하는 형식에 맞는 쿼리의 형태로 url을 만드는 메소드
    private String requestToKakaoApi(URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //get으로 받아오면 된다. 자세한 사항은 카카오개발자센터에 나와있다.
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", API_KEY);
        con.setRequestProperty("content-type", "application/json");
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setDefaultUseCaches(false);
        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
        System.out.println(con);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String addressInfo = response.toString();
        return addressInfo;
    }
    //중간 좌표 찾기 -> 입력받은 좌표들로 중간좌표 찾기
    public String findMidPoint(List<String> longitudes, List<String> latitudes) {

        double x = 0f;
        double y = 0f;

        for (String longitude : longitudes) {
            x += Double.parseDouble(longitude);
            System.out.println(x);
        }
        x /= longitudes.size();
        for (String latitude : latitudes) {
            y += Double.parseDouble(latitude);
            System.out.println(y);
        }
        y /= latitudes.size();

        return String.format("%.12f", x) + " " + String.format("%.12f",y);
    }
    //String to Json
    public JSONObject stringToJson(String addressInfo) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object parse = jsonParser.parse(addressInfo);
            return (JSONObject) parse;
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("data", "데이터형식이 잘못되었습니다");
            return jsonObject;
        }
    }
}
