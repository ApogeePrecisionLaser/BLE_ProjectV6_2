/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import com.mysql.cj.xdevapi.JsonArray;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saini
 */
public class CommandModel {

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

    public int getOperationId(String operation_name) {
        String query = " select op.id from operation_name op where operation_name='" + operation_name + "'";
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

    public int getCommandTypeId(String command_type) {
        String query = " select ct.id from command_type ct where ct.name='" + command_type + "'";
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

    public int insertRecord(CommandBean commandBean, int sel_no, int inp_no, List<String> inputParameter, List<Integer> SelValList, List<String> SelParameter, List<Integer> selCheckedVal, List<Integer> bitCheckedVal, int bitwise, List<String> BitParamList, List<Integer> BitvaluesList) {
        PreparedStatement psmt;
        ResultSet rst;
        int command_type_id = getCommandTypeId(commandBean.getCommand_type());
        String query2 = "", query3 = "", query4 = "", query5 = "";

        String query = " insert into command(command,starting_del,end_del,remark,command_type_id,format,input,selection,bitwise,short_name) "
                + " values(?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, commandBean.getCommand());
            if (!commandBean.getStarting_del().equals("")) {
                pstmt.setString(2, commandBean.getStarting_del());
            } else {
                pstmt.setString(2, "null");
            }
            if (!commandBean.getStarting_del().equals("")) {
                pstmt.setString(3, commandBean.getEnd_del());
            } else {
                pstmt.setString(3, "null");
            }
            pstmt.setString(4, commandBean.getRemark());
            pstmt.setInt(5, command_type_id);
            pstmt.setString(6, commandBean.getFormat());
            pstmt.setInt(7, commandBean.getInput_no());
            pstmt.setInt(8, commandBean.getSelection_no());
            pstmt.setInt(9, commandBean.getBitwise());
            pstmt.setString(10, commandBean.getShort_name());
            //System.err.println("query insert -" + pstmt);
            System.err.println("------------- insert query ----------" + pstmt);
            rowsAffected = pstmt.executeUpdate();

            int k = 0;
            // main loop
            if (rowsAffected > 0) {
                String command_id = "";
                int parameter_id = 0;
                if (sel_no > 0) {
                    //String command_id = "";
                    //int parameter_id = 0;
                    String qry = " select max(id)as id from command ";
                    psmt = connection.prepareStatement(qry);
                    rst = psmt.executeQuery();
                    while (rst.next()) {
                        command_id = rst.getString(1);
                    }
                    if (command_id == null) {
                        command_id = "1";
                    }

                    // START loop for Selection Type
                    int l = 0;
                    for (int i = 0; i < sel_no; i++) {

                        parameter_id = getParameterId(SelParameter.get(i));

                        for (int j = 0; j < SelValList.get(i); j++) {

                            if (selCheckedVal.get(l) != 0) {

                                psmt = null;
                                rst = null;
                                query2 = " insert into command_param_map(command_id,parameter_id,selection_value_id) "
                                        + " values(?,?,?) ";
                                psmt = connection.prepareStatement(query2);
                                psmt.setString(1, command_id);
                                psmt.setInt(2, parameter_id);
                                psmt.setInt(3, selCheckedVal.get(l));
                                rowsAffected = psmt.executeUpdate();
                            }
                            l++;
                        }

                    }
                }
                // END loop for Selection Type

                // START loop for Input Type
                if (inp_no>0) {
                    if (inputParameter.get(0) != "") {

                        String qry = " select max(id)as id from command ";
                        psmt = connection.prepareStatement(qry);
                        rst = psmt.executeQuery();
                        while (rst.next()) {
                            command_id = rst.getString(1);
                        }
                        if (command_id == null) {
                            command_id = "1";
                        }

                        for (int i = 0; i < inp_no; i++) {
                            psmt = null;
                            rst = null;
                            parameter_id = getParameterId(inputParameter.get(i));

                            query3 = " insert into input(command_id,parameter_id) values(?,?) ";
                            psmt = connection.prepareStatement(query3);
                            psmt.setString(1, command_id);
                            psmt.setInt(2, parameter_id);
                            rowsAffected = psmt.executeUpdate();
                        }

                    }
                }

                // END loop for Input Type
                // START loop for Bitwise Type
                if (bitwise > 0) {
                    String qry = " select max(id)as id from command ";
                    psmt = connection.prepareStatement(qry);
                    rst = psmt.executeQuery();
                    while (rst.next()) {
                        command_id = rst.getString(1);
                    }
                    if (command_id == null) {
                        command_id = "1";
                    }
                    int m = 0;
                    for (int i = 0; i < bitwise; i++) {

                        parameter_id = getParameterId(BitParamList.get(i));

                        for (int j = 0; j < BitvaluesList.get(i); j++) {
                            if (bitCheckedVal.get(m) != 0) {

                                psmt = null;
                                rst = null;
                                query2 = " insert into command_param_map(command_id,parameter_id,sub_division_selection_id)"
                                        + " values(?,?,?) ";
                                psmt = connection.prepareStatement(query2);
                                psmt.setString(1, command_id);
                                psmt.setInt(2, parameter_id);
                                psmt.setInt(3, bitCheckedVal.get(m));
                                rowsAffected = psmt.executeUpdate();
                            }
                            m++;
                        }

                    }
                }
                // END loop for Bitwise Type

                //}
            }
            // main loop end

            if (rowsAffected > 0) {
                connection.commit();
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }

        return rowsAffected;

    }

    public int getParameterId(String parameter) {
        int param_id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        try {
            String query = " select parameter_id from parameter where parameter_name='" + parameter + "' and active='Y' ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                param_id = rst.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.dataEntry.CommandModel.getParameterId() -" + e);
        }
        return param_id;

    }

    public int getLastId() throws SQLException {
        int id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        String query = " select max(id) from command where active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error...." + e);
        } finally {
            connection.close();
        }
        return id;

    }

    public boolean updateRecords(CommandBean bean) {
        boolean status = false;

        int updateRowsAffected = 0;

        int command_type_id = getCommandTypeId(bean.getCommand_type());
        int id = bean.getCommand_id();

        String query2 = " UPDATE command SET id=?,command=?,starting_del=?,end_del=?,active=?,remark=?,command_type_id=?,format=?,selection=?,input=?,bitwise=? WHERE id ='" + id + "'";

        try {
            PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query2);

            psmt.setInt(1, id);
            // psmt.setInt(2,device_id);
            psmt.setString(2, bean.getCommand());
            // psmt.setInt(4,operation_id);
            psmt.setString(3, bean.getStarting_del());
            psmt.setString(4, bean.getEnd_del());
            psmt.setString(5, "Y");
            psmt.setString(6, bean.getRemark());
            psmt.setInt(7, command_type_id);
//             psmt.setInt(7,rev);

            psmt.setString(8, bean.getFormat());
            psmt.setInt(9, bean.getSelection_no());
            psmt.setInt(10, bean.getInput_no());
            psmt.setInt(11, bean.getBitwise());

            int a = psmt.executeUpdate();
            if (a > 0) {
                status = true;
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

    public int getNoOfRows(String searchCommandName, String searchDeviceName, String searchManufacturerName, String searchDeviceType) {
//        String query = " select count(*) "
//                      +" from command c,device d "
//                      +" where c.device_id = d.id "
//                      + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
//                      //+ " and IF('" + searchCommandName + "' = '', device_name LIKE '%%',device_name =?) "
//                      +" and c.active='Y' ";
        String query1 = "select count(*)  from command c  where IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command ='')  and c.active='Y' ;";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<CommandBean> showData() {
        List<CommandBean> list = new ArrayList<CommandBean>();

        String query2 = "select c.id as command_id,c.remark,c.short_name, "
                + " c.starting_del,c.end_del,c.command,ct.name as command_type,c.format as format, c.input, c.selection, c.bitwise "
                + " from command c,command_type ct "
                + " where c.command_type_id = ct.id "
                + " and c.active='Y' and ct.active='Y' order by c.created_at desc ";

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CommandBean commandBean = new CommandBean();
                commandBean.setCommand_id(rset.getInt("command_id"));
                String remark = rset.getString("remark");
                commandBean.setRemark(remark);
                commandBean.setStarting_del(rset.getString("starting_del"));
                commandBean.setEnd_del(rset.getString("end_del"));

                commandBean.setCommand(rset.getString("command"));
                commandBean.setCommand_type(rset.getString("command_type"));
                commandBean.setFormat(rset.getString("format"));
                commandBean.setSelection_no(rset.getInt("selection"));
                commandBean.setInput_no(rset.getInt("input"));
                commandBean.setBitwise(rset.getInt("bitwise"));
                commandBean.setShort_name(rset.getString("short_name"));

                list.add(commandBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public static String bytesToHex(Byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public int deleteRecord(int command_id) {

//      String query = "update command set active='N' where id=" + command_id;
        String query = " delete command where id=" + command_id;
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

    public List<String> getDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from device_type group by type order by type desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
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
                list.add("No such Command Name exists.......");
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
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getDeviceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_name from model m where m.active='Y' group by device_name order by device_name desc";
        try {
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

    public List<String> getDeviceNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_no from model m where m.active='Y' group by device_no order by device_no desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
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
                list.add("No such Model Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name group by operation_name order by operation_name desc;";
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

    public List<String> getParameter(String q, String type) {
        List<String> list = new ArrayList<String>();
//        String query = "select parameter_name from parameter where parameter_type='" + type + "' "                
//                + " order by parameter_name desc ";

        String query = "select parameter_name from parameter where active='Y'  ";
        if (!type.equals("Common")) {
            query += " and parameter_type='" + type + "' ";
        }
        query += " order by parameter_name desc ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.

                String parameter_name = rset.getString("parameter_name");
                if (parameter_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(parameter_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Parameter Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSelectionValue(String q, String param) {
        List<String> list = new ArrayList<String>();
        String query = " select s.selection_value_no "
                + " from selection s,parameter p where p.parameter_id=s.parameter_id and s.active='Y' and p.active='Y' "
                + " and p.parameter_name='" + param + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.

                String val = rset.getString(1);
                if (val.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(val);
                    count++;
                }
            }
            if (count == 0) {
                list.add("Value not exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getBitwiseValue(String q, String param) {
        List<String> list = new ArrayList<String>();
        String query = " select sbd.parameter_name,sbd.start_pos,sbd.no_of_bit,sbd.sub_division_no "
                + " from parameter p,byte_data bd,sub_byte_division sbd where p.active='Y' and bd.active='Y' "
                + " and sbd.active='Y' and bd.parameter_id=p.parameter_id and p.parameter_name='" + param + "' "
                + " and sbd.byte_id=bd.byte_data_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.
                String val = rset.getString(1);
                //list.add(val);
                count++;
            }
            list.add(String.valueOf(count));
            if (count == 0) {
                list.add("Value not exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<CommandBean> getDisplayByteVal(String q, String sel_val, String param) {
        List<CommandBean> list = new ArrayList<CommandBean>();
        String query = " select sv.display_value,sv.byte_value,sv.selection_value_id "
                + " from selection s,parameter p, selection_value sv where p.parameter_id=s.parameter_id and s.active='Y' and p.active='Y' "
                + " and p.parameter_name='" + param + "' and sv.selection_id=s.selection_id "
                + " and s.selection_value_no='" + sel_val + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.
                CommandBean bean = new CommandBean();
                bean.setDisplay_val(rset.getString(1));
                bean.setByte_val(rset.getString(2));
                bean.setSelection_val_id(rset.getString(3));
                list.add(bean);
            }
            if (count == 0) {
                //list.add("No such Values exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<CommandBean> getSubByteDivVal(String q, String sub_byte_div_val, String param) {
        List<CommandBean> list = new ArrayList<CommandBean>();
        String query = " select sbd.parameter_name,sbd.start_pos,sbd.no_of_bit,sbd.sub_division_no,sbd.sub_byte_division_id "
                + " from parameter p,byte_data bd,sub_byte_division sbd where p.active='Y' and bd.active='Y' "
                + " and sbd.active='Y' and bd.parameter_id=p.parameter_id and p.parameter_name='" + param + "' "
                + " and sbd.byte_id=bd.byte_data_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.
                CommandBean bean = new CommandBean();
                bean.setSub_parameter_name(rset.getString(1));
                bean.setStart_pos(rset.getString(2));
                bean.setNo_of_bit(rset.getString(3));
                bean.setSub_div_no(rset.getString(4));
                bean.setSub_byte_div_id(rset.getString(5));
                list.add(bean);
            }
            if (count == 0) {
                //list.add("No such Values exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<CommandBean> getBitwiseDisByteVal(String q, String id) {
        List<CommandBean> list = new ArrayList<CommandBean>();
        String query = " select display_value,bit_value,sub_division_selection_id "
                + " from sub_division_selection where active='Y' and sub_byte_division_id='" + id + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.
                CommandBean bean = new CommandBean();
                bean.setDisplay_val(rset.getString(1));
                bean.setByte_val(rset.getString(2));
                bean.setSub_div_sel_id(rset.getString(3));
                list.add(bean);
            }
            if (count == 0) {
                //list.add("No such Values exists.......");
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
        String query = "select name from command_type ct group by name order by name desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
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
//
//   public List<String> getSearchCommandName(String q) {
//        List<String> list = new ArrayList<String>();
//        String query = " select command from command "
//                       +" where active='Y' group by command order by command desc ";
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

    public List<String> getSearchCommandName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select remark from command "
                + " where active='Y' group by remark order by remark desc ";
// String query = " select remark from command "
//                       +" where active='Y' group by remark order by remark desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("remark");
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
                + " from device d,command c,device_type dt "
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
                + " from device d,command c,model m "
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
