package com.semicolon.africa.Estore.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentDto {

    private String amount;
    private String email;
    private String[] channels;
}
