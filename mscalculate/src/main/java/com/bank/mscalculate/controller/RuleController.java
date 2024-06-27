package com.bank.mscalculate.controller;

import com.bank.mscalculate.dto.RuleRequestDTO;
import com.bank.mscalculate.dto.RuleResponseDTO;
import com.bank.mscalculate.dto.mapper.RuleMapperService;
import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.services.RuleServices;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/rules")
    public ResponseEntity<RuleResponseDTO> create(@RequestBody RuleRequestDTO requestDTO){
        Rule rule = ruleMapperService.toEntity(requestDTO);
        Rule saved = ruleServices.save(rule);
        RuleResponseDTO responseDTO = ruleMapperService.toResponseDTO(saved);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<RuleResponseDTO>> findAll() {
        List<Rule> rules = ruleServices.findAll();
        List<RuleResponseDTO> responseDTOS = rules.stream()
                .map(ruleMapperService::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping("/rules/{id}")
    public ResponseEntity<RuleResponseDTO> update(@PathVariable Long id, @RequestBody RuleRequestDTO ruleRequestDTO){
        Rule rule = ruleMapperService.toEntity(ruleRequestDTO);
        return ResponseEntity.ok(ruleMapperService.toResponseDTO(ruleServices.update(id, rule)));
    }

    @DeleteMapping("/rules/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(ruleServices.findById(id).isPresent()){
           ruleServices.delete(id);
           return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
