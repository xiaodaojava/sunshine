/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package red.lixiang.tools.jdk;


import java.util.Calendar;
import java.util.Properties;

/**
 * Snowflake distributed primary key generator.
 * 
 * <p>
 * Use snowflake algorithm. Length is 64 bit.
 * </p>
 * 
 * <pre>
 * 1bit sign bit.
 * 41bits timestamp offset from 2016.11.01(ShardingSphere distributed primary key published data) to now.
 * 10bits worker process id.
 * 12bits auto increment offset in one mills
 * </pre>
 * 
 * <p>
 * Call @{@code SnowflakeShardingKeyGenerator.setWorkerId} to set worker id, default value is 0.
 * </p>
 * 
 * <p>
 * Call @{@code SnowflakeShardingKeyGenerator.setMaxTolerateTimeDifferenceMilliseconds} to set max tolerate time difference milliseconds, default value is 0.
 * </p>
 * 
 * @author gaohongtao
 * @author panjuan
 */
public final class IDTools {
    
    public static final long EPOCH;
    
    private static final long SEQUENCE_BITS = 4L;
    
    private static final long WORKER_ID_BITS = 2L;
    
    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;
    
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;
    
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;
    
    private static final long WORKER_ID = 0;
    
    private static final int DEFAULT_VIBRATION_VALUE = 1;
    
    private static final int MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 10;


    private static Properties properties = new Properties();
    
    private static int sequenceOffset = -1;
    
    private static long sequence;
    
    private static long lastMilliseconds;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }
    

    public static synchronized Long ID() {
        long currentMilliseconds = System.currentTimeMillis();
        if (waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
            currentMilliseconds = System.currentTimeMillis();
        }
        if (lastMilliseconds == currentMilliseconds) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMilliseconds = waitUntilNextTime(currentMilliseconds);
            }
        } else {
            vibrateSequenceOffset();
            sequence = sequenceOffset;
        }
        lastMilliseconds = currentMilliseconds;
        long p1 = (currentMilliseconds - EPOCH);
        long part1 =  p1<< TIMESTAMP_LEFT_SHIFT_BITS;
        long part2 = getWorkerId() << WORKER_ID_LEFT_SHIFT_BITS;
        long part3  = sequence;
        return (part1) | (part2) | part3;
    }
    
    private static boolean waitTolerateTimeDifferenceIfNeed(final long currentMilliseconds) {
        if (lastMilliseconds <= currentMilliseconds) {
            return false;
        }
        long timeDifferenceMilliseconds = lastMilliseconds - currentMilliseconds;
        try {
            Thread.sleep(timeDifferenceMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    private static long getWorkerId() {
        long result = Long.valueOf(properties.getProperty("worker.id", String.valueOf(WORKER_ID)));
        return result;
    }
    
    private static   int getMaxVibrationOffset() {
        int result = Integer.parseInt(properties.getProperty("max.vibration.offset", String.valueOf(DEFAULT_VIBRATION_VALUE)));
        return result;
    }
    
    private int getMaxTolerateTimeDifferenceMilliseconds() {
        return Integer.valueOf(properties.getProperty("max.tolerate.time.difference.milliseconds", String.valueOf(MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS)));
    }
    
    private static long waitUntilNextTime(final long lastTime) {
        long result = System.currentTimeMillis();
        while (result <= lastTime) {
            result = System.currentTimeMillis();
        }
        return result;
    }
    
    private static void vibrateSequenceOffset() {
        sequenceOffset = sequenceOffset >= getMaxVibrationOffset() ? 0 : sequenceOffset + 1;
    }

    public static void main(String[] args) {

//        Long aLong = SnowflakeGenerator.generateKey();
//            System.out.println(aLong);

//            long p = 101164884579L;
//        System.out.println(p<<6);
        for (int i = 0; i < 10; i++) {
           Long aLong = IDTools.ID();
            System.out.println(aLong);

        }
    }
}
