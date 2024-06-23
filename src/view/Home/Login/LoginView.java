package view.Home.Login;

import business.concrete.UserManager;
import business.services.IUserService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessDataResult;
import entity.User;
import entity.enums.Role;
import view.Admin.AdminHomeView;
import view.Home.Employee.EmployeeHomeView;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel container;
    private JTextField txt_usermane;
    private JTextField txt_password;
    private JButton loginButton;
    private IUserService userService;
    private User user;
    public LoginView(){

        this.add(container);
        this.userService = new UserManager();
        FrameHelper.setupFrame(this, 400, 400, "Alpay Tourism Agency");


        loginButton.addActionListener(e -> {
            SuccessDataResult<User> user = userService.signIn(txt_usermane.getText() , txt_password.getText());

            if (user != null) {
                user.showMessageDialog();
                if(user.getData().getRole().equals(Role.Admin)){

                    AdminHomeView adminHomeView = new AdminHomeView(user.getData());

                }
                else{

                    EmployeeHomeView employeeHomeView = new EmployeeHomeView(user.getData());
                }
                dispose();
            }

        });
    }
}
