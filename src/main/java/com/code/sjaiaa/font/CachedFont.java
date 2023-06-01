package com.code.sjaiaa.font;

import org.lwjgl.opengl.GL11;

/**
 * @author sjaiaa
 * @date 2023/5/18 16:16
 * @discription
 */

public class CachedFont {
    private int displayList;
    long lastUsage;
    private boolean deleted;

    public CachedFont(int displayList, long lastUsage) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
    }

    protected void finalize() {
        if (!deleted) {
            GL11.glDeleteLists(displayList, 1);
        }
    }

    public int getDisplayList() {
        return displayList;
    }

    public long getLastUsage() {
        return lastUsage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

