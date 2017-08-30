package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.util.SiddhiConstants;

import static jdk.internal.org.objectweb.asm.Opcodes.GETSTATIC;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class LessThanCompareConditionExpressionExecutorFloatDoubleBytecodeGenerator implements ByteCodeGenerator {

    @Override
    public void generate(ExpressionExecutor conditionExecutor, int status, int parent, Label specialCase, int parentStatus,
                         MethodVisitor methodVisitor, FilterProcessor filterProcessor) {

        /*methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("less than");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);*/

        ExpressionExecutor left = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                .getLeftExpressionExecutor();
        ExpressionExecutor right = ((LessThanCompareConditionExpressionExecutorFloatDouble) conditionExecutor)
                .getRightExpressionExecutor();
        int[] leftPosition = null;
        int[] rightPosition = null;
        int beforeWindowIndexLeft = 0;
        int beforeWindowIndexRight = 0;
        if (left instanceof VariableExpressionExecutor) {
            leftPosition = ((VariableExpressionExecutor) left).getPosition();
            beforeWindowIndexLeft = leftPosition[SiddhiConstants.STREAM_ATTRIBUTE_INDEX_IN_TYPE];
            //methodVisitor.visitVarInsn(ALOAD, 1);
            //methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/stream/StreamEvent");
            //methodVisitor.visitVarInsn(ASTORE, 2);
            //methodVisitor.visitVarInsn(ALOAD, 2);
            //methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/stream/StreamEvent","getBeforeWindowData", "()[Ljava/lang/Object;", false);
            //methodVisitor.visitVarInsn(ASTORE, 2);
            //methodVisitor.visitVarInsn(ALOAD, 2);
            //methodVisitor.visitIntInsn(BIPUSH, beforeWindowIndexLeft);
            //methodVisitor.visitInsn(AALOAD);
            //methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Float");
            // methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F",false);
            //methodVisitor.visitVarInsn(FSTORE, 2);
        } else if (left instanceof ConstantExpressionExecutor) {
            Float leftVariable = (Float) ((ConstantExpressionExecutor) left).getValue();
            methodVisitor.visitLdcInsn(new Float(leftVariable));
            //methodVisitor.visitVarInsn(FSTORE, 2);
        }
        if (right instanceof VariableExpressionExecutor) {
            rightPosition = ((VariableExpressionExecutor) right).getPosition();
            beforeWindowIndexRight = rightPosition[SiddhiConstants.STREAM_ATTRIBUTE_INDEX_IN_TYPE];
            //methodVisitor.visitVarInsn(ALOAD, 1);
            //methodVisitor.visitTypeInsn(CHECKCAST, "org/wso2/siddhi/core/event/stream/StreamEvent");
            //methodVisitor.visitVarInsn(ASTORE, 3);
            //methodVisitor.visitVarInsn(ALOAD, 3);
            //methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/wso2/siddhi/core/event/stream/StreamEvent","getBeforeWindowData", "()[Ljava/lang/Object;", false);
            //methodVisitor.visitVarInsn(ASTORE, 3);
            //methodVisitor.visitVarInsn(ALOAD, 3);
            //methodVisitor.visitIntInsn(BIPUSH, beforeWindowIndexRight);
            //methodVisitor.visitInsn(AALOAD);
            //methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Double");
            //methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D",false);
            //methodVisitor.visitVarInsn(DSTORE, 3);

        } else if (right instanceof ConstantExpressionExecutor) {
            Double rightVariable = (Double) ((ConstantExpressionExecutor) right).getValue();
            methodVisitor.visitLdcInsn(new Double(rightVariable));
            methodVisitor.visitVarInsn(DSTORE, 3);
        }
        //methodVisitor.visitVarInsn(FLOAD, 2);
        //methodVisitor.visitInsn(F2D);
        //methodVisitor.visitVarInsn(DLOAD, 3);
        //methodVisitor.visitInsn(DCMPG);
        //Label l0 = new Label();
        //methodVisitor.visitJumpInsn(IFGE, l0);
        //methodVisitor.visitInsn(ICONST_1);
        //Label l1 = new Label();
        //methodVisitor.visitJumpInsn(GOTO, l1);
        //methodVisitor.visitLabel(l0);
        methodVisitor.visitInsn(ICONST_1);
        //methodVisitor.visitLabel(l1);
        if (status == 2) {
            methodVisitor.visitVarInsn(ISTORE, 3);
        } else {
            methodVisitor.visitVarInsn(ISTORE, 2);
        }

    }
}
