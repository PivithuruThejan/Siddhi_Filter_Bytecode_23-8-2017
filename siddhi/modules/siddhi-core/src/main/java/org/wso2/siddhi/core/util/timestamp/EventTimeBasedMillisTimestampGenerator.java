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

package org.wso2.siddhi.core.util.timestamp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Return the timestamp of the latest event received by the stream junction.
 * This generator can notify the {@link org.wso2.siddhi.core.util.Scheduler}
 * whenever there is a new event with increased timestamp.
 * Also it can optionally send heart beat to the registered schedulers based
 * on the configuration provided.
 *
 * @see org.wso2.siddhi.core.stream.StreamJunction#sendData(long, Object[])
 */
public class EventTimeBasedMillisTimestampGenerator implements TimestampGenerator {

    /**
     * ScheduledExecutorService used to produce heartbeat.
     */
    private final ScheduledExecutorService scheduledExecutorService;
    /**
     * Timestamp as defined by the last event.
     */
    private long lastEventTimestamp;
    /**
     * The actual timestamp of last event arrival.
     */
    private long lastSystemTimestamp;
    /**
     * The minimum time to wait for new events to arrive.
     * If a new event does not arrive within this time, the generator
     * timestamp will be increased by incrementInMilliseconds.
     */
    private long idleTime = -1;
    /**
     * By how many milliseconds, the event timestamp should be increased.
     */
    private long incrementInMilliseconds;
    /**
     * A flag used to start the heartbeat clock for the first time only.
     */
    private boolean heartbeatRunning;

    /**
     * A callback to receive the ScheduledExecutorService notification and to increase the time if required.
     */
    private TimeInjector timeInjector = new TimeInjector();

    /**
     * List of listeners listening to this timestamp generator.
     */
    private List<TimeChangeListener> timeChangeListeners = new ArrayList<TimeChangeListener>();

    /**
     * Create an EventBasedTimeMillisTimestampGenerator.
     *
     * @param scheduledExecutorService  the schedule service to be executed for produce heartbeat.
     */
    public EventTimeBasedMillisTimestampGenerator(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /**
     * Return the time of last event received by Siddhi or the time generated by internal clock after a delay
     * as defined in Playback annotation.
     *
     * @return timestamp
     */
    @Override
    public long currentTime() {
        return lastEventTimestamp;
    }

    /**
     * Set the timestamp to the {@link EventTimeBasedMillisTimestampGenerator} and notify the interested listeners.
     *
     * @param timestamp the timestamp to the {@link EventTimeBasedMillisTimestampGenerator}
     */
    public void setCurrentTimestamp(long timestamp) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        if (timestamp >= this.lastEventTimestamp) {
            synchronized (this) {
                if (timestamp >= this.lastEventTimestamp) {
                    // Update the time only if the time is greater than or equal to previous time
                    this.lastEventTimestamp = timestamp;

                    // Send a notification to listeners - Scheduler
                    for (TimeChangeListener listener : this.timeChangeListeners) {
                        listener.onTimeChange(this.lastEventTimestamp);
                    }
                }
                // Schedule the heartbeat from the current event timestamp
                this.lastSystemTimestamp = System.currentTimeMillis();
                notifyAfter(idleTime);
            }
        }
    }

    /**
     * Register to listen for time changes.
     *
     * @param listener any listeners interested on time change.
     * @see org.wso2.siddhi.core.util.Scheduler
     */
    public void addTimeChangeListener(TimeChangeListener listener) {
        synchronized (this) {
            this.timeChangeListeners.add(listener);
        }
    }

    /**
     * The {@link ScheduledExecutorService} waits until idleTime from the timestamp of last event
     * and if there are no new events arrived within that period, it will inject a new timestamp.
     *
     * @param idleTime the ideal time for wait until from the timestamp of last event.
     */
    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    /**
     * Set by how many milliseconds, the event timestamp should be increased.
     *
     * @param incrementInMilliseconds the timestamp incremental value.
     */
    public void setIncrementInMilliseconds(long incrementInMilliseconds) {
        this.incrementInMilliseconds = incrementInMilliseconds;
    }

    /**
     * This method must be called within a synchronized block to avoid multiple schedulers from running simultaneously.
     *
     * @param duration delay the time from now to delay execution
     */
    private void notifyAfter(long duration) {
        if (!heartbeatRunning && idleTime != -1) {
            // Start the heartbeat if this is the first time
            scheduledExecutorService.schedule(timeInjector, duration, TimeUnit.MILLISECONDS);
            heartbeatRunning = true;
        }
    }

    /**
     * Listener used to get notification when a new event comes in.
     */
    public interface TimeChangeListener {
        void onTimeChange(long currentTimestamp) throws IllegalAccessException, InvocationTargetException, InstantiationException, IOException;
    }

    /**
     * This {@link Runnable} class is executed by the {@link ScheduledExecutorService}
     */
    private class TimeInjector implements Runnable {
        @Override
        public void run() {
            long currentTimestamp = System.currentTimeMillis();
            synchronized (EventTimeBasedMillisTimestampGenerator.this) {
                heartbeatRunning = false;
                long diff = currentTimestamp - lastSystemTimestamp;
                if (diff >= idleTime) {
                    // Siddhi has not received events for more than idleTime
                    long newTimestamp = lastEventTimestamp + incrementInMilliseconds;
                    try {
                        setCurrentTimestamp(newTimestamp);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Wait for idleTime from the timestamp if last event arrival
                    notifyAfter(idleTime - diff);
                }
            }
        }
    }
}
