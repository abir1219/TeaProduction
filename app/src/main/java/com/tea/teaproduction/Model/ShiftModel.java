package com.tea.teaproduction.Model;

public class ShiftModel {
    String ShiftId, ShiftName;

    public ShiftModel(String ShiftId, String ShiftName) {
        this.ShiftId = ShiftId;
        this.ShiftName = ShiftName;
    }

    public String getShiftId() {
        return ShiftId;
    }

    public void setShiftId(String ShiftId) {
        ShiftId = ShiftId;
    }

    public String getShiftName() {
        return ShiftName;
    }

    public void setShiftName(String ShiftName) {
        ShiftName = ShiftName;
    }
}
