package com.springboot3.template.exception;

import lombok.*;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class ApiException extends RuntimeException {

    @NonNull
    private Object body;

}
