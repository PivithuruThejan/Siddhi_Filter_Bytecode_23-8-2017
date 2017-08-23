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
package org.wso2.siddhi.core.executor.condition.compare.lessthanequal;

import org.wso2.siddhi.core.executor.ExpressionExecutor;

/**
 * Executor class for Long-Float Less Than or Equal condition. Condition evaluation logic is implemented within
 * executor.
 */
public class LessThanEqualCompareConditionExpressionExecutorLongFloat
        extends LessThanEqualCompareConditionExpressionExecutor {


    public LessThanEqualCompareConditionExpressionExecutorLongFloat(
            ExpressionExecutor leftExpressionExecutor,
            ExpressionExecutor rightExpressionExecutor) {
        super(leftExpressionExecutor, rightExpressionExecutor);
    }

    @Override
    protected Boolean execute(Object left, Object right) {
        return (Long) left <= (Float) right;

    }

    @Override
    public ExpressionExecutor cloneExecutor(String key) {
        return new LessThanEqualCompareConditionExpressionExecutorLongFloat(leftExpressionExecutor.cloneExecutor(key)
                , rightExpressionExecutor.cloneExecutor(key));
    }
}
