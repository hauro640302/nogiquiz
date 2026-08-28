package com.example.demo.common.repository;

import java.sql.ResultSet;

@FunctionalInterface
public interface MapperFunction<R> extends SqlFunction<ResultSet, R> {
}
