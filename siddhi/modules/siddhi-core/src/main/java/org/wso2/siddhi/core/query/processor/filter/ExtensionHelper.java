package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.executor.ExpressionExecutor;

import java.util.ArrayList;

public interface ExtensionHelper {
    public void setUnknownExpressionExecutors(ArrayList<ExpressionExecutor> unknownExpressionExecutors);
}
