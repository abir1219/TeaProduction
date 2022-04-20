package com.tea.teaproduction.Model;

public class SectorModel {
    String SectorId, SectorName;

    public SectorModel(String SectorId, String SectorName) {
        this.SectorId = SectorId;
        this.SectorName = SectorName;
    }

    public String getSectorId() {
        return SectorId;
    }

    public void setSectorId(String SectorId) {
        SectorId = SectorId;
    }

    public String getSectorName() {
        return SectorName;
    }

    public void setSectorName(String SectorName) {
        SectorName = SectorName;
    }
}
