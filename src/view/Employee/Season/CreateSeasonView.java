package view.Employee.Season;

import business.concrete.SeasonManager;
import business.services.ISeasonService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateSeasonView extends JFrame{
    private JPanel container;

    private JButton createButton;
    private JTextField txt_hotel;
    private JFormattedTextField txt_startDate;
    private JFormattedTextField txt_endDate;
    private Hotel hotel;
    private ISeasonService seasonService;
    private Season season;


    public CreateSeasonView(Hotel hotel) {
        this.add(container);
        this.hotel = hotel;
        this.season = new Season();
        this.seasonService = new SeasonManager();

        FrameHelper.setupFrame(this,455, 311, "Alpay Tourism Agency");

        this.txt_hotel.setText(hotel.getHotelName() +" "+ hotel.getCity()+" "+ hotel.getDistrict() );
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                season.setHotel_id(hotel.getHotelId());
                season.setStart_date(LocalDate.parse(txt_startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                season.setEnd_date(LocalDate.parse(txt_endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));


                SuccessInformationResult result = seasonService.create(season);
                result.showMessageDialog();
                dispose();

            }
        });
    }
    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        this.txt_startDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txt_endDate = new JFormattedTextField(new MaskFormatter("##/##/####"));

        // Bugünün tarihini al ve formatla
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        // Alanlara bugünün tarihini ata
        this.txt_startDate.setText(formattedDate);
        this.txt_endDate.setText(formattedDate);

    }
}
