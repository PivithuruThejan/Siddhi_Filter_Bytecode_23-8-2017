package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;

import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ICONST_0;
import static org.mvel2.asm.Opcodes.ISTORE;

public class NOTExpressionExecutorBytecodeGenerator implements ByteCodeGenerator {

    @Override
    public void generate(ExpressionExecutor conditionExecutor, int status, int parent, Label specialCase, int parentStatus,
                         MethodVisitor methodVisitor, FilterProcessor filterProcessor) {
        ExpressionExecutor condition = ((NotConditionExpressionExecutor) conditionExecutor).getConditionExecutor();
        Label l0 = new Label();
        Label l1 = new Label();
        filterProcessor.execute(condition, 1, 3, l1, status, methodVisitor, filterProcessor);
        //boolean result = execute(condition, complexEvent, 1, 3, l1, status);
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

    }
}
