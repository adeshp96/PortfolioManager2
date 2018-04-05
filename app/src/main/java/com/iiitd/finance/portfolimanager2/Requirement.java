package com.iiitd.finance.portfolimanager2;

import java.io.Serializable;
public class Requirement implements Serializable {
    public float returns;
    public int horizon;
    public int risk;
    public String purpose;
    public int final_amount = 100000, present_amount;

    public Requirement(float returns, int risk, int horizon) {
        this.returns = returns;
        this.risk = risk;
        this.horizon = horizon;
    }

    public Requirement(float returns, int horizon, int risk, String purpose, int final_amount, int
            present_amount) {
        this.returns = returns;
        this.horizon = horizon;
        this.risk = risk;
        this.purpose = purpose;
        this.final_amount = final_amount;
        this.present_amount = present_amount;
    }

    @Override
    public String toString() {
        return
                "returns=" + returns +
                ", horizon=" + horizon +
                ", risk=" + risk;
    }
}
