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
import org.wso2.siddhi.core.executor.condition.compare.equal.*;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.*;
import org.wso2.siddhi.core.executor.condition.compare.greaterthanequal.*;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.*;
import org.wso2.siddhi.core.executor.condition.compare.lessthanequal.*;
import org.wso2.siddhi.core.executor.condition.compare.notequal.NotEqualCompareConditionExpressionExecutorBoolBool;

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
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorDoubleDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorDoubleFloat.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorDoubleFloat.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorDoubleInt.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorDoubleInt.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorIntDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorIntDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorIntFloat.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorIntFloat.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatInt.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatInt.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorIntInt.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorIntInt.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorDoubleLong.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorDoubleLongBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorDoubleLong.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorDoubleLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorLongDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorLongDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorLongDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorLongDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatLong.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatLongBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatLong.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorLongFloat.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorLongFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorLongFloat.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorLongFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorIntLong.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorIntegerLongBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorIntLong.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorIntegerLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorLongInt.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorLongIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorLongInt.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorLongIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorLongLong.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorLongLongBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorLongLong.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorLongLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorDoubleDouble.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorDoubleFloat.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorDoubleInt.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorDoubleLong.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorFloatFloat.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorFloatInt.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorFloatLong.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorIntDouble.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorIntFloat.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorIntInt.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorIntLong.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorLongDouble.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorLongFloat.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorLongInt.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter());
        byteCodegenerators.put(GreaterThanEqualCompareConditionExpressionExecutorLongLong.class,
                byteCode.new PrivateGreaterThanEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorDoubleDouble.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorDoubleFloat.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorDoubleInt.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorDoubleLong.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorFloatFloat.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorFloatInt.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorFloatLong.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorIntDouble.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorIntFloat.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorIntInt.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorIntLong.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorLongDouble.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorLongFloat.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorLongInt.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter());
        byteCodegenerators.put(LessThanEqualCompareConditionExpressionExecutorLongLong.class,
                byteCode.new PrivateLessThanEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorBoolBool.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorBoolBoolBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorDoubleDouble.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorDoubleFloat.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorDoubleInt.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorDoubleLong.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorFloatFloat.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorFloatInt.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorFloatIntBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorFloatLong.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorIntDouble.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorIntFloat.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorIntInt.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorIntLong.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorLongDouble.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorLongFloat.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorLongInt.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorLongLong.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter());
        byteCodegenerators.put(EqualCompareConditionExpressionExecutorStringString.class,
                byteCode.new PrivateEqualCompareConditionExpressionExecutorStringStringBytecodeEmitter());

        byteCodegenerators.put(NotEqualCompareConditionExpressionExecutorBoolBool.class,
                byteCode.new PrivateNotEqualCompareConditionExpressionExecutorBoolBoolBytecodeEmitter());
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
