package com.sczfdf.maa.random.dice.utils;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.fastjson2.JSONObject;
import io.github.hanhuoer.maa.model.RecognitionDetail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by cgb
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    private static final ThreadPoolExecutor RECOGNITION_DICE_POOL = new ThreadPoolExecutor(15, 15, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNamePrefix("recognition_dice_").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    private static final ThreadPoolExecutor LOG_POOL = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void executeRecognitionDiceTask(Runnable runnable) {
        RECOGNITION_DICE_POOL.execute(runnable);
    }

    public static void executeLogTask(Runnable runnable) {
        LOG_POOL.execute(runnable);
    }

    public static double getScore(RecognitionDetail recognitionDetail) {
        JSONObject bestResult;
        if (recognitionDetail == null || (bestResult = recognitionDetail.getBestResult()) == null) {
            return 0;
        }

        Object orDefault = bestResult.getOrDefault("score", bestResult.getOrDefault("count", 0));
        return orDefault instanceof BigDecimal ?
                ((BigDecimal) orDefault).doubleValue() : (int) orDefault;
    }

    public static boolean compare(RecognitionDetail recognitionDetail1, RecognitionDetail recognitionDetail2) {
        return getScore(recognitionDetail1) < getScore(recognitionDetail2);
    }
}
