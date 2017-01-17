package info.androidhive.navigationdrawer.Model;

/**
 * Created by Admin on 14-11-2016.
 */

public class CartOrderModel {

    String customer_id;

    String productName;
    String ProductId;
    String productCostPerUnit;
    String productImage;
    String productDesc;
    String productQuantity;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCostPerUnit() {
        return productCostPerUnit;
    }

    public void setProductCostPerUnit(String productCostPerUnit) {
        this.productCostPerUnit = productCostPerUnit;
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
