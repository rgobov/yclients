package gobov.roma.yclientsv1.controller;


import gobov.roma.yclientsv1.Service.CalendarService;
import gobov.roma.yclientsv1.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/master/{masterId}")
    public List<Appointment> getMasterSchedule(
            @PathVariable Long masterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        return calendarService.getMasterSchedule(masterId, start, end);
    }

    @GetMapping("/resource/{resourceId}")
    public List<Appointment> getResourceSchedule(
            @PathVariable Long resourceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        return calendarService.getResourceSchedule(resourceId, start, end);
    }

    @GetMapping("/company/{companyId}")
    public List<Appointment> getCompanySchedule(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        return calendarService.getCompanySchedule(companyId, start, end);
    }

    @PostMapping
    public String createAppointment(
            @RequestParam Long clientId,
            @RequestParam Long masterId,
            @RequestParam Long serviceId,
            @RequestParam(required = false) Long resourceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {

        // Здесь вы бы создавали Appointment через сервис
        // Но для демонстрации — просто проверим доступность

        if (!calendarService.isMasterAvailable(masterId, startTime, startTime.plusMinutes(60))) {
            return "Мастер занят в это время";
        }

        if (resourceId != null && !calendarService.areResourcesAvailable(serviceId, startTime, startTime.plusMinutes(60))) {
            return "Ресурс занят в это время";
        }

        return "Запись создана! (в реальном проекте — сохраняется в БД)";
    }
}