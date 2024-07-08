package com.bank.mscalculate.controller;

import com.bank.mscalculate.entity.Protocol;
import com.bank.mscalculate.entity.UpdatePoints;
import com.bank.mscalculate.services.RuleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UpdatePointsController {

    private final RuleServices ruleServices;

    @PostMapping("/update-points")
    public ResponseEntity updatePoints(@RequestBody UpdatePoints points){
        try {
            Protocol protocol = ruleServices.updatePoints(points);
            return ResponseEntity.ok(protocol);
        } catch (Error e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
