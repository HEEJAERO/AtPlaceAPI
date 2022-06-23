package capstone.atplace.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Result2<T> {
        private boolean success; // 반환 형식
        private Double x;
        private Double y;
        private Double place1X;
        private Double place1Y;
        private Double place2X;
        private Double place2Y;

        private  T data;
}

