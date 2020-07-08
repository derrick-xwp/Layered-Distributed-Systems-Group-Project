/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validators;

import Entity.Users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

/**
 *
 * @author xingwenp
 */
@FacesValidator("logINpasswordValidator")

public class LoginPasswordValidator implements Validator {

    List<Users> allusers = getAllUsers();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;

        boolean matches = false;
        for (int i = 0; i < allusers.size(); i++) {
            if (password.equalsIgnoreCase(allusers.get(i).getPassword())) {
                matches = true;
                break;
            }
        }

        if (!matches) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Please enter a valid password");
            message.setSummary("Incorrect password");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> databaseUsers = new ArrayList<>();
        String url = "jdbc:derby://localhost:1527/sample";
        String user = "app";
        String pass = "app";

        try {
            Connection connect = DriverManager.getConnection(url, user, pass);
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.USERS";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Users users = new Users(result.getString(1), result.getString(2), result.getInt(3));
                databaseUsers.add(users);

            }

            connect.close();

        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());
        }

        return databaseUsers;
    }
}

