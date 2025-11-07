package com.mindbridge.dto.RequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckLoginIdRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;


}
