/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class DevOpComMapModel {

    private static Connection connection;
    private String driverClass;
    private static String connectionString;
    private String db_username;
    private String db_password;
    DevOpComMapBean commandBean = new DevOpComMapBean();
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

    public int getDeviceId(int manufacturer_id, int deviceType_id, int model_id) {
        String query = " select id from device where manufacture_id=" + manufacturer_id + " and device_type_id=" + deviceType_id + " and model_id=" + model_id + " and active='Y'";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                device_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDeviceId CommandModel " + e);
        }

        return device_id;
    }

    public int getDevice_op_comId(int device_commamnd_id) {
        String query = "SELECT * FROM device_command_map ORDER BY device_command_id DESC LIMIT 0, 1";

        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                device_commamnd_id = rs.getInt("device_command_id") + 1;
            }
        } catch (Exception e) {
            System.out.println("Error inside getDeviceId CommandModel " + e);
        }

        return device_commamnd_id;
    }

    public int getOperationId(String operation_name) {
        String query = " select op.id from operation_name op where operation_name='" + operation_name + "' and active='Y'";
        int operation_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                operation_name_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return operation_name_id;
    }

    public int getCommandId(String command) {
        String query = " select ct.id from command ct where ct.command='" + command + "' and active='Y'";
        int command_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                command_name_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return command_name_id;
    }

    public int getManufacturerId(String manufacturere_type) {
        String query = " select id from manufacturer m where m.name='" + manufacturere_type + "' and m.active='Y'";
        int manufacturerId = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                manufacturerId = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return manufacturerId;
    }

    public int getDeviceTypeId(String device_type) {
        String query = " select id from device_type d where d.type='" + device_type + "' and d.active='Y'";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                device_type_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return device_type_id;
    }

//    public List<String> getCommand1(String q, String command_type1) {
//        List<String> list = new ArrayList<String>();
//        String[] arrSplit = command_type1.split("-");
//
//        String shorthand = arrSplit[0];
//        String command_type = arrSplit[1];
//        String query = " select command from command c,device_command_map dcm,command_type ct"
//                + " where dcm.command_id=c.id and c.command_type_id = ct.id and ct.shorthand= '" + shorthand + "' group by command order by command desc ";
//        try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String command = rset.getString("command");
//                if (command.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(command);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such Command Name exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println("ERROR inside Command Model - " + e);
//            message = "Something going wrong";
//            //messageBGColor = "red";
//        }
//        return list;
//    }
    public List<String> getCommand1(String q, String short_name1) {
        List<String> list = new ArrayList<String>();

        String query = " select command from command "
                + " where remark= '" + short_name1 + "' and active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("command");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getCommandUseShortName(String q, String short_name1) {
        List<String> list = new ArrayList<String>();

        String query = " select command from command c,device_command_map dcm"
                + " where dcm.command_id=c.id and c.remark= '" + short_name1 + "' group by command order by command desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("command");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public int getModelId(String device_name, String device_no) {
        String query = " select id from model m where device_name='" + device_name + "' and device_no='" + device_no + "' and active='Y'";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                model_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return model_id;
    }

//    public static List<DevOpComMapBean> showReport(int device_command_id, DevOpComMapBean userEntryByImageBean1,String searchCommandName,String searchDeviceName,String searchOperationName  ){
//        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
//        List<DevOpComMapBean> list1 = new ArrayList<DevOpComMapBean>();
//        DevOpComMapBean tpBean = new DevOpComMapBean();
//      
//
//        Map<String, String> map = new HashMap<String, String>();
//        String query="select md.device_name,dcm.device_command_id,m.name as manu_name,dt.type ,md.device_no as mno,opn.operation_name,c.command,c.id,dcm.remark"
//                    + " from device_command_map dcm,device d,model md ,manufacturer m ,device_type dt,operation_name opn,command c"
//                    + " where dcm.device_id=d.id and d.active='Y' and d.model_id = md.id and d.manufacture_id = m.id and d.device_type_id = dt.id  and dcm.operation_id=opn.id and dcm.command_id=c.id"
//                    + " and dcm.active='Y' and md.active='Y' and m.active='Y' and dt.active='Y' and opn.active='Y' and c.active='Y'"
//                    +" and device_command_id="+ device_command_id; 
//                      
//        try{
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//           
//             
//             ResultSet rs = pstmt.executeQuery();
//            while(rs.next()){
//               // userEntryByImageBean tpBean = new userEntryByImageBean();
//                //String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));
//
//                 device_command_id = rs.getInt("device_command_id");
//                String device_name = rs.getString("device_name");
//                String operation_name = rs.getString("operation_name");
//                String command = rs.getString("command");
//                String manufacturer = rs.getString("manu_name");
//                String device_type = rs.getString("type");
//                String device_no = rs.getString("id");
//                tpBean.setDevice_command_id(device_command_id);
//                tpBean.setDevice_name(device_name);
//                tpBean.setCommand(command);
//                tpBean.setOperation_name(operation_name);
//                tpBean.setManufacturer(manufacturer);
//                tpBean.setDevice_type(device_type);
//                tpBean.setDevice_no(device_no);
//               
//                //list.add(tpBean);
//                
//            }                      
//                 //list.add(list1);
//           // showMISData2(tpBean,device_command_id);
//            list.add(tpBean);
//            //}
//        }catch(Exception ex){
//            System.out.println("ERROR : in showData() in Model : " + query);
//        }
//        return list;
//    }
//    public static List<DevOpComMapBean> showReport(int device_command_id, DevOpComMapBean userEntryByImageBean1, String searchCommandName, String searchDeviceName, String searchOperationName) {
//        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
//        List<DevOpComMapBean> list1 = new ArrayList<DevOpComMapBean>();
//        DevOpComMapBean tpBean = new DevOpComMapBean();
//
//        Map<String, String> map = new HashMap<String, String>();
////        String query="select md.device_name,dcm.device_command_id,m.name as manu_name,dt.type ,md.device_no as mno,opn.operation_name,c.command,c.id,dcm.remark"
////                    + " from device_command_map dcm,device d,model md ,manufacturer m ,device_type dt,operation_name opn,command c"
////                    + " where dcm.device_id=d.id and d.active='Y' and d.model_id = md.id and d.manufacture_id = m.id and d.device_type_id = dt.id  and dcm.operation_id=opn.id and dcm.command_id=c.id"
////                    + " and dcm.active='Y' and md.active='Y' and m.active='Y' and dt.active='Y' and opn.active='Y' and c.active='Y'"
////                    +" and device_command_id="+ device_command_id; 
//            
//            
//            String query = "select dcm.device_command_id,dt.type,m.name as manu_name,mt.type as model_type,opn.operation_name,c.command,cds.description,dcm.remark \n"
//                + "from device_command_map dcm,device d,model md ,modal_type mt,manufacturer m ,device_type dt,operation_name opn,command c,command_description cds \n"
//                + "where dcm.device_id=d.id  and d.device_type_id=dt.id \n"
//                + "and dcm.device_id=d.id and d.manufacture_id = m.id \n"
//                + "and d.model_id = md.id and mt.id=md.model_type_id\n"
//                + "and dcm.operation_id=opn.id  \n"
//                + "and dcm.command_id=c.id\n"
//                + "and cds.command_id=c.id\n"
//                + " and dcm.active='Y' and md.active='Y' and m.active='Y' and dt.active='Y' \n"
//                + "and opn.active='Y' and c.active='Y' and d.active='Y'  and device_command_id=7 "
//                + " and device_command_id=" + device_command_id;
//
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                // userEntryByImageBean tpBean = new userEntryByImageBean();
//                //String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));
//
//                device_command_id = rs.getInt("device_command_id");
////                String device_name = rs.getString("device_name");
//               
//                String description = rs.getString("description");
//                 String model_type= rs.getString("model_type");
//                String operation_name = rs.getString("operation_name");
//                String command = rs.getString("command");
//                String manufacturer = rs.getString("manu_name");
//                String device_type = rs.getString("type");                      //device_type
////                String device_no = rs.getString("id");
//
//                tpBean.setDevice_command_id(device_command_id);
//                tpBean.setDescription(description);
//                 tpBean.setModel_type(model_type);
//               
//                tpBean.setOperation_name(operation_name);
//                 tpBean.setCommand(command);
//                tpBean.setManufacturer(manufacturer);
//                tpBean.setDevice_type(device_type);
////                tpBean.setDevice_no(device_no);
//
//                //list.add(tpBean);
//            }
//            //list.add(list1);
//            // showMISData2(tpBean,device_command_id);
//            list.add(tpBean);
//            //}
//        } catch (Exception ex) {
//            System.out.println("ERROR : in showData() in Model : " + query);
//        }
//        return list;
//    }
//    public static List<DevOpComMapBean> showReport(int device_command_id, DevOpComMapBean userEntryByImageBean1, String searchCommandName, String searchDeviceName, String searchOperationName) {
//        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
//        List<DevOpComMapBean> list1 = new ArrayList<DevOpComMapBean>();
//        DevOpComMapBean tpBean = new DevOpComMapBean();
//
//        Map<String, String> map = new HashMap<String, String>();
//        String device_type = null;
//        String query = "select dt.type from device_type dt,device_command_map dcm,device d "
//                + " where dcm.device_id=d.id and d.device_type_id=dt.id  and dcm.active='Y' "
//                + " and dt.active='Y' and d.active='Y' and dcm.device_command_id=" + device_command_id;
//
//        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                device_type = rs.getString("type");
//                tpBean.setManufacturer(device_type);
//            }
//        } catch (Exception ex) {
//            System.out.println("ERROR : in showData() in Model : " + query);
//        }
//        String query1 = "select m.name as manu_name from manufacturer m,device_type dt,device_command_map dcm,device d where dcm.device_id=d.id and d.manufacture_id=m.id and dt.type='" + device_type + "'";
//        try {
//            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query1);
//            ResultSet rst = pst.executeQuery();
//            if (rst.next()) {
//
//                String manufacturer = rst.getString("manu_name");
//                tpBean.setManufacturer(manufacturer);
//            }
//        } catch (Exception ex) {
//            System.out.println("ERROR : in showData() in Model : " + query1);
//        }
//
//        return list;
//    }
    public static List<DevOpComMapBean> showReport(int device_command_id, DevOpComMapBean userEntryByImageBean1, String searchCommandName, String searchDeviceName, String searchOperationName) {
        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
        List<DevOpComMapBean> list1 = new ArrayList<DevOpComMapBean>();
        DevOpComMapBean tpBean = new DevOpComMapBean();
        DevOpComMapBean tpBean_manu = new DevOpComMapBean();
        DevOpComMapBean tpBean_model_type = new DevOpComMapBean();
        Map<Object, Map<Object, Map<Object, Map<Object, Map<Object, Object>>>>> map1 = new HashMap<>();
        Map<Object, Map<Object, Map<Object, Map<Object, Object>>>> map2 = new HashMap<>();
        Map<Object, Map<Object, Map<Object, Object>>> map3 = new HashMap<>();
        Map<Object, Map<Object, String>> map4 = new HashMap<>();
        Map<Object, String> map5 = new HashMap<>();
        List<String> ls1 = new ArrayList<>();

        String device_type = "";
        String manufacturer = "";
        String model_type = "";
        String operation = "";
        String command = "";
        String description = "";
        int i = 0;

        String query = "select dcm.device_command_id,dt.type as device_type,m.name as manufacturer,mt.type as model_type,opn.operation_name,c.command,cds.description,dcm.remark \n"
                + "from device_command_map dcm,device d,model md ,modal_type mt,manufacturer m ,device_type dt,operation_name opn,command c,command_description cds \n"
                + "where dcm.device_id=d.id  and d.device_type_id=dt.id \n"
                + "and dcm.device_id=d.id and d.manufacture_id = m.id \n"
                + "and d.model_id = md.id and mt.id=md.model_type_id\n"
                + "and dcm.operation_id=opn.id  \n"
                + "and dcm.command_id=c.id\n"
                + "and cds.command_id=c.id\n"
                + " and dcm.active='Y' and md.active='Y' and m.active='Y' and dt.active='Y' \n"
                + "and opn.active='Y' and c.active='Y' and d.active='Y' "
                + " and device_command_id=" + device_command_id;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            int k = 0;
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();

//            while (rs.next()) {
//                String dt = rs.getString("device_type");
//                String man = rs.getString("manufacturer");
//                String m_type = rs.getString("model_type");
//                if (device_type.equals("")) {
//                    device_type = dt;
//                    map5.put(man, m_type);
//                } else if(device_type.equals(dt)) {
//                    map5.put(man, m_type);
//                } else if(!device_type.equals(dt)){
//                    map4.put(device_type, map5);
//                    device_type = dt;
//                    map5 = new HashMap<>();
//                    map5.put(man,m_type);
//                }
//                
//                if(k == rows-1) {
//                    map4.put(device_type, map5);
//                }
//                k++;
//                
//            }
            while (rs.next()) {
                String dt = rs.getString("device_type");
                String man = rs.getString("manufacturer");
                String m_type = rs.getString("model_type");
                if (device_type.equals("")) {
                    device_type = dt;
                    map5.put(man, m_type);
                } else if (device_type.equals(dt)) {
                    map5.put(man, m_type);
                } else if (!device_type.equals(dt)) {
                    map4.put(device_type, map5);
                    device_type = dt;
                    map5 = new HashMap<>();
                    map5.put(man, m_type);
                }

                if (k == rows - 1) {
                    map4.put(device_type, map5);
                }
                k++;

            }
        } catch (Exception ex) {
            System.out.println("ERROR : in showData() in Model : " + query);
        }

        return list;
    }

    public static List<DevOpComMapBean> showPDF(DevOpComMapBean dcm, String searchCommandName, String searchDeviceName, String searchOperationName) {
        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
        Map<String, String> map = new HashMap<String, String>();
        String query = " select dcm.device_command_id, "
                + " dcm.device_id,dcm.remark,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type  "
                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'";
//                      + " and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
//                     + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
//                     + " and iF('" + searchOperationName +"'='',opn.operation_name LIKE '%%',opn.operation_name=?)"     

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//                 pstmt.setString(1, searchCommandName);
//            pstmt.setString(2, searchDeviceName);
//            pstmt.setString(3, searchOperationName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // List<device_operation_command> list1 = new ArrayList<device_operation_command>();
                DevOpComMapBean dcm1 = new DevOpComMapBean();
                dcm1.setDevice_command_id(rs.getInt("device_command_id"));
                //  commandBean.setCommand_id(rset.getInt("id"));

                dcm1.setManufacturer(rs.getString("name"));
                dcm1.setDevice_type(rs.getString("type"));
                dcm1.setDevice_name(rs.getString("device_name"));
                dcm1.setDevice_no(rs.getString("device_no"));
                String command = rs.getString("command");

//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                
//                String hex = bytesToHex(b);
                dcm1.setCommand(command);

                dcm1.setOperation_name(rs.getString("operation_name"));

                dcm1.setRemark(rs.getString("remark"));

                list.add(dcm1);
                //list.add(list1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public static List<DevOpComMapBean> getDevice() {
        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
        List<DevOpComMapBean> list1 = new ArrayList<DevOpComMapBean>();

        int id = 0;
        int id1 = 0;
        int id2 = 0;
        int id3 = 0;
        String query = " select distinct dt.id as device_type_id, dt.type from device_type dt,device d where d.device_type_id=dt.id and d.active='Y' and dt.active='Y'";
//        String query2 = " select distinct  mf.id,mf.name,dt.id from manufacturer mf,device d,device_type dt where mf.id=d.manufacture_id and dt.id=? and d.active='Y' and mf.active='Y'  and dt.active='Y'";
        String query2 = " select distinct  mf.id as manufacture_id,mf.name,dt.id as device_type_id from manufacturer mf,device d,device_type dt where mf.id=d.manufacture_id and d.device_type_id=dt.id and dt.id=? and d.active='Y' and mf.active='Y'  and dt.active='Y' order by mf.id asc";
//        String query3 = "  select distinct m.id, m.device_name,mf.id from model m,device d,manufacturer mf where d.model_id=m.id and mf.id=? and m.active='Y' and d.active='Y' and mf.active='Y' ";
        String query3 = " select distinct mt.id as model_type_id, mt.type as model_type,mf.id as manufacture_id from model m,device d,manufacturer mf,modal_type mt where d.model_id=m.id and d.manufacture_id=mf.id and m.model_type_id=mt.id and mf.id=? and m.active='Y' and d.active='Y' and mf.active='Y' and mt.active='Y'  order by mt.id asc";
//String query4 = " select dcm.device_command_id, op.operation_name,m.id from device_command_map dcm,operation_name op,model m where  dcm.operation_id=op.id  and m.id=? and dcm.active='Y'  and op.active='Y' and m.active='Y'";
        String query4 = " select distinct op.id as operation_id,op.operation_name,mt.id model_type_id from device_command_map dcm,operation_name op,model m ,modal_type mt,device d "
                + " where  dcm.operation_id=op.id and dcm.device_id=d.id and d.model_id=m.id "
                + " and m.model_type_id=mt.id and mt.id=? and dcm.active='Y'  and op.active='Y' and m.active='Y' and mt.active='Y' and d.active='Y' order by op.id asc";
//        String query5 = " select dcm.device_command_id, c.command,dcm.operation_id from device_command_map dcm,command c where  c.id=dcm.command_id and dcm.operation_id=? and dcm.active='Y'  and c.active='Y'";
        String query5 = " select distinct c.id as command_id,c.command,op.id as operation_id from device_command_map dcm,command c,operation_name op where op.id=dcm.operation_id and dcm.command_id=c.id and op.id=? and dcm.active='Y'  and c.active='Y' and op.active='Y' order by c.id asc";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                DevOpComMapBean bean = new DevOpComMapBean();

                bean.setName(rset.getString("type"));
                List<DevOpComMapBean> manufacturer = new ArrayList<>();
                id = Integer.parseInt(rset.getString("device_type_id"));

                PreparedStatement pstmt = connection.prepareStatement(query2);

                pstmt.setInt(1, Integer.parseInt(rset.getString("device_type_id")));
                ResultSet rset1 = pstmt.executeQuery();

                while (rset1.next()) {
                    DevOpComMapBean bean2 = new DevOpComMapBean();
                    List<DevOpComMapBean> model = new ArrayList<>();
                    bean2.setName(rset1.getString("name"));

                    PreparedStatement pstmt1 = connection.prepareStatement(query3);
                    id1 = Integer.parseInt(rset1.getString("manufacture_id"));
                    pstmt1.setInt(1, Integer.parseInt(rset1.getString("manufacture_id")));

                    ResultSet rset2 = pstmt1.executeQuery();

                    while (rset2.next()) {
                        DevOpComMapBean bean3 = new DevOpComMapBean();
//                        String device = rset2.getString("model_type");
                        bean3.setName(rset2.getString("model_type"));

                        List<DevOpComMapBean> operation = new ArrayList<>();
                        PreparedStatement pstmt2 = connection.prepareStatement(query4);
                        id2 = Integer.parseInt(rset2.getString("model_type_id"));
                        pstmt2.setInt(1, Integer.parseInt(rset2.getString("model_type_id")));
                        ResultSet rset3 = pstmt2.executeQuery();

                        while (rset3.next()) {
                            DevOpComMapBean bean4 = new DevOpComMapBean();
                            bean4.setName(rset3.getString("operation_name"));

                            List<DevOpComMapBean> command = new ArrayList<>();
                            PreparedStatement pstmt3 = connection.prepareStatement(query5);
                            id3 = Integer.parseInt(rset3.getString("operation_id"));
                            pstmt3.setInt(1, Integer.parseInt(rset3.getString("operation_id")));
                            ResultSet rset4 = pstmt3.executeQuery();
                            while (rset4.next()) {
                                DevOpComMapBean bean5 = new DevOpComMapBean();
                                bean5.setName(rset4.getString("command"));

                                command.add(bean5);
                            }
                            bean4.setDevice(command);
                            operation.add(bean4);
                        }
                        bean3.setDevice(operation);
                        model.add(bean3);
                    }
                    bean2.setDevice(model);
                    manufacturer.add(bean2);
                }

                bean.setDevice(manufacturer);

                list1.add(bean);
                count++;

            }

            if (count == 0) {
                //   list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);

            //messageBGColor = "red";
        }
        return list1;
    }

    public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (JRException e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int getMaxDeviceCommandId() {
        String query = " select Max(device_command_id) from device_command_map dcm";
        int manufacturerId = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                manufacturerId = rs.getInt("device_command_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return manufacturerId;
    }

    public int insertRecord1(DevOpComMapBean commandBean) {
        int deviceType_id = getDeviceTypeId(commandBean.getDevice_type());
        int manufdacture_id = getManufacturerId(commandBean.getManufacturer());
        int model_id = getModelId(commandBean.getDevice_name(), commandBean.getDevice_no());
        int device_id = getDeviceId(manufdacture_id, deviceType_id, model_id);
        int operation_id = getOperationId(commandBean.getOperation_name());
        int command_type_id = getCommandId(commandBean.getCommand());
        int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());

        String query = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,order_no,delay,remark,short_name) "
                + " values(?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, device_command_id + 1);
            pstmt.setInt(2, device_id);
            pstmt.setInt(3, command_type_id);
            pstmt.setInt(4, operation_id);
            pstmt.setString(5, commandBean.getOrder_no());
            pstmt.setString(6, commandBean.getDelay());
            pstmt.setString(7, commandBean.getRemark());
            pstmt.setString(8, commandBean.getShort_name());
            System.err.println("querry -" + pstmt);
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

//    public boolean reviseRecords(DevOpComMapBean commandBean, int device_command_id) {
//        boolean status = false;
//        String query = "";
//        int rowsAffected = 0;
//
//        int device_id = getDeviceTypeId(commandBean.getDevice_type());
//        int operation_id = getOperationId(commandBean.getOperation_name());
//        int command_id = getCommandId(commandBean.getCommand());
////       int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());
//
//        String query1 = " SELECT max(revision_no) revision_no FROM device_command_map c WHERE c.device_command_id = " + device_command_id + " AND active='Y' ORDER BY revision_no DESC";
//        String query2 = " UPDATE device_command_map SET active=? WHERE device_command_id = ? AND revision_no = ? ";
//        String query3 = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,remark,revision_no,active,order_no,delay) VALUES (?,?,?,?,?,?,?,?,?) ";
//
//        int updateRowsAffected = 0;
//        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//                pst.setString(1, "N");
//                pst.setInt(2, device_command_id);
//                pst.setInt(3, rs.getInt("revision_no"));
//                updateRowsAffected = pst.executeUpdate();
//                if (updateRowsAffected >= 1) {
//                    int rev = rs.getInt("revision_no") + 1;
//                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//                    psmt.setInt(1, device_command_id);
//                    psmt.setInt(2, device_id);
//                    psmt.setInt(3, command_id);
//                    psmt.setInt(4, operation_id);
//
//                    psmt.setString(5, commandBean.getRemark());
//
//                    psmt.setInt(6, rev);
//                    psmt.setString(7, "Y");
//                    psmt.setString(8, commandBean.getOrder_no());
//                    psmt.setString(9, commandBean.getDelay());
//
//                    int a = psmt.executeUpdate();
//                    if (a > 0) {
//                        status = true;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("CommandModel reviseRecord() Error: " + e);
//        }
//        if (status) {
//            message = "Record updated successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//            message = "Record Not updated Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not updated");
//        }
//
//        return status;
//
//    }
    public boolean reviseRecords1(DevOpComMapBean commandBean, int device_command_id) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        PreparedStatement ps = null;
//        int device_id = getDeviceTypeId(commandBean.getDevice_type());
//        int operation_id = getOperationId(commandBean.getOperation_name());
//        int command_id = getCommandId(commandBean.getCommand());
//       int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());
        int deviceType_id = getDeviceTypeId(commandBean.getDevice_type());
        int manufdacture_id = getManufacturerId(commandBean.getManufacturer());
        int model_id = getModelId(commandBean.getDevice_name(), commandBean.getDevice_no());
        int device_id = getDeviceId(manufdacture_id, deviceType_id, model_id);
        int operation_id = getOperationId(commandBean.getOperation_name());
        int command_type_id = getCommandId(commandBean.getCommand());
//        int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());
        String query1 = " SELECT max(revision_no) revision_no FROM device_command_map c WHERE c.device_command_id = " + device_command_id + " AND active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE device_command_map SET active=? WHERE device_command_id = ? AND revision_no = ? ";
        String query3 = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,remark,revision_no,active,order_no,delay,short_name) VALUES (?,?,?,?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, device_command_id);
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    ps = (PreparedStatement) connection.prepareStatement(query3);
                    ps.setInt(1, device_command_id);
                    ps.setInt(2, device_id);
                    ps.setInt(3, command_type_id);
                    ps.setInt(4, operation_id);
                    ps.setString(5, commandBean.getRemark());
                    ps.setInt(6, rev);
                    ps.setString(7, "Y");
                    ps.setString(8, commandBean.getOrder_no());
                    ps.setString(9, commandBean.getDelay());
                    ps.setString(10, commandBean.getShort_name());
                    int a = ps.executeUpdate();
                    if (a > 0) {
                        connection.commit();
                        status = true;
                    } else {
                        connection.rollback();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CommandModel reviseRecord() Error: " + e);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DevOpComMapModel.class.getName()).log(Level.SEVERE, null, ex);
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

    public static int getNoOfRows(String searchCommandName, String searchDeviceName, String searchOperationName, String searchDeviceTypeName) {
//        String query1 = " select count(*)"
//                + " from device_command_map dcm,device d,command c,operation_name opn"
//                + " where dcm.device_id = d.id and dcm.command_id = c.id and dcm.operation_id = opn.id and dcm.active='Y' and d.active='Y' and c.active='Y' and opn.active='Y'";
        String query1 = "  select distinct count(*)"
                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'  "
                + " and IF('" + searchCommandName + "' = '', dcm.short_name LIKE '%%',dcm.short_name =?) "
                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
                + " and IF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
                + " and IF('" + searchDeviceTypeName + "'='',dt.type LIKE '%%',dt.type=?)";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, searchCommandName);
            stmt.setString(2, searchDeviceName);
            stmt.setString(3, searchOperationName);
            stmt.setString(4, searchDeviceTypeName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public static List<DevOpComMapBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCommandName, String searchDeviceName, String searchOperationName, String searchDeviceTypeName) {
        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//
//        String query3 = " select dcm.device_command_id, "
//                + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type  "
//                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
//                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
//                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'"
//                + " and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
//                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
//                + " and iF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
//                + addQuery;

        String query3 = " select dcm.device_command_id, "
                + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,ct.name as command_type,c.command,mf.name,m.device_name,m.device_no,dt.type  "
                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt,command_type ct "
                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y' and ct.active='Y'  "
                + " and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
                + " and iF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
                + " and iF('" + searchDeviceTypeName + "'='',dt.type LIKE '%%',dt.type=?)"
                + addQuery;
        try {

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query3);
            pstmt.setString(1, searchCommandName);
            pstmt.setString(2, searchDeviceName);
            pstmt.setString(3, searchOperationName);
            pstmt.setString(4, searchDeviceTypeName);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DevOpComMapBean commandBean = new DevOpComMapBean();

                commandBean.setDevice_command_id(rset.getInt("device_command_id"));
                //  commandBean.setCommand_id(rset.getInt("id"));

                commandBean.setManufacturer(rset.getString("name"));
                commandBean.setDevice_type(rset.getString("type"));
                commandBean.setDevice_name(rset.getString("device_name"));
                commandBean.setDevice_no(rset.getString("device_no"));
                commandBean.setCommand_type(rset.getString("command_type"));
                String command = rset.getString("command");
                commandBean.setOrder_no(rset.getString("order_no"));
                commandBean.setDelay(rset.getString("delay"));
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                commandBean.setCommand(command);

                commandBean.setOperation_name(rset.getString("operation_name"));

                commandBean.setRemark(rset.getString("remark"));

                list.add(commandBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    ///////////////////////////short name according show data
    public static List<DevOpComMapBean> showData1(int lowerLimit, int noOfRowsToDisplay, String searchCommandName, String searchDeviceName, String searchOperationName, String searchDeviceTypeName) {
        List<DevOpComMapBean> list = new ArrayList<DevOpComMapBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

//        String query3 = " select dcm.device_command_id, "
//                + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type,dcm.short_name "
//                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
//                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
//                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'  "
//                + " and IF('" + searchCommandName + "' = '', dcm.short_name LIKE '%%',dcm.short_name =?) "
//                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
//                + " and IF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
//                + " and IF('" + searchDeviceTypeName + "'='',dt.type LIKE '%%',dt.type=?)"
//                + " ORDER BY dcm.order_no ASC "
//                + addQuery;
        String query3 = "  select distinct dcm.device_command_id, "
                + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type,dcm.short_name "
                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'  "
                + " and IF('" + searchCommandName + "' = '', dcm.short_name LIKE '%%',dcm.short_name =?) "
                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
                + " and IF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
                + " and IF('" + searchDeviceTypeName + "'='',dt.type LIKE '%%',dt.type=?)"
                + " ORDER BY dcm.order_no ASC ";
        //+ addQuery;
//        SELECT COUNT(CustomerID), Country
//FROM Customers
// GROUP BY dcm.order_no 
//ORDER BY COUNT(CustomerID) DESC;
//  String query3 = " select dcm.device_command_id, "
//                + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,ct.name as command_type,c.command,mf.name,m.device_name,m.device_no,dt.type,dcm.short_name  "
//                + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt,command_type ct "
//                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id "
//                + "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y' and ct.active='Y' "
//                + " and IF('" + searchCommandName + "' = '', dcm.short_name LIKE '%%',dcm.short_name =?) "
//                + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
//                + " and IF('" + searchOperationName + "'='',opn.operation_name LIKE '%%',opn.operation_name=?)"
//                + " and IF('" + searchDeviceTypeName + "'='',dt.type LIKE '%%',dt.type=?)"
//                + addQuery;
        try {
            System.err.println("------------ device op comand map query -----------" + query3);
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query3);
            pstmt.setString(1, searchCommandName);
            pstmt.setString(2, searchDeviceName);
            pstmt.setString(3, searchOperationName);
            pstmt.setString(4, searchDeviceTypeName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DevOpComMapBean commandBean = new DevOpComMapBean();

                commandBean.setDevice_command_id(rset.getInt("device_command_id"));
                //  commandBean.setCommand_id(rset.getInt("id"));

                commandBean.setManufacturer(rset.getString("name"));
                commandBean.setDevice_type(rset.getString("type"));
                commandBean.setDevice_name(rset.getString("device_name"));
                commandBean.setDevice_no(rset.getString("device_no"));
//                commandBean.setCommand_type(rset.getString("command_type"));
                String command = rset.getString("command");
                commandBean.setOrder_no(rset.getString("order_no"));
                commandBean.setDelay(rset.getString("delay"));
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                commandBean.setCommand(command);

                commandBean.setOperation_name(rset.getString("operation_name"));

                commandBean.setRemark(rset.getString("remark"));
                commandBean.setShort_name(rset.getString("short_name"));

                list.add(commandBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    /////////////////end 
    public static String bytesToHex(Byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public int deleteRecord(int device_command_id) {

        String query = "update device_command_map set active='N' where device_command_id=" + device_command_id;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public List<String> getDeviceType(String q, String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct dt.type "
                + " from device d inner join manufacturer m on d.manufacture_id = m.id"
                + " inner join model ml on d.model_id = ml.id"
                + " inner join device_type dt on dt.id = d.device_type_id"
                + " inner join modal_type mt on ml.model_type_id = mt.id"
                + " where d.manufacture_id = (select id from manufacturer m where m.name=? and m.active='Y')"
                + " and d.device_type_id = dt.id  and dt.active='Y' and d.active='Y' and m.active='Y' and mt.active='Y' and ml.active = 'Y'";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, manufacturer_name);
            //System.err.println("query device type -"+pstmt);
            System.err.println("--- get device type ------------" + pstmt);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("type");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
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

    public List<String> getManufacturerName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='y' order by name desc";
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
            System.out.println(" ERROR inside DevOpComMapModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getModelName(String q, String manufacturer, String devicetype) {
        List<String> list = new ArrayList<String>();
        int man_id=getManufacturerId(manufacturer);
        int dev_typ_id=getDeviceTypeId(devicetype);
        String query = " select m.device_name from device d, model m  where d.manufacture_id="+man_id+" and d.device_type_id="+dev_typ_id+" "
                + " and d.active='Y' and m.active='Y' and m.id=d.model_id ";
        try {
            System.err.println("query model name -"+query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_name = rset.getString("device_name");
                if (device_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Model Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getModelNo(String q, String device_name, String manufacturer, String devicetype) {
        List<String> list = new ArrayList<String>();
        String query = " select device_no from model where active='Y' and "
                + " device_name=? ";

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, device_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_no = rset.getString("device_no");
                if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside DevOpComMapModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name where active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String operation_name = rset.getString("operation_name");
                if (operation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(operation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getCommandType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name,shorthand from command_type ct where active='Y' group by name order by name desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name = rset.getString("name");
                String shorthand = rset.getString("shorthand");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(shorthand + "-" + name);

                    count++;
                }
            }

            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getShortName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select short_name from command ct where active='Y' group by short_name order by short_name desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String short_name = rset.getString(1);
                if (short_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(short_name);

                    count++;
                }
            }

            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getCommandName(String q, String short_name) {
        List<String> list = new ArrayList<String>();
        int device_type_id = 0;
        int model_name_id = 0;
        int operation_name_id = 0;
        String query = " select command from command "
                + " where active='Y' and short_name='" + short_name + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command_name = rset.getString(1);
                if (command_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select operation_name from operation_name "
                + " where active='Y'and is_super_child='on' group by operation_name order by operation_name desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String operation_name = rset.getString("operation_name");
                if (operation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(operation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getCommand(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select command from command "
                + " where active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("command");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchManufacturerName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select m.name "
                + " from device d,command c,manufacturer m "
                + " where c.device_id = d.id "
                + " and d.manufacture_id = m.id "
                + " and c.active='Y' group by m.name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("name");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select dt.type "
                + " from device d,device_command_map c,device_type dt "
                + " where c.device_id = d.id "
                + " and d.device_type_id = dt.id "
                + " and c.active='Y' group by dt.type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("type");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchDeviceName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = " select device_name "
//                       +" from command c,device d "
//                       +" where c.device_id = d.id "
//                       +" and c.active='Y' group by device_name ";
        String query1 = "select device_name "
                + " from device d,device_command_map c,model m "
                + " where c.device_id = d.id "
                + " and d.model_id = m.id "
                + " and c.active='Y' group by device_name ";
        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_name = rset.getString("device_name");
                if (device_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

}
