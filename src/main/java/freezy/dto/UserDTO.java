package freezy.dto;

import freezy.utils.FreazyValidField;

public class UserDTO {

    @FreazyValidField(notBlank = true, message = "Name cannot be blank.")
    String name;
    @FreazyValidField(isEmail = true, message = "Invalid email format.")
    String email;
    @FreazyValidField(numbersOnly = true, message = "Phone Number field can only have numbers.")
    String phoneNumber;
    @FreazyValidField(notBlank = true, message = "Address cannot be blank.")
    String address;
    @FreazyValidField(notBlank = true, message = "City cannot be blank.")
    String city;
    @FreazyValidField(numbersOnly = true, message = "Pincode field can only have numbers.")
    String pincode;
    @FreazyValidField(alphanumeric = true, message = "GST code shoule be alphanumeric.")
    String gstId;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
