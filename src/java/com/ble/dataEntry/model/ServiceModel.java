/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.ServiceBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ServiceModel {
    
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
        }
    }
    
    public int getDeviceId(String manufacturer,String device_type,String model) {
    
    
    int device_id = 0;

        String query = " select d.id\n" +
                       "from device d,manufacturer mf,model md,device_type dt\n" +
                       "where mf.id=d.manufacture_id\n" +
                       "and d.model_id = md.id\n" +
                       "and d.device_type_id = dt.id\n" +
                       "and mf.name='"+manufacturer +"'"+
                       " and md.device_name='"+model +"'"+
                       " and dt.type='" +device_type+"'"+
                       " and d.active='Y'\n" +
                       " and mf.active='Y'\n" +
                       " and md.active='Y'\n" +
                       " and dt.active='Y'";
        
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1,manufacturer);
//            pstmt.setString(2,model);
//            pstmt.setString(3,device_type);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                device_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (device_id > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return device_id;

    }


public int insertRecord(ServiceBean serviceBean) {
    
    String manufacturer = serviceBean.getManufacturer();
    String device_type = serviceBean.getDevice_type();
    String model = serviceBean.getModel();
    int device_id = getDeviceId(manufacturer,device_type,model);
    

        String query = " insert into servicies(service_name,service_uuid,device_id,remark)\n" +
                       "values(?,?,?,?)";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1,serviceBean.getService_name() );
            pstmt.setString(2,serviceBean.getService_uuid() );
            pstmt.setInt(3,device_id);
            pstmt.setString(4, serviceBean.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

public boolean reviseRecords(ServiceBean serviceBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;
    String manufacturer = serviceBean.getManufacturer();
    String device_type = serviceBean.getDevice_type();
    String model = serviceBean.getModel();
    int device_id = getDeviceId(manufacturer,device_type,model);
    
     PreparedStatement ps=null;

      String query1 = " SELECT max(revision_no) revision_no FROM servicies c WHERE c.id = "+serviceBean.getService_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE servicies SET active=? WHERE id = ? && revision_no = ? ";
      String query3 = " INSERT INTO servicies (id,service_name,service_uuid,device_id,remark,revision_no,active) VALUES (?,?,?,?,?,?,?) ";

      int updateRowsAffected=0;
      try {
          connection.setAutoCommit(false);
           ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
         ps = (PreparedStatement) connection.prepareStatement(query2);
           ps.setString(1,  "N");
           ps.setInt(2,serviceBean.getService_id());
           ps.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = ps.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
            ps = (PreparedStatement) connection.prepareStatement(query3);
             ps.setInt(1,serviceBean.getService_id());
             ps.setString(2,serviceBean.getService_name());
             ps.setString(3,serviceBean.getService_uuid());
             ps.setInt(4,device_id);
             ps.setString(5,serviceBean.getRemark());
             ps.setInt(6,rev);
             ps.setString(7,"Y");
             

             int a = ps.executeUpdate();
              if (a > 0) {
                        connection.commit();
                        status = true;
                    }else {
                    connection.rollback();
                    }
             }
           }
          } catch (Exception e)
             {
              System.out.println("CommandModel reviseRecord() Error: " + e);
             }
      finally{
        try {
            ps.close();
          //  connection.setAutoCommit(true);
        } catch (SQLException ex) {
             
        }
      }
      if (status) {
             message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
             message = "Record Not updated Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not updated");
        }

       return status;
    }
  public int getNoOfRows(String searchDeviceType) {
      String query1="select count(*)\n" +
                    "from servicies s,device d,manufacturer mf,model m,device_type dt\n" +
                    "where d.manufacture_id = mf.id\n" +
                    "and d.device_type_id = dt.id\n" +
                    "and d.model_id = m.id\n" +
                    "and s.device_id = d.id\n" +
                    "and s.active='Y'\n" +
                    "and d.active='Y'\n" +
                    "and mf.active='Y'\n" +
                    "and m.active='Y'\n" +
                    "and dt.active='Y' "+
                    " AND IF('" + searchDeviceType + "' = '', service_name LIKE '%%',service_name =?) ";
                    

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchDeviceType);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<ServiceBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchDeviceType) {
        List<ServiceBean> list = new ArrayList<ServiceBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
       String query2=" select s.id,s.service_name,s.service_uuid,mf.name,m.device_name,dt.type,s.remark\n" +
                     "from servicies s,device d,manufacturer mf,model m,device_type dt\n" +
                     "where d.manufacture_id = mf.id\n" +
                     "and d.device_type_id = dt.id\n" +
                     "and d.model_id = m.id\n" +
                     "and s.device_id = d.id\n" +
                     "and s.active='Y'\n" +
                     "and d.active='Y'\n" +
                     "and mf.active='Y'\n" +
                     "and m.active='Y'\n" +
                     "and dt.active='Y' "+
                     "AND IF('" + searchDeviceType + "' = '', service_name LIKE '%%',service_name =?) "+
                     
                     addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchDeviceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ServiceBean serviceBean = new ServiceBean();

                serviceBean.setService_id(rset.getInt("id"));
                serviceBean.setService_name(rset.getString("service_name"));
                serviceBean.setService_uuid(rset.getString("service_uuid"));
                serviceBean.setManufacturer(rset.getString("name"));
                serviceBean.setModel(rset.getString("type"));
                serviceBean.setDevice_type(rset.getString("device_name"));
                
                serviceBean.setRemark(rset.getString("remark"));
                list.add(serviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

//  public int deleteRecord(int device_type_id) {
//
//      String query = "update device_type set active='N' where id=" + device_type_id;
//        int rowsAffected = 0;
//        try {
//            rowsAffected = connection.prepareStatement(query).executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record deleted successfully......";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Error Record cannot be deleted.....";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
   public int deleteRecord(int device_type_id) {
// String query = "update device_type set active='N' where id=" + device_type_id;
      String query = "update servicies set active='N' where id=" + device_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
  public List<String> getSearchServiceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select service_name\n" +
                       "from servicies s\n" +
                       "where s.active='Y'\n" +
                       "group by service_name order by service_name;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("service_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Service Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getDeviceType(String q,String manufacturer) {
        List<String> list = new ArrayList<String>();
        String query = "select dt.type\n" +
                       "from manufacturer mf,device_type dt,device d\n" +
                       "where d.manufacture_id = mf.id\n" +
                       "and d.device_type_id = dt.id \n"+
                       "and mf.name='"+manufacturer+"'\n"+
                       "and d.active='Y'\n" +
                       "and mf.active='Y'\n" +
                       "and dt.active='Y'\n" +
                       "group by type order by type;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("type");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' group by name order by name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getModelType(String q,String manufacturer,String device_type) {
        List<String> list = new ArrayList<String>();
        String query = " select m.device_name\n" +
                      "from device d,manufacturer mf,model m,device_type dt\n" +
                      "where d.manufacture_id = mf.id\n" +
                      "and d.device_type_id = dt.id\n" +
                      "and m.id = d.model_id \n"+
                      "and mf.name='"+manufacturer+"'\n"+
                      "and dt.type='"+device_type+"'\n"+
                      "and d.active='Y'\n" +
                      "and mf.active='Y'\n" +
                      "and m.active='Y'\n" +
                      "and dt.active='Y'\n" +
                      "group by m.device_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("device_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Model Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
   public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    
}
