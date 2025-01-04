package com.sczfdf.maa.random.dice.bean;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

@Getter
public class RDContext {

    private static final Logger logger = Logger.getLogger(RDContext.class.getName());

    // 初始坐标，棋盘的左上角（大概是第一个骰子的左上角）
    private static final int INIT_X = 150;
    private static final int INIT_Y = 700;

    // 骰子大小
    private static final int DICE_SIZE = 70;

    private static List<int[]> diceList = null;

    // 截图
    private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock(true);
    private static BufferedImage screenshotImg = null;

    // 移动
    private static final ReentrantLock SWIPE_LOCK = new ReentrantLock();

    // 需要移动次数
    private static int needSwipeNum = 0;
    private static final ReentrantLock needSwipeLock = new ReentrantLock();

    // 红骰子下标
    private static Object redStoneBox = null;

    public RDContext() {
    }

    public static List<int[]> initDiceList() {
        diceList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int o = 0; o < 5; o++) {
                int[] diceRoi = {
                    INIT_X + (DICE_SIZE + 10) * o,
                    INIT_Y + (DICE_SIZE + 10) * i
                };
                logger.info(String.format("init_dice_list [%d, %d] = [%d, %d]", i, o, diceRoi[0], diceRoi[1]));
                diceList.add(diceRoi);
            }
        }
        return diceList;
    }

    public static void setScreenshot(BufferedImage img) {
        READ_WRITE_LOCK.writeLock().lock();
        try {
            screenshotImg = img;
        } finally {
            READ_WRITE_LOCK.writeLock().unlock();
        }
    }

    public static BufferedImage getScreenshot() {
        READ_WRITE_LOCK.readLock().lock();
        try {
            return screenshotImg;
        } finally {
            READ_WRITE_LOCK.readLock().unlock();
        }
    }

    public static boolean tryGetSwipeToken() {
        // This method is currently hardcoded to return true.
        // Add your logic here if you need to manage swipe tokens.
        return true;
    }
}
