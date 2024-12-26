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
public class LogSysAlertTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long no;

    @Column(length = 45, name = "userip")
    private String userIp;

    @Column(length = 50, name = "alerturi")
    private String alertUri;

    @Column(name = "alertmsg", columnDefinition = "LONGTEXT")
    private String alertMsg;

    @Column(name = "creatdt")
    @CreationTimestamp
    private LocalDateTime createdt;

}
