package capstone.atplace.controller;

import capstone.atplace.form.CoordinateForm;
import capstone.atplace.kakaoapi.kakaoLocalAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class kakaoApiController {
    private final kakaoLocalAPI kakaoLocalAPI;
    @GetMapping("/recommend")
    public String recommend(@ModelAttribute CoordinateForm coordinateForm) {
        kakaoLocalAPI.getAddressInfoByCoordinate(coordinateForm.getX()+" "+ coordinateForm.getY());
        return "recommend";
    }
}
