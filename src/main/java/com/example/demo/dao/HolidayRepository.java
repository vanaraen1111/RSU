package com.example.demo.dao;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Holiday;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, String>{
    @Query(value = "select coalesce(sum(cast(atc.\"COUNT\" as decimal)), 0)"
    + "from \"CALENDAR\" atc "
    + "where atc.\"DATE\" between :startDate and :endDate", nativeQuery = true)
    double getHolidayCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
