package it.amrito.pastryshopbe.dto;

import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull
    private String nickname;

    private String password;
}
