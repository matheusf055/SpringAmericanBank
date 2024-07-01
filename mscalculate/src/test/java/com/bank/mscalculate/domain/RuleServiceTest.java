package com.bank.mscalculate.domain;

import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.repository.RuleRepository;
import com.bank.mscalculate.services.RuleServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bank.mscalculate.common.RuleConstants.INVALID_RULE;
import static com.bank.mscalculate.common.RuleConstants.RULE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

    @InjectMocks
    private RuleServices ruleServices;

    @Mock
    private RuleRepository ruleRepository;

    @Test
    public void calculatePoints_WithValidData_ReturnsCorrectPoints() {
        Long categoryId = 1L;
        double value = 100.0;

        Rule rule = new Rule(categoryId, "Eletronico", 10);
        when(ruleRepository.findById(categoryId)).thenReturn(Optional.of(rule));

        int totalPoints = ruleServices.calculatePoints(categoryId, value);

        int expectedPoints = (int) (value * rule.getParity());
        assertEquals(expectedPoints, totalPoints);
    }

    @Test
    public void calculatePoints_WithNonExistingCategory_ReturnsDefaultPoints() {
        Long categoryId = 99L;
        double value = 100.0;

        when(ruleRepository.findById(categoryId)).thenReturn(Optional.empty());

        int totalPoints = ruleServices.calculatePoints(categoryId, value);

        assertEquals((int) value, totalPoints);
    }

    @Test
    public void createRule_WIthValidData_ReturnsRule(){
        when(ruleRepository.save(RULE)).thenReturn(RULE);

        Rule sut = ruleServices.save(RULE);

        assertThat(sut).isEqualTo(RULE);
    }

    @Test
    public void createRule_WithInvalidData_ThrowsException(){
        when(ruleRepository.save(INVALID_RULE)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> ruleServices.save(INVALID_RULE)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getRule_ByExistingId_ReturnsRule(){
        when(ruleRepository.findById(anyLong())).thenReturn(Optional.of(RULE));

        Optional<Rule> sut = ruleServices.findById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(RULE);
    }

    @Test
    public void findAllRules_ReturnsRuleList(){
        when(ruleRepository.findAll()).thenReturn(Collections.singletonList(RULE));

        List<Rule> sut = ruleServices.findAll();

        assertThat(sut).isNotEmpty();
        assertThat(sut).contains(RULE);
    }

    @Test
    public void update_ExistingRule_UpdatesRule(){
        Rule rule = new Rule(1L, "Eletronico", 10);

        when(ruleRepository.existsById(1L)).thenReturn(true);
        when(ruleRepository.save(rule)).thenReturn(rule);

        Rule sut = ruleServices.update(1L, rule);

        assertEquals(rule, sut);
    }

    @Test
    public void update_NonExistingRule_ThrowsRuntimeException(){
        Rule rule = new Rule(1L, "Eletronico", 10);

        when(ruleRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ruleServices.update(1L, rule);
        });

        assertEquals("Provider for jakarta.ws.rs.ext.RuntimeDelegate cannot be found", exception.getCause().getMessage());
    }

    @Test
    public void removeRule_WithExistingId_doesNotThrowAnyException(){
        assertThatCode(() -> ruleServices.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removeRule_WithUnexistingId_doesNotThrowAnyException(){
        doThrow(new RuntimeException()).when(ruleRepository).deleteById(99L);

        assertThatThrownBy(() -> ruleServices.delete(99L)).isInstanceOf(RuntimeException.class);
    }
}
