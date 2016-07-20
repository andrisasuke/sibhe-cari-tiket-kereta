package com.masbhe.tiket.pjka.android.helper;

public class DroplistItem {
    private String key;
    private String label;

    public DroplistItem(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
