package com.sczfdf.maa.random.dice.bean;

import lombok.Data;

/**
 * @author cgb
 */
@Data
public class Dice {
    private int roiX;
    private int roiY;
    private int roiW = 70;
    private int roiH = 70;
    private int index;
    private String type;

    public Dice(int roiX, int roiY, int index) {
        this.roiX = roiX;
        this.roiY = roiY;
        this.index = index;
        this.type = "*";
    }
}