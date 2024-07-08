package com.bank.mscalculate.services;

import com.bank.mscalculate.config.queue.UpdatePointsPublisher;
import com.bank.mscalculate.entity.Protocol;
import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.entity.UpdatePoints;
import com.bank.mscalculate.repository.RuleRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RuleServices {

    private final RuleRepository repository;
    private final UpdatePointsPublisher publisher;

    public int calculatePoints(Long categoryId, double value) {
        Optional<Rule> ruleOptional = repository.findById(categoryId);

        int parity = ruleOptional.map(Rule::getParity).orElse(1);

        return (int) (value * parity);
    }

    public Rule save(Rule rule){
        return repository.save(rule);
    }

    public List<Rule> findAll(){
        return repository.findAll();
    }

    public Optional<Rule> findById(Long id){
        return repository.findById(id);
    }

    public Rule update(Long id, Rule updatedRule){
        if (repository.existsById(id)){
            updatedRule.setId(id);
            return repository.save(updatedRule);
        } else {
            throw new NotFoundException("Rule not found with id: " + id);
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Protocol updatePoints(UpdatePoints points){
        try {
            publisher.updatePoints(points);
            var protocol = UUID.randomUUID().toString();
            return new Protocol(protocol);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
