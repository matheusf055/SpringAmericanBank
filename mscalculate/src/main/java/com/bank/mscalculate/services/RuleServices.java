package com.bank.mscalculate.services;

import com.bank.mscalculate.entity.Rule;
import com.bank.mscalculate.repository.RuleRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleServices {

    private final RuleRepository repository;

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
}
