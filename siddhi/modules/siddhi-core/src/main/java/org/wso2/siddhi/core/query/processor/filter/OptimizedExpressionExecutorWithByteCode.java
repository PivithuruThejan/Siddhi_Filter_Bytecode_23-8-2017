package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.mvel2.asm.Opcodes.*;

public class OptimizedExpressionExecutorWithByteCode {
    public static void main(String args[]) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor methodVisitor;
        classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, "ByteCode", null, "java/lang/Object",
                null);
        classWriter.visitSource("ByteCode.java", null);
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "optimizedExecute",
                    "(Lorg/wso2/siddhi/core/executor/ExpressionExecutor;Lorg/wso2/siddhi/core/event/ComplexEvent;)Z",
                    null, null);
            methodVisitor.visitCode();
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 1);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 2);
            methodVisitor.visitVarInsn(ILOAD, 1);
            Label l0 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitVarInsn(ILOAD, 2);
            methodVisitor.visitJumpInsn(IFEQ, l0);
            methodVisitor.visitInsn(ICONST_1);
            Label l1 = new Label();
            methodVisitor.visitJumpInsn(GOTO, l1);
            methodVisitor.visitLabel(l0);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(l1);
            methodVisitor.visitInsn(IRETURN);
            methodVisitor.visitMaxs(1, 3);
            methodVisitor.visitEnd();
        }

        byte[] byteCode = classWriter.toByteArray();
        OptimizedExpressionExecutorClassLoader optimizedExpressionExecutorClassLoader = new
                OptimizedExpressionExecutorClassLoader();
        Class regeneratedClass = optimizedExpressionExecutorClassLoader.defineClass("ByteCode", byteCode);
        Object object = regeneratedClass.newInstance();
        Method[] methods = regeneratedClass.getMethods();
        ExpressionExecutor expressionExecutor = new ExpressionExecutor() {
            @Override
            public Object execute(ComplexEvent event) throws IllegalAccessException, InstantiationException,
                    InvocationTargetException {
                return null;
            }

            @Override
            public Attribute.Type getReturnType() {
                return null;
            }

            @Override
            public ExpressionExecutor cloneExecutor(String key) {
                return null;
            }
        };

        ComplexEvent complexEvent = new ComplexEvent() {
            @Override
            public ComplexEvent getNext() {
                return null;
            }

            @Override
            public void setNext(ComplexEvent events) {

            }

            @Override
            public Object[] getOutputData() {
                return new Object[0];
            }

            @Override
            public void setOutputData(Object object, int index) {

            }

            @Override
            public long getTimestamp() {
                return 0;
            }

            @Override
            public Object getAttribute(int[] position) {
                return null;
            }

            @Override
            public void setAttribute(Object object, int[] position) {

            }

            @Override
            public Type getType() {
                return null;
            }

            @Override
            public void setType(Type type) {

            }
        };

        boolean result = (boolean) methods[0].invoke(object, expressionExecutor, complexEvent);
        System.out.println(result);
    }
}
