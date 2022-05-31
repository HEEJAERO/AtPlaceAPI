package capstone.atplace;

import lombok.Getter;

@Getter
public class PrivateInfo {
    private String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&page=1&size=10&query=";
    private String API_KEY = "KakaoAK bc6a29f2bfa0a8ccd02aeb329145a454";
}
