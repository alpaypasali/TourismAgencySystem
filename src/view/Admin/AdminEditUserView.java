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

public class AdminEditUserView extends JFrame {

    private JPanel container;
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JComboBox<Role> cmb_role;
    private JButton updateButton;
    private  User user;
    private IUserService userService;

    public  AdminEditUserView(User user){
        this.add(container);
        this.user = user;
        this.userService = new UserManager();
        FrameHelper.setupFrame(this, 300, 500, "Alpay Tourism Agency");

        this.cmb_role.setModel(new DefaultComboBoxModel<>(Role.values()));
        this.cmb_role.setSelectedItem(user.getRole());
        txt_username.setText(user.getUsername());
        txt_password.setText(user.getPassword());

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setUsername(txt_username.getText());
                user.setRole((Role) cmb_role.getSelectedItem());
                user.setPassword(txt_password.getText());

              SuccessInformationResult updatedUser = userService.update(user);
              updatedUser.showMessageDialog();
              dispose();
            }
        });
    }
}
