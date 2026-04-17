package com.example.qmahistoryservice.service;

import com.example.qmahistoryservice.dto.RecordRequestDto;
import com.example.qmahistoryservice.entity.History;
import com.example.qmahistoryservice.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void saveHistory(RecordRequestDto recordRequestDto, Long userId) {
        History history = RecordRequestDto.getRecord(recordRequestDto, userId);
        historyRepository.save(history);
    }

    @Override
    public List<History> getHistory(Long userId) {
        return historyRepository.findByUserId(userId);
    }
}
