package com.nulstudio.dormitory.common;

public enum CreditChangeAction {
    ;

    private final int action;

    private final String description;

    CreditChangeAction(int action, String description) {
        this.action = action;
        this.description = description;
    }

    public int getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }
}
