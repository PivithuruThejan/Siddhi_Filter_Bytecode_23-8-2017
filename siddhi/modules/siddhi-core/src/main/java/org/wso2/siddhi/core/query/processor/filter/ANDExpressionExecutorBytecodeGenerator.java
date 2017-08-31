package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class ANDExpressionExecutorBytecodeGenerator implements ByteCodeGenerator {

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
        leftResult = filterProcessor.execute(left,complexEvent, 1, 1, l1, status, methodVisitor, filterProcessor);
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
        return  leftResult && rightResult;
    }
}
