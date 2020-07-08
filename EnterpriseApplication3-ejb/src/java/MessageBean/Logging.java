/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageBean;

import java.sql.Connection;
import java.sql.Statement;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Level;
import javax.jms.Message;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author xingwenp
 */
@JMSDestinationDefinition(name = "java:app/myLogFiles/dest", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "dest")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/myLogFiles/dest")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class Logging implements MessageListener {
    
    public Logging() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println(msg.getText());
            updateLogDatabase((String) msg.getText());            
        } catch (JMSException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateLogDatabase(String logInfo){
        


            try {
                    Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app",  "app");
                    Statement statement = connect.createStatement();
                    String sql = "insert into LOGINFO  values ('"+logInfo+"')";
                    int result = statement.executeUpdate(sql);
                connect.close();
             }catch(SQLException sql){
                 System.out.println("Message: " + sql.getMessage());
             }
    }
    
}
