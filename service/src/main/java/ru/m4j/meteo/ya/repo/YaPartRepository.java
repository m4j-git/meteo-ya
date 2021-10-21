/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import ru.m4j.meteo.ya.domain.YaPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YaPartRepository extends JpaRepository<YaPart, Long> {

}
