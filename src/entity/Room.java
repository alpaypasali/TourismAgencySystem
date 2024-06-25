package entity;

import core.utilities.helpers.ComboItem;

public class Room {

    private Integer roomId;
    private Integer hotelId;
    private Hotel hotel;
    private Integer roomTypeId;
    private RoomType roomType;
    private Integer adultPrice;
    private Integer childPrice;
    private Integer stock;
    private Integer bedCount;
    private Integer squareMeters;
    private Boolean television;
    private Boolean minibar;
    private Boolean gameConsole;
    private Boolean safe;
    private Boolean projection;
    public Room(){

    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(Integer adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Integer getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Integer childPrice) {
        this.childPrice = childPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Boolean getTelevision() {
        return television;
    }

    public void setTelevision(Boolean television) {
        this.television = television;
    }

    public Boolean getMinibar() {
        return minibar;
    }

    public void setMinibar(Boolean minibar) {
        this.minibar = minibar;
    }

    public Boolean getGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(Boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public Boolean getSafe() {
        return safe;
    }

    public void setSafe(Boolean safe) {
        this.safe = safe;
    }

    public Boolean getProjection() {
        return projection;
    }

    public void setProjection(Boolean projection) {
        this.projection = projection;
    }

    public ComboItem getComboItem() {
        return new ComboItem(this.getRoomId(),this.getRoomType().getName() +" - " + "Child Price:"+this.getChildPrice() + " - " + "Adult Price:"+ this.getAdultPrice());
    }
}
