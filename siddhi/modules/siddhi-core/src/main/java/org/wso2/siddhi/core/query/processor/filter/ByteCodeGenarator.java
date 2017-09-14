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

import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorDoubleDouble;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorDoubleFloat;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatFloat;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatFloat;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the class that generates byte code for ExpressionExecutor.
 */
public class ByteCodeGenarator {

    static Map<Class<? extends ExpressionExecutor>, ByteCodeEmitter> byteCodegenerators;
    static byte[] byteArray;
    static ExpressionExecutor expressionExecutor;

    static {
        byteCodegenerators = new HashMap<Class<? extends ExpressionExecutor>, ByteCodeEmitter>();
        ByteCodeRegistry byteCode = new ByteCodeRegistry();
        byteCodegenerators.put(AndConditionExpressionExecutor.class, byteCode.
                new PrivateANDExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(OrConditionExpressionExecutor.class, byteCode.
                new PrivateORExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(NotConditionExpressionExecutor.class, byteCode.
                new PrivateNOTExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(VariableExpressionExecutor.class, byteCode.
                new PrivateVariableExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(ConstantExpressionExecutor.class, byteCode.
                new PrivateConstantExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatFloat.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorDoubleDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatFloat.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatFloatBytecodeEmitter());
    }

    private ClassWriter classWriter;
    private ByteCodeHelper byteCodeHelper;

    /**
     * This method returns Expression executor with byte code.
     *
     * @param conditionExecutor
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public ExpressionExecutor build(ExpressionExecutor conditionExecutor) throws InstantiationException,
            IllegalAccessException {
        this.byteCodeHelper = new ByteCodeHelper();
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor methodVisitor = byteCodeHelper.start(classWriter);
        this.execute(conditionExecutor, 0, 0, null, 0, methodVisitor,
                this);
        this.byteCodeHelper.end(classWriter, methodVisitor);
        return ByteCodeGenarator.expressionExecutor;
    }

    /**
     * This method generates byte code for a specific expression.
     *
     * @param conditionExecutor
     * @param status
     * @param parent
     * @param specialCase
     * @param parentStatus
     * @param methodVisitor
     * @param byteCodeGenarator
     */
    public void execute(ExpressionExecutor conditionExecutor, int status, int parent,
                        Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                        ByteCodeGenarator byteCodeGenarator) {
        ByteCodeGenarator.byteCodegenerators.get(conditionExecutor.getClass()).generate(conditionExecutor, status, parent,
                specialCase, parentStatus, methodVisitor, byteCodeGenarator);
    }
}
