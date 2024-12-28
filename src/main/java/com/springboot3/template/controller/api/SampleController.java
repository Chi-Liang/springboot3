package com.springboot3.template.controller.api;


import com.springboot3.template.model.entity.Sample;
import com.springboot3.template.model.req.SampleReq;
import com.springboot3.template.model.res.SampleRes;
import com.springboot3.template.model.to.Todo;
import com.springboot3.template.service.HttpService;
import com.springboot3.template.service.SampleService;
import com.springboot3.template.utils.JwtUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sample")
public class SampleController {

    private final SampleService sampleService;

    private final HttpService httpService;
    private final JwtUtil jwtUtil;


    @PostMapping("/getAll")
    public ResponseEntity<List<SampleRes>> getALlSample() {
        return ResponseEntity.ok(sampleService.getAllSample());
    }

    @PostMapping("/getSampleById")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "{\"id\":1}"
                            )
                    }
            )
    )
    public ResponseEntity<SampleRes> getSampleById(@Validated(SampleReq.getByIdGroup.class) @RequestBody SampleReq sampleReq) {
        return ResponseEntity.ok(sampleService.getSampleById(sampleReq.id()));
    }

    @PostMapping("/saveSample")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "{\"name\":\"john\",\"mobilePhone\":\"0955236985\"}"
                            )
                    }
            )
    )
    public ResponseEntity<Sample> saveSample( @Validated(SampleReq.saveGroup.class) @RequestBody SampleReq sampleReq) {
        return ResponseEntity.ok(sampleService.saveSample(sampleReq));
    }

    @PostMapping("/editSample")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "{\"id\":1,\"name\":\"john\",\"mobilePhone\":\"0955236985\"}"
                            )
                    }
            )
    )
    public ResponseEntity<Sample> editSample(@Validated(SampleReq.editGroup.class) @RequestBody SampleReq sampleReq) {
        return ResponseEntity.ok(sampleService.editSample(sampleReq));
    }


    @PostMapping("/deleteSample")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "{\"id\":1}"
                            )
                    }
            )
    )
    public ResponseEntity<Sample> deleteSample(@Validated(SampleReq.deleteGroup.class) @RequestBody SampleReq sampleReq) {
        return ResponseEntity.ok(sampleService.deleteByIdSample(sampleReq.id()));
    }

    @PostMapping("/getTodoById")
    public ResponseEntity<Todo> getTodoById() {
        return ResponseEntity.ok(httpService.getTodoById(1));
    }

    @PostMapping("/createToken")
    public ResponseEntity<String> createToken() {
        return ResponseEntity.ok(jwtUtil.createToken("test",Map.of("test","testClaim")));
    }

}
