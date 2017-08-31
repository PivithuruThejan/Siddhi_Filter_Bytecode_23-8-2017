package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;

public interface ByteCodeGenerator {
    public boolean generate(ExpressionExecutor conditionExecutor, ComplexEvent complexEvent, int status, int parent, Label specialCase, int parentStatus,
                            MethodVisitor methodVisitor, FilterProcessor filterProcessor);
}
