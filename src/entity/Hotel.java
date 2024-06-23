package entity;

public class Hotel {

    private Integer hotelId;
    private String hotelName;
    private String city;
    private String district;
    private String fullAddress;
    private String email;
    private String phone;
    private Integer starRating;
    private Boolean hasFreeParking;
    private Boolean hasSpa;
    private Boolean has24_7RoomService;
    private Integer pensionTypeId;
    private PensionType pensionType;
    public Hotel(){

    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHasFreeParking() {
        return hasFreeParking;
    }

    public void setHasFreeParking(Boolean hasFreeParking) {
        this.hasFreeParking = hasFreeParking;
    }

    public Boolean getHasSpa() {
        return hasSpa;
    }

    public void setHasSpa(Boolean hasSpa) {
        this.hasSpa = hasSpa;
    }


    public Boolean getHas24_7RoomService() {
        return has24_7RoomService;
    }

    public void setHas24_7RoomService(Boolean has24_7RoomService) {
        this.has24_7RoomService = has24_7RoomService;
    }

    public Integer getPensionTypeId() {
        return pensionTypeId;
    }

    public void setPensionTypeId(Integer pensionTypeId) {
        this.pensionTypeId = pensionTypeId;
    }

    public PensionType getPensionType() {
        return pensionType;
    }

    public void setPensionType(PensionType pensionType) {
        this.pensionType = pensionType;
    }
}
