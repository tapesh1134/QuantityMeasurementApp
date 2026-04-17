package com.example.qmahistoryservice.controller;

import com.example.qmahistoryservice.dto.ApiResponseDto;
import com.example.qmahistoryservice.dto.RecordRequestDto;
import com.example.qmahistoryservice.entity.History;
import com.example.qmahistoryservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {
	private final HistoryService historyService;
	@Autowired
	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@GetMapping("/history")
	public ResponseEntity<ApiResponseDto<List<History>>> getHistory(Authentication authentication) {
		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "History fetched successfully.", historyService.getHistory((Long) authentication.getPrincipal())));
	}

	@PostMapping("/history")
	public ResponseEntity<ApiResponseDto<?>> saveHistory(@RequestBody RecordRequestDto recordRequestDto, Authentication authentication) {
		historyService.saveHistory(recordRequestDto,(Long) authentication.getPrincipal());
		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "History saved successfully."));
	}
}