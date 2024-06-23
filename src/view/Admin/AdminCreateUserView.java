package view.Admin;

import business.concrete.UserManager;
import business.services.IUserService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.User;
import entity.enums.Role;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateUserView extends JFrame {
    private JPanel container;
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JComboBox cmb_role;
    private JButton createButton;
    private User user;
    private IUserService userService;

    public AdminCreateUserView(){

        this.add(container);
        this.userService = new UserManager();
        this.user = new User();
        FrameHelper.setupFrame(this, 300, 500, "Alpay Tourism Agency");
        this.cmb_role.setModel(new DefaultComboBoxModel<>(Role.values()));
        this.cmb_role.setSelectedItem(null);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setUsername(txt_username.getText());
                user.setRole((Role) cmb_role.getSelectedItem());
                user.setPassword(txt_password.getText());

                SuccessInformationResult createdUser = userService.create(user);
                createdUser.showMessageDialog();
                dispose();
            }
        });
    }
}
