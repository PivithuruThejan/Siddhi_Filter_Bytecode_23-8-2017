package org.wso2.siddhi.core.query.processor.filter;

import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.AndConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.NotConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.OrConditionExpressionExecutor;
import org.wso2.siddhi.core.executor.condition.compare.greaterthan.GreaterThanCompareConditionExpressionExecutorFloatDouble;
import org.wso2.siddhi.core.executor.condition.compare.lessthan.LessThanCompareConditionExpressionExecutorFloatDouble;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the class that generates byte code for ExpressionExecutor.
 */
public class ByteCodeGenarator {

    static Map<Class<? extends ExpressionExecutor>, ByteCodeEmitter> byteCodegenerators;
    static byte[] byteArray;
    static ExpressionExecutor expressionExecutor;

    static {
        byteCodegenerators = new HashMap<Class<? extends ExpressionExecutor>, ByteCodeEmitter>();
        ByteCodeRegistry byteCode = new ByteCodeRegistry();
        byteCodegenerators.put(AndConditionExpressionExecutor.class, byteCode.
                new PrivateANDExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(OrConditionExpressionExecutor.class, byteCode.
                new PrivateORExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(NotConditionExpressionExecutor.class, byteCode.
                new PrivateNOTExpressionExecutorBytecodeEmitter());
        byteCodegenerators.put(GreaterThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateGreaterThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
        byteCodegenerators.put(LessThanCompareConditionExpressionExecutorFloatDouble.class,
                byteCode.new PrivateLessThanCompareConditionExpressionExecutorFloatDoubleBytecodeEmitter());
    }

    private ClassWriter classWriter;
    private ByteCodeHelper byteCodeHelper;

    /**
     * This method returns Expression executor with byte code.
     *
     * @param conditionExecutor
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public ExpressionExecutor build(ExpressionExecutor conditionExecutor) throws InstantiationException,
            IllegalAccessException {
        this.byteCodeHelper = new ByteCodeHelper();
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor methodVisitor = byteCodeHelper.start(classWriter);
        this.execute(conditionExecutor, 0, 0, null, 0, methodVisitor,
                this);
        this.byteCodeHelper.end(classWriter, methodVisitor);
        return ByteCodeGenarator.expressionExecutor;
    }

    /**
     * This method generates byte code for a specific expression.
     *
     * @param conditionExecutor
     * @param status
     * @param parent
     * @param specialCase
     * @param parentStatus
     * @param methodVisitor
     * @param byteCodeGenarator
     */
    public void execute(ExpressionExecutor conditionExecutor, int status, int parent,
                        Label specialCase, int parentStatus, MethodVisitor methodVisitor,
                        ByteCodeGenarator byteCodeGenarator) {
        ByteCodeGenarator.byteCodegenerators.get(conditionExecutor.getClass()).generate(conditionExecutor, status, parent,
                specialCase, parentStatus, methodVisitor, byteCodeGenarator);
    }
}
