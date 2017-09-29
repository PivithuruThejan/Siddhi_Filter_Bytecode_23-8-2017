package org.wso2.siddhi.core.query.processor.filter;

import org.wso2.siddhi.core.executor.ExpressionExecutor;

import java.util.ArrayList;

/**
 * Interface to set ArrayList of ExpressionExecutors.
 */
public interface ExtensionHelper {
    /**
     * @param unknownExpressionExecutors ExpressionExecutors those are written as extensions.
     */
    public void setUnknownExpressionExecutors(ArrayList<ExpressionExecutor> unknownExpressionExecutors);
}
