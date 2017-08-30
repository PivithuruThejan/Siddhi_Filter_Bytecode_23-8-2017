/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.siddhi.core.query;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.query.api.SiddhiApp;
import org.wso2.siddhi.query.api.annotation.Annotation;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;
import org.wso2.siddhi.query.api.execution.query.Query;
import org.wso2.siddhi.query.api.execution.query.input.stream.InputStream;
import org.wso2.siddhi.query.api.execution.query.selection.Selector;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.condition.Compare;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FilterTestCase1 {
    private static final Logger log = Logger.getLogger(FilterTestCase1.class);
    private volatile int count;
    private volatile boolean eventArrived;

    @Before
    public void init() {
        count = 0;
        eventArrived = false;
    }


    // Test cases for GREATER_THAN operator
    @Test
    public void filterTest1() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("filter test1");
        SiddhiManager siddhiManager = new SiddhiManager();

        String siddhiApp = "" +
                "@app:name('filterTest1') " +
                "" +
                "define stream cseEventStream (symbol string, price float, volume long);" +
                "" +
                "@info(name = 'query1') " +
                "from cseEventStream[70 > price] " +
                "select symbol, price " +
                "insert into outputStream;" +
                "" +
                "@info(name = 'query2') " +
                "from outputStream[70 > price] " +
                "select symbol, price " +
                "insert into outputStream2 ;";


        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        log.info("Running : " + siddhiAppRuntime.getName());

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertTrue("WSO2".equals(inEvents[0].getData(0)));
                count = count + inEvents.length;
                eventArrived = true;
            }

        });

        QueryCallback queryCallback = new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertTrue("WSO2".equals(inEvents[0].getData(0)));

            }

        };
        siddhiAppRuntime.addCallback("query2", queryCallback);
        queryCallback.startProcessing();

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"IBM", 700f, 100L});
        inputHandler.send(new Object[]{"WSO2", 60.5f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        Assert.assertTrue(eventArrived);
        queryCallback.stopProcessing();

        siddhiAppRuntime.shutdown();
    }
    @Test
    public void siddhipart() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {

        String definition = "@config(async = 'true') define stream players(playerName string,country string,TestAverage float,TestStrikeRate float,ODIAverage float,ODIStrikeRate float,T20Average float,T20StrikeRate float,BattingStyle string);";
        String query = "@info(name = 'query1') from players[TestAverage>45.0 and (ODIAverage>40.0 or ODIStrikeRate>100.0) and not(T20Average<10.0 or T20StrikeRate>150.0)] select playerName, BattingStyle insert into sqaud;";
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime=siddhiManager.createSiddhiAppRuntime(definition + query);
        siddhiAppRuntime.addCallback("query1",new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);


            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("players");
        siddhiAppRuntime.start();



        for(int i=1;i<=1000;i++){
            inputHandler.send(new Object[]{"Upul Tharanga","Sri Lanka",33.0f,62.5f,32.5f,80.5f,16.3f,116.3f,"LHB"});
            inputHandler.send(new Object[]{"Anjelo Mathews","Sri Lanka",47.6f,65.3f,42.1f,83.4f,26.3f,136.3f,"RHB"});
            inputHandler.send(new Object[]{"Asela Gunaratne","Sri Lanka",53.7f,57.4f,36.5f,85.5f,36.3f,146.7f,"RHB"});
            inputHandler.send(new Object[]{"Joe Root","England",55.8f,52.5f,52.7f,88.3f,24.9f,128.3f,"RHB"});
            inputHandler.send(new Object[]{"Ben Stokes","England",41.2f,72.5f,43.6f,90.7f,22.3f,133.8f,"LHB"});
            inputHandler.send(new Object[]{"Kane Williamson","New Zealand",54.2f,48.7f,45.1f,79.3f,29.3f,119.3f,"RHB"});
            inputHandler.send(new Object[]{"Steve Smith","Australia",63.3f,51.5f,50.5f,82.7f,16.3f,112.2f,"RHB"});
            inputHandler.send(new Object[]{"AB de Villiers","South Africa",51.9f,62.1f,52.5f,101.5f,33.3f,156.3f,"RHB"});
            inputHandler.send(new Object[]{"Hashim Amla","South Africa",47.8f,47.5f,52.5f,86.5f,26.3f,127.3f,"RHB"});
            inputHandler.send(new Object[]{"Virat Kholi","India",52.0f,66.5f,53.5f,89.5f,30.3f,136.3f,"RHB"});
            inputHandler.send(new Object[]{"Rohit Sharma","India",32.0f,62.5f,42.5f,93.5f,26.3f,141.3f,"RHB"});




        }
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void filterTest2() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("filter test2");
        SiddhiManager siddhiManager = new SiddhiManager();


        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[150 > volume] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertTrue("IBM".equals(inEvents[0].getData(0)));
                count = count + inEvents.length;
                eventArrived = true;
            }

        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"IBM", 700f, 100L});
        inputHandler.send(new Object[]{"WSO2", 60.5f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        Assert.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery3() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test3");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[70 > price] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertTrue("WSO2".equals(inEvents[0].getData(0)));
                count = count + inEvents.length;
            }

        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 100});
        inputHandler.send(new Object[]{"IBM", 75.6f, 100});
        try {
            inputHandler.send(new Object[]{"WSO2", 57.6f, 200});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();
    }


    @Test
    public void testFilterQuery4() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test4");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50f] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery5() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test5");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50L] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery6() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test6");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50L] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60});
        inputHandler.send(new Object[]{"WSO2", 70f, 40});
        inputHandler.send(new Object[]{"WSO2", 44f, 200});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery7() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test7");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50L] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);

        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery8() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test8");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume float);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50L] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60f});
        inputHandler.send(new Object[]{"WSO2", 70f, 40f});
        inputHandler.send(new Object[]{"WSO2", 44f, 200f});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery9() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test9");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume float);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50f] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");

        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60f});
        inputHandler.send(new Object[]{"WSO2", 70f, 40f});
        inputHandler.send(new Object[]{"WSO2", 44f, 200f});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery10() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test10");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50d] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery11() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test11");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50f] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery12() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test12");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 45] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery13() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test13");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume float);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50d] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60f});
        inputHandler.send(new Object[]{"WSO2", 70f, 40f});
        inputHandler.send(new Object[]{"WSO2", 44f, 200f});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery14() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test14");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume float);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 45] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60f});
        inputHandler.send(new Object[]{"WSO2", 70f, 40f});
        inputHandler.send(new Object[]{"WSO2", 44f, 200f});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery15() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test15");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume float, quantity " +
                "int);";
        String query = "@info(name = 'query1') from cseEventStream[quantity > 4d] select symbol,price,quantity insert" +
                " into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery16() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test16");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 50d] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery17() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test17");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.GREATER_THAN, Expression.value(45))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("volume", Expression.variable("volume")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery18() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test18");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.compare(Expression.value(70),
                        Compare.Operator.GREATER_THAN,
                        Expression.variable("volume"))
                )
        );
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        QueryCallback queryCallback = new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertTrue("WSO2".equals(inEvents[0].getData(0)));
                count = count + inEvents.length;
            }

        };
        siddhiAppRuntime.addCallback("query1", queryCallback);
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 50});
        inputHandler.send(new Object[]{"IBM", 75.6f, 100});
        inputHandler.send(new Object[]{"WSO2", 57.6f, 30});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery20() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test20");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.compare(Expression.variable("volume"),
                        Compare.Operator.LESS_THAN,
                        Expression.value(100))
                )
        );
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price")).
                        select("volume", Expression.variable("volume"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertEquals(10L, ((Long) inEvents[0].getData(2)).longValue());
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 103L});
        inputHandler.send(new Object[]{"WSO2", 57.6f, 10L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery21() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test21");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 100] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertEquals(10L, ((Long) inEvents[0].getData(2)).longValue());
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 100L});
        inputHandler.send(new Object[]{"WSO2", 57.6f, 10L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery22() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test22");

        SiddhiManager siddhiManager = new SiddhiManager();


        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume > 12L and price < 56] select symbol,price," +
                "volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);


        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertEquals(100d, inEvents[0].getData(2));
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 100d});
        inputHandler.send(new Object[]{"WSO2", 57.6f, 10d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery23() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test23");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[symbol != 'WSO2' and volume != 55L and price != " +
                "45f ] select symbol,price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 45f, 100L});
        inputHandler.send(new Object[]{"IBM", 35f, 50L});

        Thread.sleep(200);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery24() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test24");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 50f] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 45f, 100L});
        inputHandler.send(new Object[]{"IBM", 35f, 50L});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery25() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test25");

        SiddhiManager siddhiManager = new SiddhiManager();


        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[price != 35L] select symbol,price insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 45f, 100L});
        inputHandler.send(new Object[]{"IBM", 35f, 50L});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }


    @Test
    public void testFilterQuery26() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test26");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 100 and volume != 70d] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 100L});
        inputHandler.send(new Object[]{"IBM", 57.6f, 10L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }


    @Test
    public void testFilterQuery27() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test27");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[price != 53.6d or price != 87] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.6f, 100L});
        inputHandler.send(new Object[]{"IBM", 57.6f, 10L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery28() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test28");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 40f and volume != 400] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50});
        inputHandler.send(new Object[]{"WSO2", 50.5f, 400});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery29() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test29");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 40d and volume != 400d] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50});
        inputHandler.send(new Object[]{"WSO2", 50.5f, 400});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery30() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test30");

        SiddhiManager siddhiManager = new SiddhiManager();
        String cseEventStream = "define stream cseEventStream (symbol string, price float, available bool);";
        String query = "@info(name = 'query1') from cseEventStream[available != true ] select symbol,price,available " +
                "insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        try {
            inputHandler.send(new Object[]{"IBM", 55.6f, true});
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputHandler.send(new Object[]{"WSO2", 57.6f, false});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery31() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test31");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("available", Attribute.Type.BOOL);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.compare(Expression.variable("available"),
                        Compare.Operator.NOT_EQUAL,
                        Expression.value(true))
                )
        );
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price")).
                        select("available", Expression.variable("available"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertEquals("WSO2", inEvents[0].getData(0).toString());
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"IBM", 55.6f, true});
        inputHandler.send(new Object[]{"WSO2", 57.6f, false});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery32() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test32");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[price != 50 and volume != 50L] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery33() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test33");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 50d] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery34() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test34");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 50f  or volume != 50] select symbol," +
                "price,volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery35() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test35");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume != 50L] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery36() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test36");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("available", Attribute.Type.BOOL);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.compare(Expression.variable("available"),
                        Compare.Operator.EQUAL,
                        Expression.value(true))
                )
        );
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price")).
                        select("available", Expression.variable("available"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Assert.assertEquals("IBM", inEvents[0].getData(0).toString());
                count = count + inEvents.length;
            }

        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"IBM", 55.6f, true});
        inputHandler.send(new Object[]{"WSO2", 57.6f, false});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery37() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test37");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[volume == 50d] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery38() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test38");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[symbol == 'IBM'] select symbol,price,volume insert" +
                " into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"IBM", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery39() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test39");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[price <= 53.5f] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery40() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test40");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume double);";
        String query = "@info(name = 'query1') from cseEventStream[price <= 54] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40d});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50d});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery41() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test41");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[volume <= 40] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery42() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test42");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume int);";
        String query = "@info(name = 'query1') from cseEventStream[price >= 54] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery43() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test43");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume >= 50] select symbol,price,volume insert " +
                "into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 55.5f, 40L});
        inputHandler.send(new Object[]{"WSO2", 53.5f, 50L});

        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery44() throws InterruptedException, InstantiationException, IllegalAccessException {
        log.info("Filter test44");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume >= 50 and volume] select symbol,price," +
                "volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery45() throws InterruptedException, InstantiationException, IllegalAccessException {
        log.info("Filter test45");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[price and volume >= 50 ] select symbol,price," +
                "volume insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery46() throws InterruptedException, InstantiationException, IllegalAccessException {
        log.info("Filter test46");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume >= 50 or volume] select symbol,price,volume" +
                " insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery47() throws InterruptedException, InstantiationException, IllegalAccessException {
        log.info("Filter test47");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[price or volume >= 50 ] select symbol,price,volume" +
                " insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery48() throws InterruptedException, IllegalAccessException, InstantiationException {
        log.info("Filter test48");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("available", Attribute.Type.BOOL);
        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.not(Expression.variable("price"))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price")).
                        select("available", Expression.variable("available"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery49() throws InterruptedException, IllegalAccessException, InstantiationException {
        log.info("Filter test49");

        SiddhiManager siddhiManager = new SiddhiManager();

        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("available", Attribute.Type.BOOL);
        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").
                filter(Expression.variable("price")));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(
                Selector.selector().
                        select("symbol", Expression.variable("symbol")).
                        select("price", Expression.variable("price")).
                        select("available", Expression.variable("available"))
        );
        query.insertInto("StockQuote");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

    }

    @Test(expected = SiddhiAppValidationException.class)
    public void testFilterQuery50() throws InterruptedException, InstantiationException, IllegalAccessException {
        log.info("Filter test50");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "define stream cseEventStream (symbol string, price float, volume long);";
        String query = "@info(name = 'query1') from cseEventStream[volume] select symbol,price,volume insert into " +
                "outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(cseEventStream + query);
    }

    @Test
    public void testFilterQuery51() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test51");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL, Expression.value(60f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery52() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test52");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL, Expression.value(60))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");


        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery53() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test53");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL,
                Expression.value(60L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");


        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery54() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test54");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.EQUAL, Expression.value(50d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery55() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test55");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.EQUAL, Expression.value(50f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery56() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test56");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.EQUAL, Expression.value(70))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery57() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test57");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.EQUAL,
                Expression.value(60L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery58() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test58");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.EQUAL, Expression.value(5d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery59() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test59");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.EQUAL, Expression.value(5f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery60() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test60");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.EQUAL, Expression.value(2))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery61() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test61");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.EQUAL,
                Expression.value(4L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery62() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test62");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG).attribute
                ("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL,
                Expression.value(200L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60L, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 200L, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery63() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test63");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL, Expression.value(40d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery64() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test64");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL, Expression.value(40f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery65() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test65");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.EQUAL, Expression.value(40))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery66() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test66");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.not(Expression.compare(Expression.variable
                ("volume"), Compare.Operator.EQUAL, Expression.value(40)))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    //************************************************************************************************************
    //Test cases for less than or equal
    @Test
    public void testFilterQuery67() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test67");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.DOUBLE).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(60d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50d, 60L});
        inputHandler.send(new Object[]{"WSO2", 70d, 40L});
        inputHandler.send(new Object[]{"WSO2", 44d, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery68() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test68");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.DOUBLE).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(100f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50d, 60L});
        inputHandler.send(new Object[]{"WSO2", 70d, 40L});
        inputHandler.send(new Object[]{"WSO2", 44d, 200L});
        Thread.sleep(100);
        Assert.assertEquals(3, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery69() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test69");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.DOUBLE).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(50))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50d, 60L});
        inputHandler.send(new Object[]{"WSO2", 70d, 40L});
        inputHandler.send(new Object[]{"WSO2", 44d, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery70() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test70");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN_EQUAL,
                Expression.value(200L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300d, 4});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery71() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test71");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(50d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery72() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test72");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN_EQUAL,
                Expression.value(200L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 500f, 60d, 5});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300d, 4});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery73() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test73");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(5d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 500f, 60d, 6});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300d, 4});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery74() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test74");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(5f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 500f, 60d, 6});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300d, 4});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();


    }

    @Test
    public void testFilterQuery75() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test75");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE)
                .attribute("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("quantity"),
                Compare.Operator.LESS_THAN_EQUAL,
                Expression.value(3L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 500f, 60d, 6});
        inputHandler.send(new Object[]{"WSO2", 70f, 60d, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300d, 4});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testFilterQuery76() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test76");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(50d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery77() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test77");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(50f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery78() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test78");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN_EQUAL, Expression.value(50))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60L});
        inputHandler.send(new Object[]{"WSO2", 70f, 40L});
        inputHandler.send(new Object[]{"WSO2", 44f, 200L});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery79() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test79");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.LONG).attribute
                ("quantity", Attribute.Type.INT);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN_EQUAL,
                Expression.value(60L))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")).select("quantity", Expression.variable("quantity")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 500f, 60L, 6});
        inputHandler.send(new Object[]{"WSO2", 70f, 60L, 2});
        inputHandler.send(new Object[]{"WSO2", 60f, 300L, 4});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();
    }


    //*****************************************************************************************************************
    //Test cases for less-than operator

    @Test
    public void testFilterQuery80() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test80");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN, Expression.value(50d))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery81() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test81");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.FLOAT).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("volume"),
                Compare.Operator.LESS_THAN, Expression.value(70f))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50f, 60d});
        inputHandler.send(new Object[]{"WSO2", 70f, 40d});
        inputHandler.send(new Object[]{"WSO2", 44f, 200d});
        Thread.sleep(100);
        Assert.assertEquals(2, count);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testFilterQuery82() throws InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        log.info("Filter test82");

        SiddhiManager siddhiManager = new SiddhiManager();
        StreamDefinition cseEventStream = StreamDefinition.id("cseEventStream").attribute("symbol", Attribute.Type
                .STRING).attribute("price", Attribute.Type.DOUBLE).attribute("volume", Attribute.Type.DOUBLE);

        Query query = new Query();
        query.from(InputStream.stream("cseEventStream").filter(Expression.compare(Expression.variable("price"),
                Compare.Operator.LESS_THAN, Expression.value(50))));
        query.annotation(Annotation.annotation("info").element("name", "query1"));
        query.select(Selector.selector().select("symbol", Expression.variable("symbol")).select("price", Expression
                .variable("price")));
        query.insertInto("outputStream");

        SiddhiApp siddhiApp = new SiddhiApp("ep1");
        siddhiApp.defineStream(cseEventStream);
        siddhiApp.addQuery(query);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count = count + inEvents.length;
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 50d, 60d});
        inputHandler.send(new Object[]{"WSO2", 70d, 40d});
        inputHandler.send(new Object[]{"WSO2", 44d, 200d});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        siddhiAppRuntime.shutdown();
    }

}
