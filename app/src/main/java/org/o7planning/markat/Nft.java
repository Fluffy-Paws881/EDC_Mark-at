package org.o7planning.markat;

import java.io.Serializable;
import java.util.ArrayList;

public class Nft implements Serializable
{
    public static ArrayList<Nft> nftArrayList = new ArrayList<>();
    private int id;
    private int price;
    private double priceETH;
    private String name;
    private int owner;

    public Nft(int id, String name, int owner, int price, double prETH)
    {
        this.id = id;
        this.price = price;
        this.name = name;
        this.owner = owner;
        this.priceETH = prETH;
    }
    public Nft()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() { return owner; }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public double getPriceETH() { return priceETH; }

    public void setPriceETH(double newPriceETH) {
        this.priceETH = newPriceETH;
    }
}
