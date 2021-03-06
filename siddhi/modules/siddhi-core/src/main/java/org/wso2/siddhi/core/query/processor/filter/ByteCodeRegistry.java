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

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.*;
import org.wso2.siddhi.core.executor.condition.compare.equal.*;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.*;
import org.wso2.siddhi.core.executor.condition.compare.greaterthanequal.*;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.*;
import org.wso2.siddhi.core.executor.condition.compare.lessthanequal.*;
import org.wso2.siddhi.core.executor.condition.compare.notequal.*;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.executor.math.add.AddExpressionExecutorDouble;
import org.wso2.siddhi.core.executor.math.add.AddExpressionExecutorFloat;
import org.wso2.siddhi.core.executor.math.add.AddExpressionExecutorInt;
import org.wso2.siddhi.core.executor.math.add.AddExpressionExecutorLong;
import org.wso2.siddhi.core.executor.math.divide.DivideExpressionExecutorDouble;
import org.wso2.siddhi.core.executor.math.divide.DivideExpressionExecutorFloat;
import org.wso2.siddhi.core.executor.math.divide.DivideExpressionExecutorInt;
import org.wso2.siddhi.core.executor.math.divide.DivideExpressionExecutorLong;
import org.wso2.siddhi.core.executor.math.mod.ModExpressionExecutorDouble;
import org.wso2.siddhi.core.executor.math.mod.ModExpressionExecutorFloat;
import org.wso2.siddhi.core.executor.math.mod.ModExpressionExecutorInt;
import org.wso2.siddhi.core.executor.math.mod.ModExpressionExecutorLong;
import org.wso2.siddhi.core.executor.math.multiply.MultiplyExpressionExecutorDouble;
import org.wso2.siddhi.core.executor.math.multiply.MultiplyExpressionExecutorFloat;
import org.wso2.siddhi.core.executor.math.multiply.MultiplyExpressionExecutorInt;
import org.wso2.siddhi.core.executor.math.multiply.MultiplyExpressionExecutorLong;
import org.wso2.siddhi.core.executor.math.subtract.SubtractExpressionExecutorDouble;
import org.wso2.siddhi.core.executor.math.subtract.SubtractExpressionExecutorFloat;
import org.wso2.siddhi.core.executor.math.subtract.SubtractExpressionExecutorInt;
import org.wso2.siddhi.core.executor.math.subtract.SubtractExpressionExecutorLong;
import org.wso2.siddhi.core.query.selector.attribute.processor.executor.AggregationAttributeExecutor;
import org.wso2.siddhi.core.query.selector.attribute.processor.executor.GroupByAggregationAttributeExecutor;

import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

/**
 * This is an outer class that consists of classes that has  implemented interface "ByteCodeEmitter".
 */
public class ByteCodeRegistry {
    /**
     * This class generates byte code for "AND" operator.
     */
    class PrivateANDExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((AndConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((AndConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            Label l3 = new Label();
            Label l4 = new Label();
            byteCodeGenarator.execute(left, 1, 1, l1, status, methodVisitor, byteCodeGenarator);
            if (left instanceof VariableExpressionExecutor) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l3);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitLabel(l3);
            methodVisitor.visitInsn(ICONST_0);
            if (parent == 1) {//Checks for consecutive "AND" operators.
                if (parentStatus == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, specialCase);
            } else {
                if (status == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, l1);
            }

            methodVisitor.visitLabel(l0);
            byteCodeGenarator.execute(right, 2, 1, l1, status, methodVisitor,
                    byteCodeGenarator);
            if (right instanceof VariableExpressionExecutor) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l4);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
                methodVisitor.visitVarInsn(ISTORE, 3);
            }
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IFNE, l2);
            methodVisitor.visitLabel(l4);
            methodVisitor.visitInsn(ICONST_0);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }

            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l2);
            methodVisitor.visitInsn(ICONST_1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }

            methodVisitor.visitLabel(l1);
        }
    }

    /**
     * This class generates byte code for "OR" operator.
     */
    class PrivateORExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((OrConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((OrConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            byteCodeGenarator.execute(left, 1, 2, l1, status, methodVisitor,
                    byteCodeGenarator);
            if (left instanceof VariableExpressionExecutor) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
                methodVisitor.visitVarInsn(ISTORE, 2);
            }

            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            if (parent == 2) {//Checks for consecutive "OR" operators.
                if (parentStatus == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, specialCase);
            } else {
                if (status == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, l1);
            }

            methodVisitor.visitLabel(l0);
            byteCodeGenarator.execute(right, 2, 2, l1, status, methodVisitor,
                    byteCodeGenarator);
            if (right instanceof VariableExpressionExecutor) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l2);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
                methodVisitor.visitVarInsn(ISTORE, 3);
            }

            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IFEQ, l2);
            methodVisitor.visitInsn(ICONST_1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }

            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l2);
            methodVisitor.visitInsn(ICONST_0);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }

            methodVisitor.visitLabel(l1);
        }
    }

    /**
     * This class generates byte code for "NOT" operator.
     */
    class PrivateNOTExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor condition = ((NotConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            byteCodeGenarator.execute(condition, 1, 3, l1, status, methodVisitor,
                    byteCodeGenarator);
            if (condition instanceof VariableExpressionExecutor) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l2);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
            methodVisitor.visitVarInsn(ILOAD, 2);
            if (parent == 3) {//Checks for consecutive "NOT" operators.
                if (parentStatus == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, specialCase);
            } else {
                methodVisitor.visitJumpInsn(IFNE, l0);
                methodVisitor.visitLabel(l2);
                methodVisitor.visitInsn(ICONST_1);
                if (status == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, l1);
                methodVisitor.visitLabel(l0);
                methodVisitor.visitInsn(ICONST_0);
                if (status == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }
                methodVisitor.visitLabel(l1);
            }
        }
    }

    /**
     * This class generates byte code for "IsNull" operator.
     */
    class PrivateIsNullExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor condition = ((IsNullConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
            byteCodeGenarator.execute(condition, 1, 0, null, status, methodVisitor,
                    byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitJumpInsn(IFNONNULL, l0);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "IsNullStream" operator.
     */
    class PrivateIsNullStreamExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            int[] eventPosition = ((IsNullStreamConditionExpressionExecutor) conditionExecutor).getEventPosition();
            Label l0 = new Label();
            Label l1 = new Label();
            if (eventPosition == null) {
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitJumpInsn(IFNONNULL, l0);
                methodVisitor.visitInsn(ICONST_1);
                methodVisitor.visitJumpInsn(GOTO, l1);
            } else {
                methodVisitor.visitInsn(ICONST_2);
                methodVisitor.visitIntInsn(NEWARRAY, T_INT);
                for (int i = 0; i < 2; i++) {
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitIntInsn(BIPUSH, i);
                    methodVisitor.visitIntInsn(BIPUSH, eventPosition[i]);
                    methodVisitor.visitInsn(IASTORE);
                }

                if (status == 2) {
                    methodVisitor.visitVarInsn(ASTORE, 3);
                    methodVisitor.visitVarInsn(ALOAD, 1);
                    methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/state/StateEvent");
                    methodVisitor.visitVarInsn(ALOAD, 3);
                    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/state/StateEvent",
                            "getStreamEvent", "([I)Lorg/wso2/siddhi/core/event/stream/StreamEvent;", false);
                } else {
                    methodVisitor.visitVarInsn(ASTORE, 2);
                    methodVisitor.visitVarInsn(ALOAD, 1);
                    methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/state/StateEvent");
                    methodVisitor.visitVarInsn(ALOAD, 2);
                    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/state/StateEvent",
                            "getStreamEvent", "([I)Lorg/wso2/siddhi/core/event/stream/StreamEvent;", false);
                }

                methodVisitor.visitJumpInsn(IFNONNULL, l0);
                methodVisitor.visitInsn(ICONST_1);
                methodVisitor.visitJumpInsn(GOTO, l1);
            }

            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "Bool" operator.
     */
    class PrivateBoolExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor condition = ((BoolConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
            byteCodeGenarator.execute(condition, 1, 0, null, status, methodVisitor,
                    byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            if (!(condition instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            }
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for InConditionExpressionExecutor.
     */
    class PrivateInConditionExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            byteCodeGenarator.unknownExpressionExecutors.add(conditionExecutor);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "ByteCodeRegistry", "unknownExpressionExecutors",
                    "Ljava/util/ArrayList;");
            methodVisitor.visitLdcInsn(byteCodeGenarator.unknownExpressionExecutorIndex);
            byteCodeGenarator.unknownExpressionExecutorIndex++;
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get",
                    "(I)Ljava/lang/Object;", false);
            methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/executor/condition/InConditionExpressionExecutor");
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/executor/condition/InConditionExpressionExecutor",
                    "execute", "(Lorg/wso2/siddhi/core/event/ComplexEvent;)Ljava/lang/Boolean;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for 'Siddhi' extensions.
     */
    class PrivateExtensionBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            byteCodeGenarator.unknownExpressionExecutors.add(conditionExecutor);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "ByteCodeRegistry", "unknownExpressionExecutors",
                    "Ljava/util/ArrayList;");
            methodVisitor.visitLdcInsn(byteCodeGenarator.unknownExpressionExecutorIndex);
            byteCodeGenarator.unknownExpressionExecutorIndex++;
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get",
                    "(I)Ljava/lang/Object;", false);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/executor/ExpressionExecutor",
                    "execute", "(Lorg/wso2/siddhi/core/event/ComplexEvent;)Ljava/lang/Object;", true);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to take data from an event.
     */
    class PrivateVariableExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface  method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            int[] variablePosition = ((VariableExpressionExecutor) conditionExecutor).getPosition();
            methodVisitor.visitInsn(ICONST_4);
            methodVisitor.visitIntInsn(NEWARRAY, T_INT);
            for (int i = 0; i < 4; i++) {
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitIntInsn(BIPUSH, i);
                methodVisitor.visitIntInsn(BIPUSH, variablePosition[i]);
                methodVisitor.visitInsn(IASTORE);
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to take value from an expression
     */
    class PrivateConstantExpressionExecutorBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            Object constantVariable = ((ConstantExpressionExecutor) conditionExecutor).getValue();
            methodVisitor.visitLdcInsn(constantVariable);
        }
    }

    /**
     * This class generates byte code to add 2 doubles.
     */
    class PrivateAddExpressionExecutorDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((AddExpressionExecutorDouble) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((AddExpressionExecutorDouble) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number",
                        "doubleValue", "()D", false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number",
                            "doubleValue", "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number",
                            "doubleValue", "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number",
                            "doubleValue", "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(DSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(DLOAD, 4);
            }

            methodVisitor.visitInsn(DADD);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                    "(D)Ljava/lang/Double;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to subtract 2 doubles.
     */
    class PrivateSubtractExpressionExecutorDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((SubtractExpressionExecutorDouble) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((SubtractExpressionExecutorDouble) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(DSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            methodVisitor.visitVarInsn(DSTORE, 8);
            if (status == 2) {
                methodVisitor.visitVarInsn(DLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(DLOAD, 4);
            }

            methodVisitor.visitVarInsn(DLOAD, 8);
            methodVisitor.visitInsn(DSUB);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                    "(D)Ljava/lang/Double;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to divide 2 doubles.
     */
    class PrivateDivideExpressionExecutorDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((DivideExpressionExecutorDouble) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((DivideExpressionExecutorDouble) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(DSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            methodVisitor.visitVarInsn(DSTORE, 8);
            methodVisitor.visitVarInsn(DLOAD, 8);
            methodVisitor.visitInsn(DCONST_0);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);


            if (status == 2) {
                methodVisitor.visitVarInsn(DLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(DLOAD, 4);
            }

            methodVisitor.visitVarInsn(DLOAD, 8);
            methodVisitor.visitInsn(DDIV);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                    "(D)Ljava/lang/Double;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to mod 2 doubles.
     */
    class PrivateModExpressionExecutorDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((ModExpressionExecutorDouble) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((ModExpressionExecutorDouble) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(DSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            methodVisitor.visitVarInsn(DSTORE, 8);
            methodVisitor.visitVarInsn(DLOAD, 8);
            methodVisitor.visitInsn(DCONST_0);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);


            if (status == 2) {
                methodVisitor.visitVarInsn(DLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(DLOAD, 4);
            }

            methodVisitor.visitVarInsn(DLOAD, 8);
            methodVisitor.visitInsn(DREM);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                    "(D)Ljava/lang/Double;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to divide 2 floats.
     */
    class PrivateDivideExpressionExecutorFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((DivideExpressionExecutorFloat) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((DivideExpressionExecutorFloat) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(FSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            methodVisitor.visitVarInsn(FSTORE, 8);
            methodVisitor.visitVarInsn(FLOAD, 8);
            methodVisitor.visitInsn(FCONST_0);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(FLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(FLOAD, 4);
            }

            methodVisitor.visitVarInsn(FLOAD, 8);
            methodVisitor.visitInsn(FDIV);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                    "(F)Ljava/lang/Float;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to mod 2 floats.
     */
    class PrivateModExpressionExecutorFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((ModExpressionExecutorFloat) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((ModExpressionExecutorFloat) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(FSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            methodVisitor.visitVarInsn(FSTORE, 8);
            methodVisitor.visitVarInsn(FLOAD, 8);
            methodVisitor.visitInsn(FCONST_0);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(FLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(FLOAD, 4);
            }

            methodVisitor.visitVarInsn(FLOAD, 8);
            methodVisitor.visitInsn(FREM);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                    "(F)Ljava/lang/Float;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to divide 2 long values.
     */
    class PrivateDivideExpressionExecutorLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((DivideExpressionExecutorLong) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((DivideExpressionExecutorLong) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(LSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            methodVisitor.visitVarInsn(LSTORE, 8);
            methodVisitor.visitVarInsn(LLOAD, 8);
            methodVisitor.visitInsn(LCONST_0);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(LLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(LLOAD, 4);
            }

            methodVisitor.visitVarInsn(LLOAD, 8);
            methodVisitor.visitInsn(LDIV);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                    "(J)Ljava/lang/Long;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to mod 2 long values.
     */
    class PrivateModExpressionExecutorLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((ModExpressionExecutorLong) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((ModExpressionExecutorLong) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(LSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            methodVisitor.visitVarInsn(LSTORE, 8);
            methodVisitor.visitVarInsn(LLOAD, 8);
            methodVisitor.visitInsn(LCONST_0);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(LLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(LLOAD, 4);
            }

            methodVisitor.visitVarInsn(LLOAD, 8);
            methodVisitor.visitInsn(LREM);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                    "(J)Ljava/lang/Long;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to divide 2 Integers.
     */
    class PrivateDivideExpressionExecutorIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((DivideExpressionExecutorInt) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((DivideExpressionExecutorInt) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 6);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            methodVisitor.visitVarInsn(ISTORE, 8);
            methodVisitor.visitVarInsn(ILOAD, 8);
            methodVisitor.visitInsn(ICONST_0);
            //methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IF_ICMPEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(ILOAD, 6);
            } else {
                methodVisitor.visitVarInsn(ILOAD, 4);
            }

            methodVisitor.visitVarInsn(ILOAD, 8);
            methodVisitor.visitInsn(IDIV);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                    "(I)Ljava/lang/Integer;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to mod 2 Integers.
     */
    class PrivateModExpressionExecutorIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((ModExpressionExecutorInt) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((ModExpressionExecutorInt) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 6);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            methodVisitor.visitVarInsn(ISTORE, 8);
            methodVisitor.visitVarInsn(ILOAD, 8);
            methodVisitor.visitInsn(ICONST_0);
            //methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IF_ICMPEQ, l0);

            if (status == 2) {
                methodVisitor.visitVarInsn(ILOAD, 6);
            } else {
                methodVisitor.visitVarInsn(ILOAD, 4);
            }

            methodVisitor.visitVarInsn(ILOAD, 8);
            methodVisitor.visitInsn(IREM);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                    "(I)Ljava/lang/Integer;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to multiply 2 doubles.
     */
    class PrivateMultiplyExpressionExecutorDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((MultiplyExpressionExecutorDouble) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((MultiplyExpressionExecutorDouble) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(DSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "doubleValue",
                            "()D", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(DLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(DLOAD, 4);
            }

            methodVisitor.visitInsn(DMUL);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                    "(D)Ljava/lang/Double;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to add 2 floats.
     */
    class PrivateAddExpressionExecutorFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((AddExpressionExecutorFloat) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((AddExpressionExecutorFloat) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(FSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                        "()F", false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(FLOAD, 4);
            }

            methodVisitor.visitInsn(FADD);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                    "(F)Ljava/lang/Float;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to subtract 2 floats.
     */
    class PrivateSubtractExpressionExecutorFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((SubtractExpressionExecutorFloat) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((SubtractExpressionExecutorFloat) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(FSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            methodVisitor.visitVarInsn(FSTORE, 8);
            if (status == 2) {
                methodVisitor.visitVarInsn(FLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(FLOAD, 4);
            }

            methodVisitor.visitVarInsn(FLOAD, 8);
            methodVisitor.visitInsn(FSUB);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                    "(F)Ljava/lang/Float;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to multiply 2 floats.
     */
    class PrivateMultiplyExpressionExecutorFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((MultiplyExpressionExecutorFloat) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((MultiplyExpressionExecutorFloat) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(FSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "floatValue",
                            "()F", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(FLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(FLOAD, 4);
            }

            methodVisitor.visitInsn(FMUL);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                    "(F)Ljava/lang/Float;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to add 2 Integers.
     */
    class PrivateAddExpressionExecutorIntBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((AddExpressionExecutorInt) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((AddExpressionExecutorInt) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 6);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ILOAD, 6);
            } else {
                methodVisitor.visitVarInsn(ILOAD, 4);
            }

            methodVisitor.visitInsn(IADD);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                    "(I)Ljava/lang/Integer;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to subtract 2 Integers.
     */
    class PrivateSubtractExpressionExecutorIntBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((SubtractExpressionExecutorInt) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((SubtractExpressionExecutorInt) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 6);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            methodVisitor.visitVarInsn(ISTORE, 8);
            if (status == 2) {
                methodVisitor.visitVarInsn(ILOAD, 6);
            } else {
                methodVisitor.visitVarInsn(ILOAD, 4);
            }

            methodVisitor.visitVarInsn(ILOAD, 8);
            methodVisitor.visitInsn(ISUB);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                    "(I)Ljava/lang/Integer;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to multiply 2 Integers.
     */
    class PrivateMultiplyExpressionExecutorIntBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((MultiplyExpressionExecutorInt) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((MultiplyExpressionExecutorInt) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 6);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                } else if (constantVariable instanceof Long) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                            "(J)Ljava/lang/Long;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "intValue",
                            "()I", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(ILOAD, 6);
            } else {
                methodVisitor.visitVarInsn(ILOAD, 4);
            }

            methodVisitor.visitInsn(IMUL);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                    "(I)Ljava/lang/Integer;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to add 2 Long values.
     */
    class PrivateAddExpressionExecutorLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((AddExpressionExecutorLong) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((AddExpressionExecutorLong) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(LSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(LLOAD, 4);
            }

            methodVisitor.visitInsn(LADD);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                    "(J)Ljava/lang/Long;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to subtract 2 Long values.
     */
    class PrivateSubtractExpressionExecutorLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((SubtractExpressionExecutorLong) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((SubtractExpressionExecutorLong) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(LSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            methodVisitor.visitVarInsn(LSTORE, 8);
            if (status == 2) {
                methodVisitor.visitVarInsn(LLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(LLOAD, 4);
            }

            methodVisitor.visitVarInsn(LLOAD, 8);
            methodVisitor.visitInsn(LSUB);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                    "(J)Ljava/lang/Long;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code to multiply 2 Long values.
     */
    class PrivateMultiplyExpressionExecutorLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         **/
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus,
                             MethodVisitor methodVisitor, ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((MultiplyExpressionExecutorLong) conditionExecutor).getLeftExpressionExecutor();
            ExpressionExecutor right = ((MultiplyExpressionExecutorLong) conditionExecutor).getRightExpressionExecutor();
            byteCodeGenarator.execute(left, status, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            Label l1 = new Label();
            int index = 2;
            if (status == 2) {
                index = 3;
            }

            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) left).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LSTORE, 6);
            } else {
                methodVisitor.visitVarInsn(LSTORE, 4);
            }

            byteCodeGenarator.execute(right, status, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, index);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J",
                        false);
            } else {
                Object constantVariable = ((ConstantExpressionExecutor) right).getValue();
                if (constantVariable instanceof Integer) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Double) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
                            "(D)Ljava/lang/Double;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                } else if (constantVariable instanceof Float) {
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf",
                            "(F)Ljava/lang/Float;", false);
                    methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Number", "longValue",
                            "()J", false);
                }
            }

            if (status == 2) {
                methodVisitor.visitVarInsn(LLOAD, 6);
            } else {
                methodVisitor.visitVarInsn(LLOAD, 4);
            }

            methodVisitor.visitInsn(LMUL);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
                    "(J)Ljava/lang/Long;", false);
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ASTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ASTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with float on left and double on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with float on left and double on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with float on left and float on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with float on left and float on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with float on left and int on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with float on left and int on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with float on left and long on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with float on left and long on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with int on left and float on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with int on left and float on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with long on left and float on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with long on left and float on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with double on left and double on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with double on left and double on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with int on left and double on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with int on left and double on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D"
                        , false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with int on left and long on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with int on left and long on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with long on left and double on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with long on left and double on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with long on left and int on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with long on left and int on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with long on left and long on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with long on left and long on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with int on left and int on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with int on left and int on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with double on left and int on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with double on left and int on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with double on left and long on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with double on left and long on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">" operator with double on left and float on right.
     */
    class PrivateGreaterThanCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for ">=" operator with double on left and float on right.
     */
    class PrivateGreaterThanEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {
        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            methodVisitor.visitCode();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFLT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with float on left and double on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with float on left and double on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with float on left and long on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPG);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with float on left and long on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPG);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with long on left and long on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with long on left and long on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with long on left and double on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with long on left and double on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with int on left and double on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with int on left and double on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPG);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with int on left and long on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with int on left and long on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with int on left and int on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with int on left and int on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with long on left and int on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with long on left and int on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with int on left and float on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with int on left and float on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with long on left and float on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with long on left and float on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with float on left and int on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with float on left and int on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorFloatIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with float on left and float on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with float on left and float on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with double on left and double on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with double on left and double on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with double on left and long on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with double on left and long on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with double on left and int on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with double on left and int on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<" operator with double on left and float on right.
     */
    class PrivateLessThanCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "<=" operator with double on left and float on right.
     */
    class PrivateLessThanEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((LessThanEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFGT, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with boolean on left and boolean on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorBoolBoolBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorBoolBool) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorBoolBool) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with boolean on left and boolean on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorBoolBoolBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorBoolBool) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorBoolBool) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with double on left and double on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with double on left and double on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorDoubleDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorDoubleDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with double on left and float on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with double on left and float on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorDoubleFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorDoubleFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with double on left and int on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with double on left and int on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorDoubleIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorDoubleInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with double on left and long on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with double on left and long on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorDoubleLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorDoubleLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(DLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with float on left and double on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with float on left and double on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with long on left and double on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with long on left and double on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorLongDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorLongDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2D);
            methodVisitor.visitVarInsn(DLOAD, 6);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with long on left and float on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with long on left and float on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorLongFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorLongFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with long on left and int on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with long on left and int on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorLongIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorLongInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with long on left and long on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with long on left and long on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorLongLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorLongLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 4);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 6);
            methodVisitor.visitVarInsn(LLOAD, 4);
            methodVisitor.visitVarInsn(LLOAD, 6);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with int on left and double on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with int on left and double on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorIntegerDoubleBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorIntDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
            }
            methodVisitor.visitVarInsn(DSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with int on left and float on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with int on left and float on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorIntegerFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorIntFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with int on left and int on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with int on left and int on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorIntegerIntegerBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorIntInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IF_ICMPEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with int on left and long on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with int on left and long on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorIntegerLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorIntLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitInsn(I2L);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(LCMP);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with float on left and float on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with float on left and float on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorFloatFloatBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorFloatFloat) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(FLOAD, 3);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with float on left and int on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorFloatIntBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with float on left and int on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorFloatIntBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorFloatInt) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I",
                        false);
            }
            methodVisitor.visitVarInsn(ISTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitInsn(I2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with float on left and long on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with float on left and long on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorFloatLongBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorFloatLong) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
            }
            methodVisitor.visitVarInsn(FSTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J",
                        false);
            }
            methodVisitor.visitVarInsn(LSTORE, 3);
            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitVarInsn(LLOAD, 3);
            methodVisitor.visitInsn(L2F);
            methodVisitor.visitInsn(FCMPL);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "==" operator with String on left and String on right.
     */
    class PrivateEqualCompareConditionExpressionExecutorStringStringBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((EqualCompareConditionExpressionExecutorStringString) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((EqualCompareConditionExpressionExecutorStringString) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            }
            methodVisitor.visitVarInsn(ASTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            }
            methodVisitor.visitVarInsn(ASTORE, 3);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitJumpInsn(IF_ACMPNE, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }

    /**
     * This class generates byte code for "!=" operator with String on left and String on right.
     */
    class PrivateNotEqualCompareConditionExpressionExecutorStringStringBytecodeEmitter implements ByteCodeEmitter {

        /**
         * This method overrides the interface method.
         *
         * @param conditionExecutor
         * @param status
         * @param parent
         * @param specialCase
         * @param parentStatus
         * @param methodVisitor
         * @param byteCodeGenarator
         */
        @Override
        public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                             Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                             ByteCodeGenarator byteCodeGenarator) {
            ExpressionExecutor left = ((NotEqualCompareConditionExpressionExecutorStringString) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((NotEqualCompareConditionExpressionExecutorStringString) conditionExecutor)
                    .getRightExpressionExecutor();
            byteCodeGenarator.execute(left, 1, 0, null, status, methodVisitor, byteCodeGenarator);
            Label l0 = new Label();
            if (!(left instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            }
            methodVisitor.visitVarInsn(ASTORE, 2);
            byteCodeGenarator.execute(right, 2, 0, null, status, methodVisitor, byteCodeGenarator);
            if (!(right instanceof ConstantExpressionExecutor)) {
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitJumpInsn(IFNULL, l0);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
            }
            methodVisitor.visitVarInsn(ASTORE, 3);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitJumpInsn(IF_ACMPEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            if (status == 2) {
                methodVisitor.visitVarInsn(ISTORE, 3);
            } else {
                methodVisitor.visitVarInsn(ISTORE, 2);
            }
        }
    }
}
