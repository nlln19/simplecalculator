package org.example.calc;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Thin wrapper around exp4j to keep evaluation logic in one place
 */
public final class ExpressionEvaluator {

    public double evaluate(String input) {
        Expression exp = new ExpressionBuilder(input)
                .variable("π")
                .build()
                .setVariable("π", Math.PI);
        return exp.evaluate();
    }
}
