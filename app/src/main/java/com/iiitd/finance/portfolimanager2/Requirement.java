package com.iiitd.finance.portfolimanager2;

import java.io.Serializable;
public class Requirement implements Serializable {
    public float returns, horizon;
    public int risk;
    public Requirement(float returns, int risk, float horizon) {
        this.returns = returns;
        this.risk = risk;
        this.horizon = horizon;
    }

    @Override
    public String toString() {
        return
                "returns=" + returns +
                ", horizon=" + horizon +
                ", risk=" + risk;
    }
}
