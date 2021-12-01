package ru.m4j.meteo.ya;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuditListener {
    @PrePersist
    private void onPrePersist(Object object) {
        log.info("persist object: " + object);
    }

    @PreUpdate
    private void onPreUpdate(Object object) {
        log.info("update object: " + object);
    }

    @PreRemove
    public void onPreRemove(Object object) {
        log.info("remove object: " + object);
    }
}
