package cn.com.creditloans.model;

/**
 * Created by apple on 2018/7/13.
 *
 */

public class LoginEvent {
    private String telephone;

    public LoginEvent(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
