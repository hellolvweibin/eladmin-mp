package me.zhengjie.modules.studio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.studio.domain.StudioImage;
import me.zhengjie.modules.studio.domain.StudioPageVisit;
import me.zhengjie.modules.studio.domain.StudioPartner;
import me.zhengjie.modules.studio.mapper.StudioPageVisitMapper;
import me.zhengjie.modules.studio.mapper.StudioPartnerMapper;
import me.zhengjie.modules.studio.service.StudioPageVisitService;
import me.zhengjie.modules.studio.service.StudioPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project ：eladmin
 * @Author ：Levi_Bee
 * @Date ：2024/2/6 14:03
 * @description ：
 */
@Service
@RequiredArgsConstructor
public class StudioPageVisitServiceImpl extends ServiceImpl<StudioPageVisitMapper, StudioPageVisit> implements StudioPageVisitService {
    @Autowired
    private StudioPageVisitMapper studioPageVisitMapper;

    public void recordVisit() {
        StudioPageVisit pageVisit = new StudioPageVisit();
        LocalDateTime now = LocalDateTime.now();
        pageVisit.setVisitTime(now);
        pageVisit.setVisitDate(now.toLocalDate());
        studioPageVisitMapper.insert(pageVisit);
    }

    public List<StudioPageVisit> getAllVisits() {
        return studioPageVisitMapper.selectList(null);
    }

    @Override
    public Map<LocalDate, Long> getDailyVisitCount() {
        List<StudioPageVisit> visits = studioPageVisitMapper.selectList(null);
        Map<LocalDate, Long> dailyVisitCount = new HashMap<>();
        for (StudioPageVisit visit : visits) {
            LocalDate visitDate = visit.getVisitDate();
            dailyVisitCount.put(visitDate, dailyVisitCount.getOrDefault(visitDate, 0L) + 1);
        }
        return dailyVisitCount;
    }

    @Override
    public Map<YearMonth, Long> getMonthlyVisitCount() {
        List<StudioPageVisit> visits = studioPageVisitMapper.selectList(null);
        Map<YearMonth, Long> monthlyVisitCount = new HashMap<>();
        for (StudioPageVisit visit : visits) {
            YearMonth visitMonth = YearMonth.from(visit.getVisitDate());
            monthlyVisitCount.put(visitMonth, monthlyVisitCount.getOrDefault(visitMonth, 0L) + 1);
        }
        return monthlyVisitCount;
    }
}
