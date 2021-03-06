/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.OperationNameBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class OperationNameModel {

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
//
//  public int getParentOperationId(String parent_operation_name) {
//      String query1="select id as operation_id "
//                    +" from operation_name mt "
//                    +" where IF('" + parent_operation_name + "' = '', operation_name LIKE '%%',operation_name =?) "
//                    +" and mt.active='Y'";
//
//        int operation_parent_name_id = 0;
//        try {
//            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
//
//            stmt.setString(1, parent_operation_name);
//
//            ResultSet rs = stmt.executeQuery();
//            rs.next();
//            operation_parent_name_id = rs.getInt(1);
//        } catch (Exception e) {
//            System.out.println("Error inside getNoOfRows CommandModel" + e);
//        }
//        System.out.println("operation_parent_name_id in Table for search is" + operation_parent_name_id);
//        return operation_parent_name_id;
//    }

    public int getParentOperationId(String parent_operation_name) {
        String query1 = "select id as operation_id "
                + " from operation_name mt "
                + " where IF('" + parent_operation_name + "' = '', operation_name LIKE '%%',operation_name =?) "
                + " and mt.active='Y'";

        int operation_parent_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, parent_operation_name);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            operation_parent_name_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("operation_parent_name_id in Table for search is" + operation_parent_name_id);
        return operation_parent_name_id;
    }

    public int insertRecord(OperationNameBean operationNameBean) {
        int parent_id;
       String IsSuperChild=operationNameBean.getIs_super_child();
//       String IsSuperParent=operationNameBean.getIs_super_parent();
        int rowsAffected = 0;
        String query = " insert into operation_name(operation_name,parent_id,remark,is_super_child) values(?,?,?,?) ";
//        if (IsSuperParent!=null) {
//            
//              if (IsSuperParent.equals("on")) {
//                  parent_id = 0;
//                   try {
//                java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//
//                pstmt.setString(1, operationNameBean.getOperation_name());
//                pstmt.setInt(2, parent_id);
//                pstmt.setString(3, operationNameBean.getRemark());
//                pstmt.setString(4, IsSuperChild);
//                pstmt.setString(5, IsSuperParent);
//                pstmt.setString(6, operationNameBean.getNo_child());
//                
//                rowsAffected = pstmt.executeUpdate();
//            } catch (Exception e) {
//                System.out.println("Error while inserting record...." + e);
//            }
//              }else if(IsSuperParent.equals("off"))
//              {
//                parent_id = getParentOperationId(operationNameBean.getParent_operation());
//                   try {
//                java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//
//                pstmt.setString(1, operationNameBean.getOperation_name());
//                pstmt.setInt(2, parent_id);
//                pstmt.setString(3, operationNameBean.getRemark());
//                  pstmt.setString(4, IsSuperChild);
//                pstmt.setString(5, IsSuperParent);
//                pstmt.setString(6, operationNameBean.getNo_child());
//
//                rowsAffected = pstmt.executeUpdate();
//            } catch (Exception e) {
//                System.out.println("Error while inserting record...." + e);
//            }
//              }
//            
//        } else {
            
            parent_id = getParentOperationId(operationNameBean.getParent_operation());
        
            try {
                java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

                pstmt.setString(1, operationNameBean.getOperation_name());
                pstmt.setInt(2, parent_id);
                pstmt.setString(3, operationNameBean.getRemark());
                  pstmt.setString(4, IsSuperChild);
//                pstmt.setString(5, IsSuperParent);
//                pstmt.setString(6, operationNameBean.getNo_child());

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

    public boolean reviseRecords(OperationNameBean operationNameBean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        String query1 = " SELECT max(revision_no) revision_no FROM operation_name c WHERE c.id = " + operationNameBean.getOperation_name_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE operation_name SET active=? WHERE id = ? && revision_no = ? ";
        String query3 = " INSERT INTO operation_name (id,operation_name,parent_id,remark,is_super_child,revision_no,active) VALUES (?,?,?,?,?,?,?) ";
        int parent_id = getParentOperationId(operationNameBean.getParent_operation());
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, operationNameBean.getOperation_name_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, operationNameBean.getOperation_name_id());
                    psmt.setString(2, operationNameBean.getOperation_name());
                    psmt.setInt(3, parent_id);
                    psmt.setString(4, operationNameBean.getRemark());
                     psmt.setString(5, operationNameBean.getIs_super_child());
                    psmt.setInt(6, rev);
                    psmt.setString(7, "Y");
                   

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CommandModel reviseRecord() Error: " + e);
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

    public int getNoOfRows(String searchOperationname) {
        String query1 = "select count(*) "
                + " from operation_name mt "
                + " where IF('" + searchOperationname + "' = '', operation_name LIKE '%%',operation_name =?) "
                + " and mt.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchOperationname);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<OperationNameBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOperationname) {
        List<OperationNameBean> list = new ArrayList<OperationNameBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//        String query2 = "select m.id,m.operation_name,m.remark,m.is_super_child,op.operation_name as parent_operation  "
//                + " from operation_name m left join operation_name op on m.parent_id = op.id "
//                + " where IF('" + searchOperationname + "' = '', m.operation_name LIKE '%%',m.operation_name =?) "
//                + " and m.active='Y' and op.active = 'Y' "
//                + addQuery;
 String query2 = "select m.id,m.operation_name,m.remark,m.is_super_child,op.operation_name as parent_operation  "
                + " from operation_name m , operation_name op "
                + " where IF('" + searchOperationname + "' = '', m.operation_name LIKE '%%',m.operation_name =?) "
                + " and m.parent_id = op.id and m.active='Y' and op.active = 'Y' "
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchOperationname);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OperationNameBean operationNameBean = new OperationNameBean();

                operationNameBean.setOperation_name_id(rset.getInt("id"));
                operationNameBean.setOperation_name(rset.getString("operation_name"));
                operationNameBean.setParent_operation(rset.getString("parent_operation"));
                operationNameBean.setRemark(rset.getString("remark"));
                 operationNameBean.setIs_super_child(rset.getString("is_super_child"));
//                  operationNameBean.setIs_super_parent(rset.getString("is_super_parent"));
//                   operationNameBean.setNo_child(rset.getString("no_child"));
                list.add(operationNameBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int deleteRecord(int model_type_id) {

        String query = "update operation_name set active='N' where id=" + model_type_id;
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

    public List<String> getOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name where active='Y' "
                + " group by operation_name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("operation_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
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
