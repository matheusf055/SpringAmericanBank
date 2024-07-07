package com.bank.mscalculate.domain;

import com.bank.mscalculate.common.RuleConstants;
import com.bank.mscalculate.entity.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {

    @Test
    public void testValidRule(){
        Rule rule = RuleConstants.RULE;

        assertEquals(1L, rule.getId());
        assertEquals("Eletronico", rule.getCategory());
        assertEquals(10, rule.getParity());
    }

    @Test
    public void testInvalidRule(){
        Rule rule = RuleConstants.INVALID_RULE;

        assertEquals(1L, rule.getId());
        assertEquals("", rule.getCategory());
        assertEquals(0, rule.getParity());
    }

    @Test
    public void testEqualsAndHashCode(){
        Rule rule1 = RuleConstants.RULE;
        Rule rule2 = new Rule(1L, "Eletronico", 10);
        Rule rule3 = RuleConstants.INVALID_RULE;

        assertEquals(rule1, rule2);
        assertNotEquals(rule1, rule3);
        assertEquals(rule1.hashCode(), rule2.hashCode());
        assertNotEquals(rule1.hashCode(), rule3.hashCode());
    }

    @Test
    public void testToString(){
        Rule rule = RuleConstants.RULE;
        String expected = "Rule(id=1, category=Eletronico, parity=10)";
        assertEquals(expected, rule.toString());
    }

    @Test
    public void testSettersAndGetters(){
        Rule rule = new Rule();
        rule.setId(2L);
        rule.setCategory("Eletronico");
        rule.setParity(10);

        assertEquals(2L, rule.getId());
        assertEquals("Eletronico", rule.getCategory());
        assertEquals(10, rule.getParity());
    }

    @Test
    public void testConstructorWithAllFields(){
        Rule rule = new Rule(1L, "Eletronico", 10);

        assertEquals(1L, rule.getId());
        assertEquals("Eletronico", rule.getCategory());
        assertEquals(10, rule.getParity());
    }

    @Test
    public void testCanEqual(){
        Rule rule1 = RuleConstants.RULE;
        Rule rule2 = new Rule(1L, "Eletronico", 10);

        assertEquals(rule1, rule2);
    }
}
