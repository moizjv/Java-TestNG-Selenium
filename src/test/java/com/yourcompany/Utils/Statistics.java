package com.yourcompany.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mehmetgerceker on 3/16/16.
 */
public class Statistics
{
    ArrayList<Double> data;
    int size;

    public Statistics(ArrayList<Double> data)
    {
        this.data = data;
        size = data.size();
    }

    public double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    public double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/size;
    }

    public double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

    public double getMedian()
    {
        Collections.sort(data);

        if (this.size % 2 == 0)
        {
            return (data.get((this.size / 2) - 1) + data.get(this.size / 2)) / 2.0;
        }
        else
        {
            return data.get(this.size / 2);
        }
    }
}
