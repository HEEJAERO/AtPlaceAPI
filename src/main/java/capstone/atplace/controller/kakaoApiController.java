package capstone.atplace.controller;

import capstone.atplace.form.CoordinateForm;
import capstone.atplace.kakaoapi.KakaoLocalAPI;
import capstone.atplace.response.Result;
import capstone.atplace.response.Result2;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/mid-point")
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
    @PostMapping("/addresses")
    public Result2 getCoordinateByAddresses(@RequestParam String place1,@RequestParam String place2) {

        JSONObject address1 = kakaoLocalAPI.getAddressInfoByAddress(place1);
        JSONObject address2 = kakaoLocalAPI.getAddressInfoByAddress(place2);

        JSONArray documents1 = (JSONArray) address1.get("documents");
        JSONObject jsonObject1 = (JSONObject) documents1.get(0);
        Double place1X = Double.parseDouble((String) jsonObject1.get("x"));
        Double place1Y = Double.parseDouble((String) jsonObject1.get("y"));

        JSONArray documents2 = (JSONArray) address2.get("documents");
        JSONObject jsonObject2 = (JSONObject) documents2.get(0);
        Double place2X = Double.parseDouble((String) jsonObject2.get("x"));
        Double place2Y = Double.parseDouble((String) jsonObject2.get("y"));

        Double  x= (place1X+place2X)/2;
        Double  y= (place1Y+place2Y)/2;

        JSONObject address = kakaoLocalAPI.getAddressInfoByCoordinate(x + " " + y);
        return new Result2(true,x,y,place1X,place1Y,place2X,place2Y,address);
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
