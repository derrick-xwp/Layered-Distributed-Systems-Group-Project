/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validators;

import Entity.Users;
import java.sql.Connection;
import javax.faces.validator.FacesValidator;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author xingwenp
 */
@FacesValidator("logInusernameValidator")
public class LoginUsersValidator implements Validator {

    List<Users> allusers = getAllUsers();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String user = (String) value;

        boolean matches = false;
        for (int i = 0; i < allusers.size(); i++) {
            if (user.equalsIgnoreCase(allusers.get(i).getUsername())) {
                matches = true;
                break;
            }
        }

        if (!matches) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Please enter a valid username");
            message.setSummary("Username not valid");
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


