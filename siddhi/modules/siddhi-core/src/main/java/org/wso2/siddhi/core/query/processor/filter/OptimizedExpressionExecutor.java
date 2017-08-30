/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;

import java.lang.reflect.InvocationTargetException;

/**
 * Implementation of the Siddhi Expression Executor in an optimized manner
 */
public class OptimizedExpressionExecutor {
    /**
     * Takes data from the complex event object and returns whether that event will proceed or not
     *
     * @param complexEvent
     * @return
     */
    static boolean count = true;
    static byte[] byteArray;
    static AbstractOptimizedExpressionExecutor abstractOptimizedExpressionExecutor;

    public static boolean byteCode(ExpressionExecutor expressionExecutor, ComplexEvent complexEvent) throws
            IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println("From Byte Code");
        return true;

    }

    public boolean optimizedExecute(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent) {

        if (conditionExecutor instanceof AndConditionExpressionExecutor) {
            //System.out.println("AND");
            ExpressionExecutor left = ((AndConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((AndConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            return optimizedExecute(left, complexEvent) && optimizedExecute(right, complexEvent);

        } else if (conditionExecutor instanceof OrConditionExpressionExecutor) {
            //System.out.println("OR");
            ExpressionExecutor left = ((OrConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((OrConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            return optimizedExecute(left, complexEvent) || optimizedExecute(right, complexEvent);
        } else if (conditionExecutor instanceof NotConditionExpressionExecutor) {
            //System.out.println("NOT");
            ExpressionExecutor condition = ((NotConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
            return !optimizedExecute(condition, complexEvent);

        } else if (conditionExecutor instanceof GreaterThanCompareConditionExpressionExecutorFloatDouble) {
            //System.out.println(">");
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            Float leftVariable = null;
            Double rightVariable = null;

            if (left instanceof VariableExpressionExecutor) {
                leftVariable = (Float) complexEvent.getAttribute(((VariableExpressionExecutor) left).getPosition());
            } else if (left instanceof ConstantExpressionExecutor) {
                leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
            }

            if (right instanceof VariableExpressionExecutor) {
                rightVariable = (Double) complexEvent.getAttribute(((VariableExpressionExecutor) right).getPosition());
            } else if (right instanceof ConstantExpressionExecutor) {
                rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
            }
            return leftVariable > rightVariable;

        } else if (conditionExecutor instanceof LessThanCompareConditionExpressionExecutorFloatDouble) {
            //System.out.println("<");
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            Float leftVariable = null;
            Double rightVariable = null;

            if (left instanceof VariableExpressionExecutor) {
                leftVariable = (Float) complexEvent.getAttribute(((VariableExpressionExecutor) left).getPosition());
            } else if (left instanceof ConstantExpressionExecutor) {
                leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
            }

            if (right instanceof VariableExpressionExecutor) {
                rightVariable = (Double) complexEvent.getAttribute(((VariableExpressionExecutor) right).getPosition());
            } else if (right instanceof ConstantExpressionExecutor) {
                rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
            }
            return leftVariable < rightVariable;

        } else {
            return true;
        }

        //return true;
    }


}
