package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;

/**
 * Interface to generate byte code.
 */
public interface ByteCodeGenerator {
    /**
     * Abstract method to generate byte code.
     *
     * @param conditionExecutor
     * @param status
     * @param parent
     * @param specialCase
     * @param parentStatus
     * @param methodVisitor
     * @param filterProcessor
     */
    public void generate(ExpressionExecutor conditionExecutor, int status, int parent,
                         Label specialCase, int parentStatus,
                         MethodVisitor methodVisitor, FilterProcessor filterProcessor);
}
