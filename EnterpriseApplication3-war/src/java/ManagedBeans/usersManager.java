/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import java.sql.Connection;
import java.sql.DriverManager;
import LocalSessionBeans.UsersFacade;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.Serializable;
import javax.inject.*;
import Entity.Users;
import javax.enterprise.context.SessionScoped;
import java.sql.ResultSet;
import java.util.List;
import javax.ejb.EJB;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author xingwenp
 */
@Named(value = "userManager")
@ManagedBean
@SessionScoped
public class usersManager implements Serializable {

    @EJB
    UsersFacade UsersFacade;

    @Inject
    usersBean userManagedBean;

    /**
     * Creates a new instance of userManager
     */
    public usersManager() {
    }

    public List<Users> getAll() {
        return UsersFacade.findAll();
    }

    public int count() {
        return UsersFacade.count();
    }

    public String delete(Users u) {
        UsersFacade.remove(u);
        return null;
    }

    public String add() {
        Users u = new Users();
        u.setUsername(userManagedBean.getUsername());
        u.setPassword(userManagedBean.getPassword());
        u.setRole(userManagedBean.getrole());
        u.setFirstname(userManagedBean.getFirstname());
        u.setLastname(userManagedBean.getLastname());
        u.setAddress(userManagedBean.getAddress());

        UsersFacade.create(u);

        return "adminMainPage.xhtml";
    }

    public void edit(Users u) {
        userManagedBean.setUsername(u.getUsername());
        userManagedBean.setPassword(u.getPassword());
        userManagedBean.setrole(u.getRole());
        userManagedBean.setFirstname(u.getFirstname());
        userManagedBean.setLastname(u.getLastname());
        userManagedBean.setAddress(u.getAddress());
    }

    public void save() {
        Users u = new Users();
        u.setUsername(userManagedBean.getUsername());
        u.setPassword(userManagedBean.getPassword());
        u.setRole(userManagedBean.getrole());
        u.setFirstname(userManagedBean.getFirstname());
        u.setLastname(userManagedBean.getLastname());
        u.setAddress(userManagedBean.getAddress());

        UsersFacade.edit(u);

    }

    public String login() {
        List<Users> fromDB = getAllUsers();

        String user = userManagedBean.getUsername();
        String password = userManagedBean.getPassword();
        int accessType = 4;

        for (int i = 0; i < fromDB.size(); i++) {
            if (user.equalsIgnoreCase(fromDB.get(i).getUsername())) {
                if (password.equalsIgnoreCase(fromDB.get(i).getPassword())) {
                    accessType = fromDB.get(i).getRole();
                    break;
                }
            }
        }

        if (accessType == 1) {
            return "admin/adminMainPage.xhtml";
        } else if (accessType == 2) {
            return "freelancer/freelancerMainPage";
        } else if (accessType == 3) {
            return "jobprovider/jobproviderMainPage";
        } else {
            return "retryredict";
        }

    }

    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> databaseUsers = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
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
