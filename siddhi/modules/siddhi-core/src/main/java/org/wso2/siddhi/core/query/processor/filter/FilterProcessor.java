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
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.exception.OperationNotSupportedException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.query.processor.Processor;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link Processor} which handles Filter expressions in Siddhi.
 */
public class FilterProcessor implements Processor {
    static Map<Class<? extends ExpressionExecutor>, ByteCodeGenerator> byteCodegenerators;

    static boolean count = true;
    static byte[] byteArray;
    static AbstractOptimizedExpressionExecutor abstractOptimizedExpressionExecutor;

    static {
        byteCodegenerators = new HashMap<Class<? extends ExpressionExecutor>, ByteCodeGenerator>();
        ByteCode byteCode = new ByteCode();
        byteCodegenerators.put(AndConditionExpressionExecutor.class, byteCode.new PrivateANDExpressionExecutorBytecodeGenerator());
        byteCodegenerators.put(OrConditionExpressionExecutor.class, byteCode.new PrivateORExpressionExecutorBytecodeGenerator());
        byteCodegenerators.put(NotConditionExpressionExecutor.class, byteCode.new PrivateNOTExpressionExecutorBytecodeGenerator());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator());
    }

    protected Processor next;
    private ExpressionExecutor conditionExecutor;
    private ClassWriter classWriter;
    private ByteCodeHelper byteCodeHelper;
    private OptimizedExpressionExecutor optimizedExpressionExecutor = new OptimizedExpressionExecutor();
    private Test2 test2 = new Test2();
    private Test3 test3 = new Test3();

    public FilterProcessor(ExpressionExecutor conditionExecutor) throws InstantiationException, IllegalAccessException {
        if (Attribute.Type.BOOL.equals(conditionExecutor.getReturnType())) {
            this.conditionExecutor = conditionExecutor;
        } else {
            throw new OperationNotSupportedException("Return type of " + conditionExecutor.toString() + " should be " +
                    "of type BOOL. " +
                    "Actual type: " + conditionExecutor.getReturnType().toString());
        }

        byteCodeHelper = new ByteCodeHelper();
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        /*MethodVisitor methodVisitor = byteCodeHelper.start(classWriter);
        this.execute(conditionExecutor, 0, 0, null, 0, methodVisitor, this);
        byteCodeHelper.end(classWriter, methodVisitor);*/
    }

    public FilterProcessor cloneProcessor(String key) throws IllegalAccessException, InstantiationException {
        return new FilterProcessor(conditionExecutor.cloneExecutor(key));
    }

    @Override
    public void process(ComplexEventChunk complexEventChunk) throws IllegalAccessException, InstantiationException,
            InvocationTargetException, IOException {
        complexEventChunk.reset();
        while (complexEventChunk.hasNext()) {
            ComplexEvent complexEvent = complexEventChunk.next();
            /*if(!(Boolean)byteCodeHelper.execute(complexEvent)){
                complexEventChunk.remove();
            }*/

            /*if(!(Boolean)optimizedExpressionExecutor.newWay(conditionExecutor , complexEvent)){
                complexEventChunk.remove();
            }*/

            /*if(OptimizedExpressionExecutor.count ){
                test2.start();
                if(!(Boolean)test3.execute(conditionExecutor, complexEvent, 0, 0, null,
                        0)){
                    complexEventChunk.remove();
                }

                test2.end();
            }else{
                if (!(Boolean) test2.execute(conditionExecutor,complexEvent)){
                    complexEventChunk.remove();
                }
            }*/

            if (FilterProcessor.count) {
                MethodVisitor methodVisitor = byteCodeHelper.start(classWriter);
                if (!(Boolean) this.execute(conditionExecutor, complexEvent, 0, 0, null,
                        0, methodVisitor, this)) {
                    complexEventChunk.remove();
                }
                byteCodeHelper.end(classWriter, methodVisitor);
            } else {
                if (!(Boolean) byteCodeHelper.execute(complexEvent)) {
                    complexEventChunk.remove();
                }
            }
        }

        if (complexEventChunk.getFirst() != null) {
            this.next.process(complexEventChunk);
        }
    }

    @Override
    public Processor getNextProcessor() {
        return next;
    }

    @Override
    public void setNextProcessor(Processor processor) {
        next = processor;
    }

    @Override
    public void setToLast(Processor processor) {
        if (next == null) {
            this.next = processor;
        } else {
            this.next.setToLast(processor);
        }
    }

    public boolean execute(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent,
                           Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                           FilterProcessor filterProcessor) {
        return FilterProcessor.byteCodegenerators.get(conditionExecutor.getClass()).generate(conditionExecutor,
                complexEvent, status, parent, specialCase, parentStatus, methodVisitor, filterProcessor);
    }
}
