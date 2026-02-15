package model;

public class FineScheme {

    private int id;
    private String schemeType;
    private double baseAmount;
    private double additional24_48;
    private double additional48_72;
    private double above72;
    private double hourlyRate;
    private boolean isActive;

    public FineScheme(int id,
            String schemeType,
            double baseAmount,
            double additional24_48,
            double additional48_72,
            double above72,
            double hourlyRate,
            boolean isActive) {

        this.id = id;
        this.schemeType = schemeType;
        this.baseAmount = baseAmount;
        this.additional24_48 = additional24_48;
        this.additional48_72 = additional48_72;
        this.above72 = above72;
        this.hourlyRate = hourlyRate;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getAdditional24_48() {
        return additional24_48;
    }

    public double getAdditional48_72() {
        return additional48_72;
    }

    public double getAbove72() {
        return above72;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return schemeType;
    }

}
