package com.service.impl;

import com.entities.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentStatistic {
    private Map<Degree, Integer> statisticMap;
}
