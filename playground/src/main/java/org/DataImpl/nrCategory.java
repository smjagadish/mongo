package org.DataImpl;

public enum nrCategory {
    highband(3),
    lowband(1),
    midband(1);
    private int sector;
    private nrCategory(int sector_count)
    {
        this.sector = sector_count;
    }
    public int getSector()
    {
        return this.sector;
    }
}
