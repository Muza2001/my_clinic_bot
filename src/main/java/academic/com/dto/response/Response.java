package academic.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Response {

    private String message;
    private boolean success;
    private Object data;
    private List<Object> dataList;
    private Map<Object, Object> mapData = new HashMap<>();

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Response(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        if (data instanceof List) {
            this.dataList = Collections.singletonList(data);
        } else {
            this.data = data;
        }
    }

    public Response(String message, boolean success, Object data, Map<Object, Object> mapData) {
        this.message = message;
        this.success = success;
        if (data instanceof List) {
            this.dataList = Collections.singletonList(data);
        } else {
          this.data = data;
        }
        this.mapData = mapData;
    }
}
