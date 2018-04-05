package com.iiitd.finance.portfolimanager2;

public class MutualFund implements  Comparable<MutualFund>{
    public String name;
    public float returns;
    int risk;
    int rating;
    String category;

    public MutualFund(String name, String category, float returns, int rating) {
        this.name = name;
        this.category = category;
        this.returns = returns;
        this.risk = -1;
        this.rating = rating;
    }

    @Override
    public String toString() {
        String returns_string = String.format("%.2f", returns);
        return "MutualFund{" +
                "name='" + name + '\'' +
                ", returns=" + returns_string +
                ", risk=" + risk +
                ", rating=" + rating +
                ", categeory='" + category + '\'' +
                '}';
    }

    @Override
    public int compareTo(MutualFund mutualFund) {
        return mutualFund.rating - this.rating;
    }
}
