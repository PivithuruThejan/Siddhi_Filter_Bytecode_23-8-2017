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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, 34.0, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7f, 57.4f, 36.5d, 85.5f, 36.3f, 146.7f, "RHB"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8f, 52.5f, null, 88.3f, 24.9f, 128.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, null, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
    public void testGreaterThanEqualOperatorIntegerLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3f, 42.1d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorFloatDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.1f<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorLongDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 40.0d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorIntegerDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 40.0d, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorFloatFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.1f<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorLongFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40L<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 40.0f, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorIntegerFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6f, 65.3f, 40.0f, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorDoubleDoubleRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage double,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.1<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorDoubleIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.0<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorLongIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42L<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorDoubleLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.0<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorLongLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42L<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorIntegerLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorFloatLongRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42f<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorIntegerIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(40<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47, 65.3f, 40, 83.4f, 26.3f, 136.3f, "RHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorFloatIntegerRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate float,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.0f<=ODIAverage )]" +
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testLessThanEqualOperatorDoubleFloatRightSideVariableExpressionExecutorLeftSideConstantExpressionExecutor()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException,
            InterruptedException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(42.1<=ODIAverage )]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0f, 62.5f, null, 80.5f, 16.3f, 116.3f, "LHB"};
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
    public void testEqualBoolBoolLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string, AggresiveBattingStyle bool, Allrounder bool);";
        String query = "@info(name = 'query1') from players[(AggresiveBattingStyle == Allrounder)]" +
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
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB", false,
                false};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB", true,
                true};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB", true,
                true};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB", false, false};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB", true, true};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB", false,
                false};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB", false,
                false};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB",
                true, false};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB", false,
                false};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB", null, false};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB", true, null};
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
    public void testEqualDoubleDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualDoubleDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 31.0, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 47.6, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 55.8, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 41.2, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 54.2, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 63.3, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 51.9, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.8, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 52.0, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 32.0, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualIntegerDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualIntegerDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.1, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, null, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 55.0, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 41.0, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 54.0, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 63.0, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 51.0, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.0, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 52.0, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 32.0, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualLongDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualLongDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.1, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 47.0, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 55.0, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 41.0, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 54.0, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 63.0, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 51.0, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.0, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 52.0, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 32.0, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualLongFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualLongFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33.1f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 47f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 55f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 41f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 54f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 63f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 51f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 47f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 52f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 32f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualLongIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualLongIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 34, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47l, 65.3, null, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", null, 57.4, 53, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 55, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 41, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 54, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 63, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 51, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 47, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 52, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 32, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualLongLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualLongLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 32l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 47l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 55l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 41l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 54l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 63l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 51l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 47l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 52l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 32l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualStringStringLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string, Nationality string);";
        String query = "@info(name = 'query1') from players[(country == Nationality )]" +
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
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB", "Sri Lanka"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47l, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB", "Sri Lanka"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, 36l, 85.5f, 36.3f, 146.7f, "RHB", "Sri Lanka"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB", "England"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB", "South Africa"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB", "New Zealand"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB", "Australia"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB",
                "South Africa"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB",
                "South Africa"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52l, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB", "India"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB", null};
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
    public void testNotEqualStringStringLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " long,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string, Nationality string);";
        String query = "@info(name = 'query1') from players[(country != Nationality )]" +
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
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33l, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB", "Sri Lankan"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47l, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB", "Sri Lankan"};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53l, 57.4, 36l, 85.5f, 36.3f, 146.7f, "RHB", "Sri Lankan"};
        Object[] o4 = new Object[]{"Joe Root", "England", 55l, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB", "English"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41l, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB", "England"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54l, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB", "Kiwi"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63l, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB", "Australian"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51l, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB",
                "South African"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47l, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB",
                "South African"};
        Object[] o10 = new Object[]{"Virat Kholi", null, 52l, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB", "Indian"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32l, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB", null};
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
    public void testEqualIntegerFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 33f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualIntegerFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 33.1f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 47f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 55f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 41f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 54f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 63f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 51f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 47f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 52f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 32f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualIntegerIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 33, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 42, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualIntegerIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 34, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 47, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 55, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 41, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 54, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 63, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 51, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 47, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 52, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 32, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualIntegerLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualIntegerLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " int,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33, 33.0, 34l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 47l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55, 52.5, 55l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41, 72.5, 41l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54, 48.7, 54l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63, 51.5, 63l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51, 62.1, 51l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47, 47.5, 47l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52, 66.5, 52l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32, 62.5, 32l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualFloatDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage == TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.5, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualFloatDoubleLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage != TestStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.6, 32.5f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, null, 42.0f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 42.0, 42.0f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 42.0, 42.0f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 42.0, 42.0f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 42.0, 42.0f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 42.0, 42.0f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 42.0, 42.0f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 42.0, 42.0f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 42.0, 42.0f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 42.0, 42.0f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualFloatFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage == ODIStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.5, 32.5f, 32.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36.5f, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualFloatFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage != ODIStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.5, 32.4f, 32.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36.5f, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 88.3f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 88.3f, 88.3f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 79.3f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 82.7f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 101.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 86.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 89.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 93.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualFloatIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate int,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage == ODIStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.5, 32.0f, 32, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 83, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36.5f, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52.7f, 88, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43.6f, 90, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45f, 79, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50.5f, 82, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52f, 101, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52.5f, 86, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53.5f, 89, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42.5f, 93, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualFloatIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate int,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIAverage != ODIStrikeRate)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 32.5, 32.1f, 32, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 83, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36.5f, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 88f, 88, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 90f, 90, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 79f, 79, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 82f, 82, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 101f, 101, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 86f, 86, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 89f, 89, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 93f, 93, 26.3f, 141.3f, "RHB"};
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
    public void testEqualDoubleIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 33, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualDoubleIntegerLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage int,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 34, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 36.0, 57.4, 36, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 52.0, 52.5, 52, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 43.0, 72.5, 43, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 45.0, 48.7, 45, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 50.0, 51.5, 50, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 52.0, 62.1, 52, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 52.0, 47.5, 52, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 52, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 32, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualDoubleLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36l, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualDoubleLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.1, 33.0, 33l, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42l, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 36.0, 57.4, null, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 52.0, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 43.0, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 45.0, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 50.0, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 52.0, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 52.0, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 53.0, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 42.0, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualFloatLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIStrikeRate == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 33l, 33.0f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36l, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52l, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43l, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45l, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50l, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52l, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52l, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53l, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42l, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualFloatLongLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage long,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(ODIStrikeRate != ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 33l, 33.1f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, null, 42f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 36l, null, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52l, 52f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43l, 43f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45l, 45f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50l, 50f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52l, 52f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52l, 52f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53l, 53f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42l, 42f, 26.3f, 141.3f, "RHB"};
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
    public void testEqualDoubleFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage == ODIAverage)]" +
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
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 33.0, 33.0f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", null, 65.3, 42.1f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, null, 36.5f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 52.7f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 43.6f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 45.1f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 50.5f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 52.5f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 52.5f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5, 53.5f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, 42.5f, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualDoubleFloatLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate double,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage != ODIAverage)]" +
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
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 7) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 8) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 9) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 13.0, 33.0, 33.0f, 80.5f, 16.3f, 116.3f, "LHB"};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3, 47.6f, 83.4f, 26.3f, 136.3f, "RHB",};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4, 53.7f, 85.5f, 36.3f, 146.7f, "RHB",};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5, 55.8f, 88.3f, 24.9f, 128.3f, "RHB"};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5, 41.2f, 90.7f, 22.3f, 133.8f, "LHB"};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7, 54.2f, 79.3f, 29.3f, 119.3f, "RHB"};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5, 63.3f, 82.7f, 16.3f, 112.2f, "RHB"};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1, 51.9f, 101.5f, 33.3f, 156.3f, "RHB"};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5, 47.8f, 86.5f, 26.3f, 127.3f, "RHB"};
        Object[] o10 = new Object[]{"Virat Kholi", "India", null, 66.5, 52.0f, 89.5f, 30.3f, 136.3f, "RHB"};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5, null, 93.5f, 26.3f, 141.3f, "RHB"};
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
    public void testNotEqualBoolBoolLeftSideVariableExperssionExecutorRightSideVariableExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " double,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string, AggresiveBattingStyle bool, Allrounder bool);";
        String query = "@info(name = 'query1') from players[(AggresiveBattingStyle != Allrounder)]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 1) {
                    Assert.assertEquals("AB de Villiers", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 2) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Rohit Sharma", inEvents[0].getData()[0]);
                    CHECK++;
                }
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();
        long start = System.currentTimeMillis();
        Object[] o1 = new Object[]{"Upul Tharanga", "Sri Lanka", 33.0, 62.5f, 32.5f, 80.5f, 16.3f, 116.3f, "LHB", null,
                null};
        Object[] o2 = new Object[]{"Anjelo Mathews", "Sri Lanka", 47.6, 65.3f, 42.1f, 83.4f, 26.3f, 136.3f, "RHB", true,
                true};
        Object[] o3 = new Object[]{"Asela Gunaratne", "Sri Lanka", 53.7, 57.4f, 36.5f, 85.5f, 36.3f, 146.7f, "RHB", true,
                true};
        Object[] o4 = new Object[]{"Joe Root", "England", 55.8, 52.5f, 52.7f, 88.3f, 24.9f, 128.3f, "RHB", false, false};
        Object[] o5 = new Object[]{"Ben Stokes", "England", 41.2, 72.5f, 43.6f, 90.7f, 22.3f, 133.8f, "LHB", true, true};
        Object[] o6 = new Object[]{"Kane Williamson", "New Zealand", 54.2, 48.7f, 45.1f, 79.3f, 29.3f, 119.3f, "RHB", false,
                false};
        Object[] o7 = new Object[]{"Steve Smith", "Australia", 63.3, 51.5f, 50.5f, 82.7f, 16.3f, 112.2f, "RHB", false,
                false};
        Object[] o8 = new Object[]{"AB de Villiers", "South Africa", 51.9, 62.1f, 52.5f, 101.5f, 33.3f, 156.3f, "RHB",
                true, false};
        Object[] o9 = new Object[]{"Hashim Amla", "South Africa", 47.8, 47.5f, 52.5f, 86.5f, 26.3f, 127.3f, "RHB", false,
                false};
        Object[] o10 = new Object[]{"Virat Kholi", "India", 52.0, 66.5f, 53.5f, 89.5f, 30.3f, 136.3f, "RHB", true, false};
        Object[] o11 = new Object[]{"Rohit Sharma", "India", 32.0, 62.5f, 42.5f, 93.5f, 26.3f, 141.3f, "RHB", true, false};
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
