package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Test implements ExpressionExecutor, ExtensionHelper {
    private ArrayList<ExpressionExecutor> unknownExpressionExecutors;

    @Override
    public Object execute(ComplexEvent event) throws IllegalAccessException, InstantiationException, InvocationTargetException {
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

    @Override
    public void setUnknownExpressionExecutors(ArrayList<ExpressionExecutor> unknownExpressionExecutors) {
        this.unknownExpressionExecutors = unknownExpressionExecutors;
    }

    public void it(){
       FunctionExecutor expressionExecutor = (FunctionExecutor) this.unknownExpressionExecutors.get(1);
       System.out.println(expressionExecutor);
    }
}
