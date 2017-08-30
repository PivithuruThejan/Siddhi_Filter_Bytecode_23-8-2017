package org.wso2.siddhi.core.query.processor.filter;


import org.wso2.siddhi.core.event.ComplexEvent;

import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;


public class Test {
    public boolean execute(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status) {


        ExpressionExecutor left = ((OrConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
        ExpressionExecutor right = ((OrConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
        boolean leftResult = true;
        boolean rightResult = true;
        leftResult = execute(left, complexEvent, 1);

        if (leftResult) {

        } else {

            rightResult = execute(right, complexEvent, 2);

        }

        return leftResult || rightResult;


    }
}
