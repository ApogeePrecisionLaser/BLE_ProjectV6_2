/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.util;

/**
 *
 * @author Shobha
 */
public class UniqueIDGenerator {

    private int uniqueID = 1;

    public int getUniqueID() {
        return uniqueID++;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

}
