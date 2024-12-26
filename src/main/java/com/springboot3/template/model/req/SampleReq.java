package com.springboot3.template.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SampleReq(@NotNull(groups = {editGroup.class,getByIdGroup.class,deleteGroup.class},message = "id cannot be null") Long id,
                        @NotBlank(groups = {editGroup.class,saveGroup.class},message = "name cannot be blank") String name,
                        @NotBlank(groups = {editGroup.class,saveGroup.class},message = "mobilePhone cannot be blank") String mobilePhone) {

    public SampleReq(Long id,String name,String mobilePhone) {
        this.id = id;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }

    public interface saveGroup {}
    public interface editGroup {}
    public interface deleteGroup {}
    public interface getByIdGroup{}

}
