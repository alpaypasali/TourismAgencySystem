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
import entity.Room;
import entity.RoomType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateRoomView extends  JFrame {
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
    private JButton updateButton;
    private JPanel container;
    private Room room;
    private IRoomService roomService;
    private IHotelService hotelService;
    private IRoomTypeService roomTypeService;
    public UpdateRoomView(Room room){

        this.add(container);
        this.roomService = new RoomManager();
        this.hotelService = new HotelManager();
        this.roomTypeService = new RoomTypeManager();
        FrameHelper.setupFrame(this,600, 500, "Alpay Tourism Agency");
        this.room = room;
        for (Hotel hotel : this.hotelService.getAll())
        {
            this.cmb_hotel.addItem(hotel.getComboItem());
        }


        for (RoomType roomType : this.roomTypeService.getAll())
        {
            this.cmb_roomType.addItem(new ComboItem(roomType.getId(),roomType.getName()));
        }

        cmb_hotel.setSelectedItem(room.getHotelId());
        cmb_roomType.setSelectedItem(room.getRoomId());
        txt_adultPrice.setText(String.valueOf(room.getAdultPrice()));
        txt_childPrice.setText(String.valueOf(room.getChildPrice()));
        txt_stock.setText(String.valueOf(room.getStock()));
        txt_bedCount.setText(String.valueOf(room.getBedCount()));
        txt_squareMeter.setText(String.valueOf(room.getSquareMeters()));
        chk_television.setSelected(room.getTelevision());
        chk_minibar.setSelected(room.getMinibar());
        chk_gameConsole.setSelected(room.getGameConsole());
        chk_safe.setSelected(room.getSafe());
        chk_projection.setSelected(room.getProjection());


        updateButton.addActionListener(new ActionListener() {
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

                SuccessInformationResult successInformationResult =roomService.update(room);
                successInformationResult.showMessageDialog();
                dispose();
            }
        });
    }
}
