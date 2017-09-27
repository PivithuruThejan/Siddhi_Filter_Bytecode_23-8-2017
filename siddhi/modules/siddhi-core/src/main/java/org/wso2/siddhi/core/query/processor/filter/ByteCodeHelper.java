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

import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;

import static org.mvel2.asm.Opcodes.*;
import static org.mvel2.asm.Opcodes.ICONST_0;
import static org.mvel2.asm.Opcodes.ISTORE;

/**
 * This class creates "ClassWriter" and Instantiates an object of class written using byte code.
 */
public class ByteCodeHelper {

    /**
     * This method creates a class called "ByteCodeRegistry" using byte code.
     *
     * @param classWriter
     * @return
     */
    public MethodVisitor start(ClassWriter classWriter) {
        MethodVisitor methodVisitor;
        classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, "ByteCodeRegistry", null,
                "java/lang/Object", new String[]
                        {"org/wso2/siddhi/core/executor/ExpressionExecutor"});
        classWriter.visitSource("ByteCodeRegistry.java", null);

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
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "execute",
                    "(Lorg/wso2/siddhi/core/event/ComplexEvent;)Ljava/lang/Object;",
                    null, null);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 2);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 3);
        }

        return methodVisitor;
    }

    /**
     * This method finishes byte code generation and creates an instance of "ByteCodeRegistry" class.
     *
     * @param classWriter
     * @param methodVisitor
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void end(ClassWriter classWriter, MethodVisitor methodVisitor) throws IllegalAccessException,
            InstantiationException {
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf",
                "(Z)Ljava/lang/Boolean;", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(4, 12);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        ByteCodeGenarator.byteArray = classWriter.toByteArray();
        OptimizedExpressionExecutorClassLoader optimizedExpressionExecutorClassLoader = new
                OptimizedExpressionExecutorClassLoader();//Instantiates the ClassLoader
        Class regeneratedClass = optimizedExpressionExecutorClassLoader
                .defineClass("ByteCodeRegistry", ByteCodeGenarator.byteArray);
        ByteCodeGenarator.expressionExecutor = (ExpressionExecutor) regeneratedClass.newInstance();
    }
}
