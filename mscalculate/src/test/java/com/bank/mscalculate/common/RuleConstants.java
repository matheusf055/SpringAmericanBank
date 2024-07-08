package com.bank.mscalculate.common;

import com.bank.mscalculate.entity.Rule;

public class RuleConstants {

    public static final Rule RULE = new Rule
            (1L, "Eletronico", 10);

    public static final Rule INVALID_RULE = new Rule
            (1L, "", 0);
}
