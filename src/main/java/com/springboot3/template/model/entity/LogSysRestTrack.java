package com.springboot3.template.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogSysRestTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long no;

    @Column(length = 25, name = "traceTag")
    private String traceTag;

    @Column(length = 60, name = "requestUri")
    private String requestUri;

    @Column(length = 45, name = "userip")
    private String userIp;

    @Column(name = "requestdata")
    private String requestData;

    @Column(name = "responsedata",columnDefinition = "LONGTEXT")
    private String responseData;

    @Column(length = 10, name = "responseCode")
    private String responseCode;

    @Column(name = "runningTime", columnDefinition = "INT")
    private long runningTime;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;


}
