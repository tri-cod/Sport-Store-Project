package DTO;

public class userInformationDTO {

    private String inforId;
    private String userId;
    private String name;
    private String phoneNumber;
    private String address;

    public userInformationDTO() {
    }

    public userInformationDTO(String userId, String name, String phoneNumber, String address) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // getters & setters
    public String getInforId() {
        return inforId;
    }

    public void setInforId(String inforId) {
        this.inforId = inforId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
