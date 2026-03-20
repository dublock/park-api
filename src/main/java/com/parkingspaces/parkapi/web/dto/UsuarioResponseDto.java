package com.parkingspaces.parkapi.web.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {

    private String id;
    private String username;
    private String role;
}
