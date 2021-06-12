package com.example.yonatan_gal_post_pc_7;
import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {
    private final String id;
    private String costumerName;
    private boolean hummus;
    private boolean thaini;
    private int pickles;
    private String comment;
    private String status;

    public Order(String name, boolean hummus, boolean thaini, int pickles, String comment, String status)
    {
        this.id = UUID.randomUUID().toString();
        this.costumerName = name;
        this.comment = comment;
        this.hummus = hummus;
        this.thaini = thaini;
        this.status = status;
        this.pickles = parsePickles(pickles);
    }

    public Order(Order otherOrder)
    {
        this.id = otherOrder.getId();
        this.costumerName = otherOrder.getCostumerName();
        this.pickles = otherOrder.getPickles();
        this.status = otherOrder.getStatus();
        this.hummus = otherOrder.isHummus();
        this.thaini = otherOrder.isThaini();
        this.comment = otherOrder.getComment();
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public void setHummus(boolean hummus) {
        this.hummus = hummus;
    }

    public void setPickles(int pickles) {
        this.pickles = pickles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThaini(boolean thaini) {
        this.thaini = thaini;
    }

    public String getId() {
        return id;
    }

    public int getPickles() {
        return pickles;
    }

    public String getComment() {
        return comment;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public String getStatus() {
        return status;
    }

    public boolean isHummus() {
        return hummus;
    }

    public boolean isThaini() {
        return thaini;
    }

    private int parsePickles(int pickles)
    {
        if (pickles > 10)
        {
            return 10;
        }
        else if (pickles < 0)
        {
            return 0;
        }
        return pickles;
    }


}
