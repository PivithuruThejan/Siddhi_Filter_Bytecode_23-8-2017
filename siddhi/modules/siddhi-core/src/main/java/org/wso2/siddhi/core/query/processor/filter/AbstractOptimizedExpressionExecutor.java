package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.event.ComplexEvent;
import java.lang.reflect.InvocationTargetException;

public interface AbstractOptimizedExpressionExecutor {
    public  boolean optimizedExecuteWithByteCode(ComplexEvent complexEvent) throws IllegalAccessException,
            InstantiationException, InvocationTargetException;
}
