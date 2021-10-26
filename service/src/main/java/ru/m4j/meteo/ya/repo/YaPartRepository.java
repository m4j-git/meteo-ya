/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.m4j.meteo.ya.domain.YaPart;

public interface YaPartRepository extends JpaRepository<YaPart, Long> {

}
