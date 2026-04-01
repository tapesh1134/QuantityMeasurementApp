package org.quantitymeasurement.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponseDto(boolean success, String message){
        this(success,message,null);
    }
}
