package me.zhengjie.modules.studio.service;

import me.zhengjie.modules.studio.domain.StudioPageVisit;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2024/2/6 14:00
 * @description ：
 */
public interface StudioPageVisitService {
     void recordVisit();

     List<StudioPageVisit> getAllVisits();

     Map<LocalDate, Long> getDailyVisitCount();

     Map<YearMonth, Long> getMonthlyVisitCount();

}
