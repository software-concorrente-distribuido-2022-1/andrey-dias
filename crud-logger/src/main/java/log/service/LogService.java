package log.service;

import log.dto.LogRequestDTO;

public interface LogService {
    void log(LogRequestDTO request);
}
