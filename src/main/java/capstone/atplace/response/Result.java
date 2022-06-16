package capstone.atplace.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Result<T> {
    private boolean success; // 반환 형식
    private  T data;
}
