package view.Employee.Room;

import business.concrete.HotelManager;
import business.concrete.RoomManager;
import business.concrete.RoomTypeManager;
import business.services.IHotelService;
import business.services.IRoomService;
import business.services.IRoomTypeService;
import core.utilities.helpers.ComboItem;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.PensionType;
import entity.Room;
import entity.RoomType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRoomView extends JFrame{
    private JPanel container;
    private JComboBox<ComboItem> cmb_hotel;
    private JComboBox cmb_roomType;
    private JTextField txt_adultPrice;
    private JTextField txt_childPrice;
    private JTextField txt_stock;
    private JTextField txt_bedCount;
    private JTextField txt_squareMeter;
    private JCheckBox chk_television;
    private JCheckBox chk_minibar;
    private JCheckBox chk_gameConsole;
    private JCheckBox chk_safe;
    private JCheckBox chk_projection;
    private JButton createButton;
    private Room room;
    private IRoomService roomService;
    private IHotelService hotelService;
    private IRoomTypeService roomTypeService;


    public CreateRoomView(){

        this.add(container);
        this.room = new Room();
        this.roomService = new RoomManager();
        this.hotelService = new HotelManager();
        this.roomTypeService = new RoomTypeManager();
        FrameHelper.setupFrame(this,600, 500, "Alpay Tourism Agency");
        for (Hotel hotel : this.hotelService.getAll())
        {
            this.cmb_hotel.addItem(hotel.getComboItem());
        }
        cmb_hotel.setSelectedItem(null);


        for (RoomType roomType : this.roomTypeService.getAll())
        {
            this.cmb_roomType.addItem(new ComboItem(roomType.getId(),roomType.getName()));
        }
        cmb_roomType.setSelectedItem(null);


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedModel = (ComboItem) cmb_hotel.getSelectedItem();
                room.setHotelId(selectedModel.getKey());
                ComboItem selectedRoomType = (ComboItem) cmb_roomType.getSelectedItem();
                room.setRoomTypeId(selectedRoomType.getKey());
                room.setAdultPrice(Integer.parseInt(txt_adultPrice.getText()));
                room.setChildPrice(Integer.parseInt(txt_childPrice.getText()));
                room.setStock(Integer.parseInt(txt_stock.getText()));
                room.setBedCount(Integer.parseInt(txt_bedCount.getText()));
                room.setSquareMeters(Integer.parseInt(txt_squareMeter.getText()));
                room.setTelevision(chk_television.isSelected());
                room.setMinibar(chk_minibar.isSelected());
                room.setGameConsole(chk_gameConsole.isSelected());
                room.setSafe(chk_safe.isSelected());
                room.setProjection(chk_projection.isSelected());

                SuccessInformationResult successInformationResult =roomService.create(room);
                successInformationResult.showMessageDialog();
                dispose();
            }
        });
    }


}
