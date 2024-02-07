package me.zhengjie.modules.studio.rest;

import io.swagger.annotations.ApiOperation;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.studio.domain.StudioPageVisit;
import me.zhengjie.modules.studio.service.StudioPageVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

/**
 * @author levi_bee
 */ // 访问统计控制器
@RestController
@RequestMapping("/api/page-visit")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudioPageVisitController {
    @Autowired
    private StudioPageVisitService pageVisitService;

    @Log("增加访问量")
    @ApiOperation("增加访问量")
    @PostMapping("/record")
    @AnonymousAccess
    public ResponseEntity<String> recordVisit() {
        pageVisitService.recordVisit();
        return ResponseEntity.ok("Visit recorded successfully!");
    }

    @Log("获得所有访问记录")
    @ApiOperation("获得所有访问记录")
    @GetMapping("/getAllVisits")
    @AnonymousAccess
    public ResponseEntity<List<StudioPageVisit>> getAllVisits() {
        List<StudioPageVisit> visits = pageVisitService.getAllVisits();
        return ResponseEntity.ok(visits);
    }

    @Log("按天获得访问记录")
    @ApiOperation("获得所有访问记录")
    @GetMapping("/dailyVisitCount")
    @AnonymousAccess
    public ResponseEntity<Map<LocalDate, Long>> getDailyVisitCount() {
        Map<LocalDate, Long> dailyVisitCount = pageVisitService.getDailyVisitCount();
        return ResponseEntity.ok(dailyVisitCount);
    }

    @Log("按月获得访问记录")
    @ApiOperation("获得所有访问记录")
    @GetMapping("/monthlyVisitCount")
    @AnonymousAccess
    public ResponseEntity<Map<YearMonth, Long>> getMonthlyVisitCount() {
        Map<YearMonth, Long> monthlyVisitCount = pageVisitService.getMonthlyVisitCount();
        return ResponseEntity.ok(monthlyVisitCount);
    }
}
