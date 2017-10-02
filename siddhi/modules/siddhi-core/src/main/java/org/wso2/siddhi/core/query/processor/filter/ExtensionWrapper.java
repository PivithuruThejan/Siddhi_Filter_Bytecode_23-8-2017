package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.lang.reflect.InvocationTargetException;

/**
 * This class maps all extensions in to single byte-code Emitter.
 */
public class ExtensionWrapper implements ExpressionExecutor {

    private ExpressionExecutor conditionExecutor;

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

    public ExpressionExecutor getConditionExecutor() {
        return conditionExecutor;
    }

    public void setConditionExecutor(ExpressionExecutor conditionExecutor) {
        this.conditionExecutor = conditionExecutor;
    }
}
