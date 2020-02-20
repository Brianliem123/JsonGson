package com.fa.merchant.modelclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("productId")
    private int productID;

    private int productQty;
    private String productName, productSlug;

    @SerializedName("productImage")
    private String productImg;

    private Merchant merchant;
    private Category category;

    public int getProductID() {
        return productID;
    }

    public int getProductQty() {
        return productQty;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public String getProductImg() {
        return productImg;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Category getCategory() {
        return category;
    }

    public Product(int productID, int productQty, String productName, String productSlug, String productImg, Merchant merchant, Category category) {
        this.productID = productID;
        this.productQty = productQty;
        this.productName = productName;
        this.productSlug = productSlug;
        this.productImg = productImg;
        this.merchant = merchant;
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productID);
        dest.writeInt(this.productQty);
        dest.writeString(this.productName);
        dest.writeString(this.productSlug);
        dest.writeString(this.productImg);
        dest.writeParcelable(this.merchant, flags);
        dest.writeParcelable(this.category, flags);
    }

    protected Product(Parcel in) {
        this.productID = in.readInt();
        this.productQty = in.readInt();
        this.productName = in.readString();
        this.productSlug = in.readString();
        this.productImg = in.readString();
        this.merchant = in.readParcelable(Merchant.class.getClassLoader());
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
