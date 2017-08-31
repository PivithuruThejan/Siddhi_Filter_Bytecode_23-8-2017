package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;

import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class ByteCode {
    class PrivateANDExpressionExecutorBytecodeGenerator implements ByteCodeGenerator {

        @Override
        public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent,
                                Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                                FilterProcessor filterProcessor) {
            ExpressionExecutor left = ((AndConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((AndConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            boolean leftResult;
            boolean rightResult;
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            leftResult = filterProcessor.execute(left, complexEvent, 1, 1, l1, status, methodVisitor, filterProcessor);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitJumpInsn(IFNE, l0);
            methodVisitor.visitInsn(ICONST_0);
            if (parent == 1) {
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
            rightResult = filterProcessor.execute(right, complexEvent, 2, 1, l1, status, methodVisitor, filterProcessor);
            methodVisitor.visitVarInsn(ILOAD, 3);
            methodVisitor.visitJumpInsn(IFNE, l2);
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
            return leftResult && rightResult;
        }
    }

    class PrivateORExpressionExecutorBytecodeGenerator implements ByteCodeGenerator {

        @Override
        public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent,
                                Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                                FilterProcessor filterProcessor) {
            ExpressionExecutor left = ((OrConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
            ExpressionExecutor right = ((OrConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            boolean leftResult;
            boolean rightResult;
            leftResult = filterProcessor.execute(left, complexEvent, 1, 2, l1, status, methodVisitor, filterProcessor);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            if (parent == 2) {
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
            rightResult = filterProcessor.execute(right, complexEvent, 2, 2, l1, status, methodVisitor, filterProcessor);
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
            return leftResult || rightResult;
        }
    }

    class PrivateNOTExpressionExecutorBytecodeGenerator implements ByteCodeGenerator {

        @Override
        public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent, Label specialCase, int parentStatus,
                                MethodVisitor methodVisitor, FilterProcessor filterProcessor) {
            ExpressionExecutor condition = ((NotConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
            Label l0 = new Label();
            Label l1 = new Label();
            boolean result = filterProcessor.execute(condition, complexEvent, 1, 3, l1, status, methodVisitor, filterProcessor);
            methodVisitor.visitVarInsn(ILOAD, 2);
            if (parent == 3) {
                if (parentStatus == 2) {
                    methodVisitor.visitVarInsn(ISTORE, 3);
                } else {
                    methodVisitor.visitVarInsn(ISTORE, 2);
                }

                methodVisitor.visitJumpInsn(GOTO, specialCase);
            } else {
                methodVisitor.visitJumpInsn(IFNE, l0);
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
            return !result;
        }
    }

    class PrivateGreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator implements ByteCodeGenerator {

        @Override
        public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent,
                                Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                                FilterProcessor filterProcessor) {
            ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            Float leftVariable = null;
            Double rightVariable = null;
            int[] leftPosition = null;
            int[] rightPosition = null;
            methodVisitor.visitCode();
            if (left instanceof VariableExpressionExecutor) {
                leftPosition = ((VariableExpressionExecutor) left).getPosition();
                leftVariable = (Float) complexEvent.getAttribute(((VariableExpressionExecutor) left).getPosition());
                methodVisitor.visitInsn(ICONST_4);
                methodVisitor.visitIntInsn(NEWARRAY, T_INT);
                for (int i = 0; i < 4; i++) {
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitIntInsn(BIPUSH, i);
                    methodVisitor.visitIntInsn(BIPUSH, leftPosition[i]);
                    methodVisitor.visitInsn(IASTORE);
                }
                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
                methodVisitor.visitVarInsn(FSTORE, 2);
            } else if (left instanceof ConstantExpressionExecutor) {
                leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
                methodVisitor.visitLdcInsn(new Float(leftVariable));
                methodVisitor.visitVarInsn(FSTORE, 2);
            }

            if (right instanceof VariableExpressionExecutor) {
                rightPosition = ((VariableExpressionExecutor) right).getPosition();
                rightVariable = (Double) complexEvent.getAttribute(((VariableExpressionExecutor) right).getPosition());
                methodVisitor.visitInsn(ICONST_4);
                methodVisitor.visitIntInsn(NEWARRAY, T_INT);
                for (int i = 0; i < 4; i++) {
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitIntInsn(BIPUSH, i);
                    methodVisitor.visitIntInsn(BIPUSH, rightPosition[i]);
                    methodVisitor.visitInsn(IASTORE);
                }

                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
                methodVisitor.visitVarInsn(DSTORE, 3);
            } else if (right instanceof ConstantExpressionExecutor) {
                rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
                methodVisitor.visitLdcInsn(new Double(rightVariable));
                methodVisitor.visitVarInsn(DSTORE, 3);
            }

            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPL);
            Label l0 = new Label();
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

            return leftVariable > rightVariable;
        }
    }

    class PrivateLessThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator implements ByteCodeGenerator {

        @Override
        public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent,
                                Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                                FilterProcessor filterProcessor) {
            ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getLeftExpressionExecutor();
            ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                    .getRightExpressionExecutor();
            Float leftVariable = null;
            Double rightVariable = null;
            int[] leftPosition = null;
            int[] rightPosition = null;
            if (left instanceof VariableExpressionExecutor) {
                leftPosition = ((VariableExpressionExecutor) left).getPosition();
                leftVariable = (Float) complexEvent.getAttribute(((VariableExpressionExecutor) left).getPosition());
                methodVisitor.visitInsn(ICONST_4);
                methodVisitor.visitIntInsn(NEWARRAY, T_INT);
                for (int i = 0; i < 4; i++) {
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitIntInsn(BIPUSH, i);
                    methodVisitor.visitIntInsn(BIPUSH, leftPosition[i]);
                    methodVisitor.visitInsn(IASTORE);
                }

                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                        false);
                methodVisitor.visitVarInsn(FSTORE, 2);
            } else if (left instanceof ConstantExpressionExecutor) {
                leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
                methodVisitor.visitLdcInsn(new Float(leftVariable));
                methodVisitor.visitVarInsn(FSTORE, 2);
            }

            if (right instanceof VariableExpressionExecutor) {
                rightPosition = ((VariableExpressionExecutor) right).getPosition();
                rightVariable = (Double) complexEvent.getAttribute(((VariableExpressionExecutor) right).getPosition());
                methodVisitor.visitInsn(ICONST_4);
                methodVisitor.visitIntInsn(NEWARRAY, T_INT);
                for (int i = 0; i < 4; i++) {
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitIntInsn(BIPUSH, i);
                    methodVisitor.visitIntInsn(BIPUSH, rightPosition[i]);
                    methodVisitor.visitInsn(IASTORE);
                }

                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitMethodInsn(INVOKEINTERFACE, "org/wso2/siddhi/core/event/ComplexEvent",
                        "getAttribute", "([I)Ljava/lang/Object;", true);
                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                        false);
                methodVisitor.visitVarInsn(DSTORE, 3);
            } else if (right instanceof ConstantExpressionExecutor) {
                rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
                methodVisitor.visitLdcInsn(new Double(rightVariable));
                methodVisitor.visitVarInsn(DSTORE, 3);
            }

            methodVisitor.visitVarInsn(FLOAD, 2);
            methodVisitor.visitInsn(F2D);
            methodVisitor.visitVarInsn(DLOAD, 3);
            methodVisitor.visitInsn(DCMPG);
            Label l0 = new Label();
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

            return leftVariable < rightVariable;
        }
    }


}
