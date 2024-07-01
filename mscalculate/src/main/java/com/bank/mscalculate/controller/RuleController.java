package com.bank.mscalculate.controller;

import com.bank.mscalculate.dto.CalculateRequestDTO;
import com.bank.mscalculate.dto.CalculateResponseDTO;
import com.bank.mscalculate.dto.RuleRequestDTO;
import com.bank.mscalculate.dto.RuleResponseDTO;
import com.bank.mscalculate.dto.mapper.RuleMapperService;
import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.services.RuleServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RuleController {

    private final RuleServices ruleServices;
    private final RuleMapperService ruleMapperService;

    @PostMapping("/calculate")
    @Operation(summary = "Adds a math account", description = "Adds a math account", tags = {"Calculate"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<CalculateResponseDTO> calculatePoints(@Valid @RequestBody CalculateRequestDTO requestDTO){
        int totalPoints = ruleServices.calculatePoints(requestDTO.getCategoryId(), requestDTO.getValue());
        CalculateResponseDTO responseDTO = new CalculateResponseDTO(totalPoints);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/rules")
    @Operation(summary = "Adds a rule", description = "Adds a rule", tags = {"Rule"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<RuleResponseDTO> create(@Valid @RequestBody RuleRequestDTO requestDTO){
        Rule rule = ruleMapperService.toEntity(requestDTO);
        Rule saved = ruleServices.save(rule);
        RuleResponseDTO responseDTO = ruleMapperService.toResponseDTO(saved);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/rules")
    @Operation(summary = "Finds all rules", description = "Finds all rules", tags = {"Rule"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<RuleResponseDTO>> findAll() {
        List<Rule> rules = ruleServices.findAll();
        List<RuleResponseDTO> responseDTOS = rules.stream()
                .map(ruleMapperService::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping("/rules/{id}")
    @Operation(summary = "Updates a rule", description = "Updates a rule", tags = {"Rule"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    })
    public ResponseEntity<RuleResponseDTO> update(@PathVariable Long id, @RequestBody RuleRequestDTO ruleRequestDTO){
        Rule rule = ruleMapperService.toEntity(ruleRequestDTO);
        return ResponseEntity.ok(ruleMapperService.toResponseDTO(ruleServices.update(id, rule)));
    }

    @DeleteMapping("/rules/{id}")
    @Operation(summary = "Deletes a rule", description = "Deletes a rule", tags = {"Rule"}, responses = {
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(ruleServices.findById(id).isPresent()){
           ruleServices.delete(id);
           return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
