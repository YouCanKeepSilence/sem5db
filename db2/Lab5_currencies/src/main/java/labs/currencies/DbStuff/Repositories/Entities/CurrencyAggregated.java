package labs.currencies.DbStuff.Repositories.Entities;

public class CurrencyAggregated {
    private String name;

    private String id;

    private float maxValue;

    private float minValue;

    private float avgValue;

    @Override
    public String toString() {
        return String.format(
                "Currency: %s \n minValue: %f\n avgValue: %f\n maxValue: %f \n",
                getId(), getMinValue(), getAvgValue(), getMaxValue());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(float avgValue) {
        this.avgValue = avgValue;
    }
}
