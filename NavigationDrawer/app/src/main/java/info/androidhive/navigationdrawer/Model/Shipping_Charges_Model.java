package info.androidhive.navigationdrawer.Model;

/**
 * Created by Admin on 07-12-2016.
 */

public class Shipping_Charges_Model {
    String shipping_charge_id;
    Double total_amount;
    String shipping_charge;

    public String getShipping_charge_id() {
        return shipping_charge_id;
    }

    public void setShipping_charge_id(String shipping_charge_id) {
        this.shipping_charge_id = shipping_charge_id;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }
}
