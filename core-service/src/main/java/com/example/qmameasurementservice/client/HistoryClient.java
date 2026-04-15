package com.example.qmameasurementservice.client;

import com.example.qmameasurementservice.dto.RecordRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "QMA-HISTORY-SERVICE")
public interface HistoryClient {
    @PostMapping("/history")
    void saveHistory(@RequestBody RecordRequestDto recordRequestDto, @RequestHeader("Cookie") String cookie);
}
