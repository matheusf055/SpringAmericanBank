package com.bank.mscalculate.domain;

import static com.bank.mscalculate.common.RuleConstants.RULE;
import static com.bank.mscalculate.exception.GeneralExceptionHandler.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.mscalculate.controller.RuleController;
import com.bank.mscalculate.dto.calculatedto.CalculateRequestDTO;
import com.bank.mscalculate.dto.mapper.RuleMapperService;
import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.services.RuleServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(RuleController.class)
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RuleServices ruleServices;

    @MockBean
    private RuleMapperService ruleMapperService;

    @Test
    public void calculatePoints_WithValidData_ReturnsOk() throws Exception {
        CalculateRequestDTO requestDTO = new CalculateRequestDTO(1L, 100.0);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/calculate")
                        .content(asJsonString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void calculatePoints_WithInvalidData_ReturnsBadRequest() throws Exception {
        CalculateRequestDTO requestDTO = new CalculateRequestDTO(null, 100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/calculate")
                        .content(asJsonString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createRule_WithValidData_ReturnsCreated() throws Exception{
        when(ruleServices.save(RULE)).thenReturn(RULE);

        mockMvc.perform(post("/v1/rules")
                        .content(objectMapper.writeValueAsString(RULE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void createRule_WithInValidData_ReturnsBadRequest() throws Exception{
        Rule emptyRule = new Rule();
        Rule invalidRule = new Rule(1L, "", 5);

        mockMvc.perform(post("/v1/rules")
                    .content(objectMapper.writeValueAsString(emptyRule))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/rules")
                    .content(objectMapper.writeValueAsString(invalidRule))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createRule_WithExistingCategory_ReturnsConflict() throws Exception{
        when(ruleServices.save(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/v1/rules")
                .content(objectMapper.writeValueAsString(RULE))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getAllRules_ReturnsListOfRules() throws Exception{
        List<Rule> rules = Arrays.asList(RULE, new Rule(2L, "Madeira", 100));

        when(ruleServices.findAll()).thenReturn(rules);

        mockMvc.perform(get("/v1/rules")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(rules.size()));
    }

    @Test
    public void updateRule_WithValidData_ReturnsOk() throws Exception {
        Rule updatedRule = new Rule(1L, "Eletronico",10);

        when(ruleServices.update(any(Long.class), any(Rule.class))).thenReturn(updatedRule);

        mockMvc.perform(put("/v1/rules/1")
                        .content(objectMapper.writeValueAsString(updatedRule))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeRule_WithUnexistingId_ReturnsNotFound() throws Exception {
        final Long ruleId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(ruleServices).delete(ruleId);

        mockMvc.perform(delete("/v1/rules/" + ruleId))
                .andExpect(status().isNotFound());
    }
}
