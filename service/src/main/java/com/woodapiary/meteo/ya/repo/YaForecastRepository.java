/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.repo;

import com.woodapiary.meteo.ya.domain.YaForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YaForecastRepository extends JpaRepository<YaForecast, Long> {

}
