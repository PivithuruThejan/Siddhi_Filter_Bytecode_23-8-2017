package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class GreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator implements  ByteCodeGenerator {

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
            for(int i = 0 ; i < 4 ; i++){
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
            for(int i = 0 ; i < 4 ; i++){
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
