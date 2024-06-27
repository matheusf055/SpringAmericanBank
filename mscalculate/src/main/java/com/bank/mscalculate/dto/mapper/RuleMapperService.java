package com.bank.mscalculate.dto.mapper;

import com.bank.mscalculate.dto.RuleRequestDTO;
import com.bank.mscalculate.dto.RuleResponseDTO;
import com.bank.mscalculate.entity.Rule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleMapperService {

    private final ModelMapper modelMapper;

    public Rule toEntity(RuleRequestDTO ruleRequestDTO){
        return modelMapper.map(ruleRequestDTO, Rule.class);
    }

    public RuleResponseDTO toResponseDTO(Rule rule){
        return modelMapper.map(rule, RuleResponseDTO.class);
    }
}
