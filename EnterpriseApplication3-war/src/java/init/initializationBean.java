/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xingwenp
 */
@Named(value = "initializationBean")
@ManagedBean
@RequestScoped
public class initializationBean {

    /**
     * Creates a new instance of initializationBean
     */
    public initializationBean() {
    }

    public String init() {

        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("create table jobs(provider varchar(50),jobid varchar(50)not null primary key, , title  varchar(50), keywords varchar(50),description varchar(500),payment int,status varchar(50),offers  varchar(50))");
            statement.executeUpdate("create table UserInfo(username varchar(50),freelancerid int not null primary key, , skills varchar(50), message varchar(500),account int)");
            statement.executeUpdate("create table users(username varchar(50)not null primary key, ,password varchar(50), role int,firstname  varchar(50),lastname  varchar(50),address varchar(50))");
            statement.executeUpdate("create table LOGINFO(LOGINFO varchar(50))");

            statement.execute("insert into jobs values('eoin','1','software engineer','programmer','In this role, as a software engineer, you will contribute to the design, development, documenting, testing, and enhancement of highly available, distributed, scalable and secure compute systems and platforms with an emphasis on computer vision',35,'open',null)");
            statement.execute("insert into jobs values('eoin','2','Training and Communication Specialist','coach','As Training and Communication Specialist you will Manage, design and facilitate business process training to all teams across the organisation as part of wider organisational change, including system and workflow components.',25,'open',null)");
            statement.execute("insert into jobs values('eoin','3','driver','bus','CPC License is essential,Adhere to all quality controls and procedures when carrying out tasks,Ensure all Items are picked, inspected and shipped according to customer requirements ',25,'open',null)");
            statement.execute("insert into jobs values('eoin','4','cook','HSE cook','There is currently one permanent and whole-time vacancy available in Midlands Regional Hospital Portlaoise.',25,'open',null)");
            statement.execute("insert into jobs values('eoin','5','teacher','English teacher','Depending on qualifications, we offer an open end or a 1 year contract in accordance with the Service regulations for the locally recruited teachers in the European Schools',15,'open',null)");

            statement.execute("insert into userInfo values('trump',1,'programming','hey,i am very good at programming, i have three years working experience as a bug writer in FBI',12)");
            statement.execute("insert into userInfo values('smith',2,'swimming','I am good at swimming, i can swimm in shannon river without rest for 24 hours',22)");

            statement.execute("insert into users values('admin','p1',1,'jhon','smith','limerick')");
            statement.execute("insert into users values('eoin','p3',3,'eoin','batch','dublin')");
            statement.execute("insert into users values('trump','p2',2,'trump','donald','cork')");
            statement.execute("insert into users values('smith','p2',2,'smith','chees','galway')");
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeResource(connection, statement, null);
        }
        return "index";

    }

    public Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
