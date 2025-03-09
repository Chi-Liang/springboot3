package com.springboot3.template.model.vo;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record UserVO(Integer no,String password,String mid,String name,LocalDateTime createdt,LocalDateTime modifydt) {
    public UserVO {
    }

}
