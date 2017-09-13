/*
* Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
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
public class FilterOptimizingTestCase1 {
    private static final int COUNT = 1;
    private static int CHECK = 1;

    @Test
    public void filterCheckWithExpressionExecutor() throws InterruptedException, IllegalAccessException,
            InvocationTargetException, InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string," +
                "TestAverage float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float," +
                "T20StrikeRate float,BattingStyle string);";
        String query = "@info(name = 'query1') from players[TestAverage>45.0 and (ODIAverage>40.0 or ODIStrikeRate>100.0)" +
                " and not(T20Average<10.0 or T20StrikeRate>150.0)] select playerName, BattingStyle insert into sqaud;";
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
                    Assert.assertEquals("Kane Williamson", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Steve Smith", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 5) {
                    Assert.assertEquals("Hashim Amla", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 6) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
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
    public void complexExpression1() throws InterruptedException, InstantiationException, IllegalAccessException,
            InvocationTargetException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players [TestAverage>45.0 and (ODIAverage>40.0 or ODIStrikeRate>100.0)" +
                " and not(T20Average<10.0 or T20StrikeRate>150.0) or (ODIAverage<35.0 or T20StrikeRate>130.0 and" +
                " not(TestStrikeRate < 55.0))] select playerName, BattingStyle insert into sqaud;";
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
                } else if (CHECK == 10) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 11) {
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
    public void complexExpression2() throws InterruptedException, InstantiationException, IllegalAccessException,
            InvocationTargetException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players [(TestAverage>45.0 and TestStrikeRate>45.0 or ODIAverage>45.0) " +
                "and (ODIAverage>40.0 or ODIStrikeRate>100.0) and not(T20Average<10.0 or T20StrikeRate>150.0 and TestStrikeRate>65.0)" +
                "or (ODIAverage<35.0 or T20StrikeRate>130.0 and not(TestStrikeRate < 55.0))] select playerName, BattingStyle insert into sqaud;";
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
                } else if (CHECK == 10) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 11) {
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
    public void testForConsecutiveOperatorsAND() throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage>45.0 and ODIAverage>40.0 and ODIStrikeRate>100.0)]" +
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
    public void testForConsecutiveOperatorsOR() throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        CHECK = 2;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage>45.0 or ODIAverage>40.0 or ODIStrikeRate>100.0)]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 2) {
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
                } else if (CHECK == 10) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 11) {
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
    public void testForConsecutiveOperatorsNOT() throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        CHECK = 2;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[not(not(TestAverage>45.0 or ODIAverage>40.0 or ODIStrikeRate>100.0))]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 2) {
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
                } else if (CHECK == 10) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 11) {
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
    public void testForGreaterthanOperatorFloatDoubleLeftSideVariableExperssionExecutorRightSideConstantExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 2;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage>45.0)]" +
                " select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (CHECK == 2) {
                    Assert.assertEquals("Anjelo Mathews", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 3) {
                    Assert.assertEquals("Asela Gunaratne", inEvents[0].getData()[0]);
                    CHECK++;
                } else if (CHECK == 4) {
                    Assert.assertEquals("Joe Root", inEvents[0].getData()[0]);
                    CHECK++;
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
                } else if (CHECK == 10) {
                    Assert.assertEquals("Virat Kholi", inEvents[0].getData()[0]);
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
    public void testLessthanOperatorFloatDoubleLeftSideVariableExperssionExecutorRightSideConstantExpressionExecutor()
            throws InterruptedException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        CHECK = 1;
        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage" +
                " float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float," +
                "BattingStyle string);";
        String query = "@info(name = 'query1') from players[(TestAverage<45.0 )]" +
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
                    Assert.assertEquals("Ben Stokes", inEvents[0].getData()[0]);
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
