package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

public class GBV_ShakeOptions {
    private boolean background;
    private int interval;
    private float sensibility;
    private int shakeCounts;

    public GBV_ShakeOptions background(Boolean background) {
        this.background = background;
        return this;
    }

    public GBV_ShakeOptions shakeCount(int shakeCount) {
        this.shakeCounts = shakeCount;
        return this;
    }

    public GBV_ShakeOptions interval(int interval) {
        this.interval = interval;
        return this;
    }

    public GBV_ShakeOptions sensibility(float sensibility) {
        this.sensibility = sensibility;
        return this;
    }

    public boolean isBackground() {
        return this.background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public int getShakeCounts() {
        return this.shakeCounts;
    }

    public int getInterval() {
        return this.interval;
    }

    public float getSensibility() {
        return this.sensibility;
    }
}
