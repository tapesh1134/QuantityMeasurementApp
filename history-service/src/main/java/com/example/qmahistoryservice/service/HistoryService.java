package com.example.qmahistoryservice.service;

import com.example.qmahistoryservice.dto.RecordRequestDto;
import com.example.qmahistoryservice.entity.History;

import java.util.List;

public interface HistoryService {
    void saveHistory(RecordRequestDto recordRequestDto, Long userId);
    List<History> getHistory(Long userId);
}
