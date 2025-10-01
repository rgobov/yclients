package gobov.roma.yclientsv1.Service;


import gobov.roma.yclientsv1.model.Appointment;
import gobov.roma.yclientsv1.model.Vacation;
import gobov.roma.yclientsv1.repository.AppointmentRepository;
import gobov.roma.yclientsv1.repository.BreakRepository;
import gobov.roma.yclientsv1.repository.ResourceRepository;
import gobov.roma.yclientsv1.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BreakRepository breakRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Проверяет, свободен ли мастер в указанное время (учитывает записи, перерывы, отпуска)
     */
    public boolean isMasterAvailable(Long masterId, LocalDateTime startTime, LocalDateTime endTime) {
        // Проверка записей
        List<Appointment> appointments = appointmentRepository.findByMasterProfileIdAndOverlap(masterId, startTime, endTime);
        if (!appointments.isEmpty()) return false;

        // Проверка перерывов
        List<com.yclientsv1.model.Break> breaks = breakRepository.findByMasterProfileIdAndOverlap(masterId, startTime, endTime);
        if (!breaks.isEmpty()) return false;

        // Проверка отпусков
        List<Vacation> vacations = vacationRepository.findByMasterProfileIdAndOverlap(masterId, startTime, endTime);
        if (!vacations.isEmpty()) return false;

        return true;
    }

    /**
     * Проверяет, доступны ли все требуемые ресурсы
     */
    public boolean areResourcesAvailable(Long serviceId, LocalDateTime startTime, LocalDateTime endTime) {
        // Получаем услугу и её ресурсы
        // В реальном проекте добавьте ServiceRepository
        // Здесь упрощённо — предположим, что сервис с ID 1 требует ресурс с ID 1
        // В реальности: service.getRequiredResources().forEach(...)

        // Пример: предположим, что услуга 1 требует ресурс 1
        List<Appointment> busy = appointmentRepository.findByResourceIdAndOverlap(1L, startTime, endTime);
        return busy.isEmpty();
    }

    /**
     * Получить расписание мастера за период
     */
    public List<Appointment> getMasterSchedule(Long masterId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByMasterProfileIdAndTimeRange(masterId, start, end);
    }

    /**
     * Получить расписание ресурса за период
     */
    public List<Appointment> getResourceSchedule(Long resourceId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByResourceIdAndTimeRange(resourceId, start, end);
    }

    /**
     * Получить расписание компании за период
     */
    public List<Appointment> getCompanySchedule(Long companyId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByCompanyIdAndTimeRange(companyId, start, end);
    }
}