package org.quantitymeasurement.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoQuantityRequestDto {

    private QuantityRequestDto q1;
    private QuantityRequestDto q2;

}