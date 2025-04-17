package conectaTEA.conectaTEA.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketRequest {

    private String action;
    private Map<String, Object> data;
}
