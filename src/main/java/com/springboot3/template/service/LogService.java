package com.springboot3.template.service;

import com.springboot3.template.model.entity.LogSysAlertTrack;
import com.springboot3.template.model.entity.LogSysRestTrack;
import com.springboot3.template.repository.LogSysAlertTrackRepository;
import com.springboot3.template.repository.LogSysRestTrackRepository;
import com.springboot3.template.utils.HttpTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogSysAlertTrackRepository logSysAlertTrackRepository;
    private final LogSysRestTrackRepository logSysRestTrackRepository;


    public void loggingRest(LogSysRestTrack logSysRestTrack) {
        logSysRestTrackRepository.save(logSysRestTrack);
    }

    @Async
    public void loggingAlert(LogSysAlertTrack logSysAlertTrack) {
        logSysAlertTrackRepository.save(logSysAlertTrack);
    }
}
