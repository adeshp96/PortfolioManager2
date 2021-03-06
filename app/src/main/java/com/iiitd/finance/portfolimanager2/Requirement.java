package com.iiitd.finance.portfolimanager2;

import java.io.Serializable;
public class Requirement implements Serializable {
    public float returns;
    public int horizon;
    public int risk;
    public String purpose;
    public int final_amount = 100000, present_amount;
    boolean is_tax_saving;
    boolean is_retirement_saving;
    public Requirement(float returns, int risk, int horizon) {
        this.returns = returns;
        this.risk = risk;
        this.horizon = horizon;
    }

    public Requirement(float returns, int risk, int horizon, String purpose, int
            present_amount, int final_amount, boolean is_tax_saving, boolean is_retirement_saving) {
        if(is_retirement_saving && is_tax_saving)
            throw new RuntimeException("Requirement cannot be both tax saving and retirement " +
                    "saving");
        this.returns = returns;
        this.horizon = horizon;
        this.risk = risk;
        this.purpose = purpose;
        this.final_amount = final_amount;
        this.present_amount = present_amount;
        this.is_tax_saving = is_tax_saving;
        this.is_retirement_saving = is_retirement_saving;
    }

    @Override
    public String toString() {
        return
                "returns=" + returns +
                ", horizon=" + horizon +
                ", risk=" + risk;
    }
}
