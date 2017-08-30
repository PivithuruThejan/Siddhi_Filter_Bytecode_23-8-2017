package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.util.SiddhiConstants;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class GreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator implements  ByteCodeGenerator {

    @Override
    public void generate(ExpressionExecutor conditionExecutor, int status, int parent, Label specialCase, int parentStatus,
                         MethodVisitor methodVisitor, FilterProcessor filterProcessor) {
        ExpressionExecutor left = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                .getLeftExpressionExecutor();
        ExpressionExecutor right = ((GreaterThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                .getRightExpressionExecutor();
        int[] leftPosition = null;
        int[] rightPosition = null;
        int beforeWindowIndexLeft = 0;
        int beforeWindowIndexRight = 0;
        methodVisitor.visitCode();
        if (left instanceof VariableExpressionExecutor) {
            leftPosition = ((VariableExpressionExecutor) left).getPosition();
            //System.out.println(SiddhiConstants.STREAM_ATTRIBUTE_INDEX_IN_TYPE);
            beforeWindowIndexLeft = leftPosition[SiddhiConstants.STREAM_ATTRIBUTE_INDEX_IN_TYPE];
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/stream/StreamEvent");
            methodVisitor.visitVarInsn(ASTORE, 2);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/stream/StreamEvent",
                    "getBeforeWindowData", "()[Ljava/lang/Object;", false);
            methodVisitor.visitVarInsn(ASTORE, 2);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitIntInsn(BIPUSH, beforeWindowIndexLeft);
            //System.out.println(beforeWindowIndexLeft);
            methodVisitor.visitInsn(AALOAD);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",
                    false);
            methodVisitor.visitVarInsn(FSTORE, 2);
        } else if (left instanceof ConstantExpressionExecutor) {
            Float leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
            methodVisitor.visitLdcInsn(new Float(leftVariable));
            methodVisitor.visitVarInsn(FSTORE, 2);
        }
        if (right instanceof VariableExpressionExecutor) {
            rightPosition = ((VariableExpressionExecutor) right).getPosition();
            beforeWindowIndexRight = rightPosition[SiddhiConstants.STREAM_ATTRIBUTE_INDEX_IN_TYPE];
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/stream/StreamEvent");
            methodVisitor.visitVarInsn(ASTORE, 3);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/stream/StreamEvent",
                    "getBeforeWindowData", "()[Ljava/lang/Object;", false);
            methodVisitor.visitVarInsn(ASTORE, 3);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitIntInsn(BIPUSH, beforeWindowIndexRight);
            methodVisitor.visitInsn(AALOAD);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",
                    false);
            methodVisitor.visitVarInsn(DSTORE, 3);
        } else if (right instanceof ConstantExpressionExecutor) {
            Double rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
            //System.out.println(rightVariable);
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
    }
}
