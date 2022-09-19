package lesson31.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetRequestInfoDto {

    private String ip;
    private String userAgent;
    private String requestDateTime;
}
