package com.lll.beizertest.draw.bean;

import android.graphics.PorterDuff;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2020/8/7
 */
public class XfermodeInfo {

    private String modeName;

    private PorterDuff.Mode mode;

    public XfermodeInfo() {
    }

    public XfermodeInfo(String modeName, PorterDuff.Mode mode) {
        this.modeName = modeName;
        this.mode = mode;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public PorterDuff.Mode getMode() {
        return mode;
    }

    public void setMode(PorterDuff.Mode mode) {
        this.mode = mode;
    }
}
