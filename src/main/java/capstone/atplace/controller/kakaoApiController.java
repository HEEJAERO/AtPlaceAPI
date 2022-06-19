package capstone.atplace.controller;

import capstone.atplace.form.CoordinateForm;
import capstone.atplace.kakaoapi.KakaoLocalAPI;
import capstone.atplace.response.Result;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class kakaoApiController {
    private final KakaoLocalAPI kakaoLocalAPI;
    @GetMapping("/address")
    public Result recommend(@ModelAttribute CoordinateForm coordinateForm) {
        JSONObject addressinfo = kakaoLocalAPI.getAddressInfoByCoordinate(coordinateForm.getX() + " " + coordinateForm.getY());
        return new Result(true,addressinfo);
    }
    @GetMapping("/mid-point")
    public Result midPoint(@RequestParam List<String> x, @RequestParam List<String> y){
        String midPoint = kakaoLocalAPI.findMidPoint(x, y);
        //JSONObject addressInfoByCoordinate = kakaoLocalAPI.getAddressInfoByCoordinate(midPoint);
        String[] s = midPoint.split(" ");
        HashMap<String,Double> coordinate = new HashMap<>();
        Double a = Double.parseDouble(s[0]);
        Double b = Double.parseDouble(s[1]);

        coordinate.put("x",a);
        coordinate.put("y",b);
        return new Result(true, coordinate);
    }
    @GetMapping("/addresses")
    public Result getCoordinateByAddresses(@RequestParam List<String> addresses) {
        //JSONObject coordinateByAddress = kakaoLocalAPI.getCoordinateByAddress(address);
        Double sumX = Double.valueOf(0);
        Double sumY = Double.valueOf(0);

        for(String address:addresses) {
            JSONObject address1 = kakaoLocalAPI.getAddressInfoByAddress(address);
            JSONArray documents = (JSONArray) address1.get("documents");
            JSONObject jsonObject = (JSONObject) documents.get(0);
            String x = (String) jsonObject.get("x");
            String y = (String) jsonObject.get("y");
            System.out.println("x = " + x);
            System.out.println("y = " + y);
            sumX += Double.parseDouble(x);
            sumY += Double.parseDouble(y);
        }
        HashMap<String,Double> coordinate = new HashMap<>();
        coordinate.put("x",sumX/addresses.size());
        coordinate.put("y",sumY/addresses.size());
        return new Result(true,coordinate );
    }
    @GetMapping("/searching")
    public Result getPlacesBySearching(@RequestParam String keyword){
        JSONObject placeByKeyword = kakaoLocalAPI.getPlaceByKeyword(keyword);
        return new Result(true, placeByKeyword);
    }
    @GetMapping("/recommend")
    public Result getCoordinateByLocations(@RequestParam String category, @RequestParam String x,@RequestParam String y){
        JSONObject placeByCategory = kakaoLocalAPI.getPlaceByCategory(category, x, y);
        return new Result(true,placeByCategory);
    }

}
