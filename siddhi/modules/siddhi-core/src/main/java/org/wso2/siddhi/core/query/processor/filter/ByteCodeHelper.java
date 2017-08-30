package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;

import java.lang.reflect.InvocationTargetException;

import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ICONST_0;
import static org.mvel2.asm.Opcodes.ISTORE;

public class ByteCodeHelper {
    public MethodVisitor start(ClassWriter classWriter){
        MethodVisitor methodVisitor;
        classWriter.visit(52,ACC_PUBLIC+ACC_SUPER,"ByteCode",null,"java/lang/Object",
                new String[]{"org/wso2/siddhi/core/query/processor/filter/AbstractOptimizedExpressionExecutor"});
        classWriter.visitSource("ByteCode.java",null);
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC,"<init>","()V",null,null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD,0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1,1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "optimizedExecuteWithByteCode",
                    "(Lorg/wso2/siddhi/core/event/ComplexEvent;)Z",
                    null, null);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 2);

            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 3);
        }
        return methodVisitor;
    }

    public boolean execute(ComplexEvent complexEvent) throws IllegalAccessException, InvocationTargetException,
            InstantiationException {
        boolean result = FilterProcessor.abstractOptimizedExpressionExecutor.optimizedExecuteWithByteCode( complexEvent);
        return result;

    }

    public void end(ClassWriter classWriter, MethodVisitor methodVisitor) throws IllegalAccessException,
            InstantiationException {
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(IRETURN);
        methodVisitor.visitMaxs(4, 8);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        FilterProcessor.byteArray = classWriter.toByteArray();
        OptimizedExpressionExecutorClassLoader optimizedExpressionExecutorClassLoader = new
                OptimizedExpressionExecutorClassLoader();
        Class regeneratedClass = optimizedExpressionExecutorClassLoader
                .defineClass("ByteCode",FilterProcessor.byteArray);
        FilterProcessor.abstractOptimizedExpressionExecutor = (AbstractOptimizedExpressionExecutor)
                regeneratedClass.newInstance();
    }


}
