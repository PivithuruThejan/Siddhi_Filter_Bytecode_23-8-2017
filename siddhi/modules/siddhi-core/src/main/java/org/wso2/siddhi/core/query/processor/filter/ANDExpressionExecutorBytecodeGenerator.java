package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;

import static jdk.internal.org.objectweb.asm.Opcodes.GETSTATIC;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ISTORE;

public class ANDExpressionExecutorBytecodeGenerator implements ByteCodeGenerator{

    @Override
    public void generate(ExpressionExecutor conditionExecutor, int status, int parent, Label specialCase, int parentStatus,
                         MethodVisitor methodVisitor, FilterProcessor filterProcessor) {
        ExpressionExecutor left = ((AndConditionExpressionExecutor) conditionExecutor).getLeftConditionExecutor();
        ExpressionExecutor right = ((AndConditionExpressionExecutor) conditionExecutor).getRightConditionExecutor();
        Label l0 = new Label();
        Label l1 = new Label();
        Label l2 = new Label();
        filterProcessor.execute(left, 1, 1, l1, status, methodVisitor, filterProcessor);
        //leftResult = execute(left, complexEvent, 1, 1, l1, status);
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
        filterProcessor.execute(right, 2, 1, l1, status, methodVisitor, filterProcessor);
        //rightResult = execute(right, complexEvent, 2, 1, l1, status);
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

    }
}
