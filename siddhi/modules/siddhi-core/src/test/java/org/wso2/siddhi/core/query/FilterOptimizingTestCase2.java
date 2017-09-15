package org.wso2.siddhi.core.query;

import org.junit.Assert;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Tests written for JIT Compilation.
 */
public class FilterOptimizingTestCase2 {

    private static final int COUNT = 1;
    private static int CHECK = 1;

    @Test
    public void testGreaterthanOperatorFloatDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f>ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorFloatDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(36.5f>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorIntegerDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40>ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorIntegerDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(37>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 37.0d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testGreaterthanOperatorIntegerFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40>ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorIntegerFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(37>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 37.0f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testGreaterthanOperatorDoubleDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0>ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorDoubleDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(36.5>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorLongDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L>ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorLongDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(36L>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.0d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanOperatorLongDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(37L>=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 37.0d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorFloatFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorFloatFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33.0f>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testGreaterthanOperatorDoubleFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorDoubleFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33.0>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorLongFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorLongFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 40.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorDoubleIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorDoubleIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33.0>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorDoubleLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorDoubleLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33.0>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorFloatLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorFloatLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33.0f>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorIntegerLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanOperatorIntegerLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorLongLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorLongLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33L>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33L, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47L, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53L, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55L, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41L, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54L, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63L, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51L, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47L, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52L, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32L, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorIntegerIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorIntegerIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorLongIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorLongIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33L>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterthanOperatorFloatIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f>TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testGreaterThanEqualOperatorFloatIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(33f>=TestAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Upul Tharanga", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorFloatDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorLongDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testLessthanOperatorIntegerDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5d, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7d, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6d, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1d, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5d, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5d, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5d, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5d, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5d, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorFloatFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorLongFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorIntegerFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorDoubleDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorDoubleIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorLongIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testLessthanOperatorDoubleLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32L, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42L, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36L, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52L, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43L, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45L, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50L, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52L, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52L, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53L, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42L, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorLongLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32L, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42L, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36L, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52L, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43L, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45L, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50L, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52L, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52L, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53L, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42L, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorIntegerLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32L, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42L, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36L, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52L, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43L, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45L, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50L, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52L, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52L, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53L, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42L, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorFloatLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40f<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32L, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42L, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36L, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52L, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43L, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45L, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50L, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52L, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52L, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53L, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42L, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorIntegerIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testLessthanOperatorFloatIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0f<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, 32, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 42, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4f, 36, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5f, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5f, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7f, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5f, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1f, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5f, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5f, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5f, 42, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testLessthanOperatorDoubleFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40.0<ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2f, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2f, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3f, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9f, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8f, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0f, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0f, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
        for (int i = 1; i <= COUNT; i++) {
            inputHandler.send(o1);
            inputHandler.send(o2);
            inputHandler.send(o3);
            inputHandler.send(o4);
            inputHandler.send(o5);
            inputHandler.send(o6);
            inputHandler.send(o7);
            inputHandler.send(o8);
            inputHandler.send(o9);
            inputHandler.send(o10);
            inputHandler.send(o11);
        }

        long end = System.currentTimeMillis();
        System.out.println("XTime: " + (end - start) + " ms.");
        siddhiAppRuntime.shutdown();
    }
}
