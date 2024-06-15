package com.semicolon.africa.Estore.dtos.request;

import jakarta.validation.constraints.Digits;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanDto {
    private String name;
    private String interval;
    @Digits(integer = 6, fraction = 2)
    private Integer amount;
}
