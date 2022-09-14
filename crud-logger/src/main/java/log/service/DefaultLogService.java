package log.service;

import java.time.LocalDateTime;
import log.domain.LogOperation;
import log.repository.LogRepository;
import log.dto.LogRequestDTO;

public class DefaultLogService implements LogService {
    private final LogRepository logRepository;

    public DefaultLogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void log(LogRequestDTO request) {
        System.out.println("[LogApplication] userId: " + request.getUserId() + " | " + request.getMessage());
        this.logRepository.save(new LogOperation(request.getUserId(), request.getMessage(), LocalDateTime.now()));
    }
}
