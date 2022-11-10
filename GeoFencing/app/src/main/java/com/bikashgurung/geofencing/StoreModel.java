package com.bikashgurung.geofencing;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StoreModel {

    @PrimaryKey(autoGenerate = true)
    public int storeID;

    @ColumnInfo(name = "bankName")
    public String bank_name;

    @ColumnInfo(name = "branchName")
    public String branch_name;

    @ColumnInfo(name = "longitute")
    public String store_longitute;

    @ColumnInfo(name = "latitude")
    public String store_latitude;

    @ColumnInfo(name = "status")
    public String store_status;

    public StoreModel(int _tmpStoreID, String _tmpBank_name, String _tmpBranch_name, String _tmpStore_longitute, String _tmpStore_latitude, String _tmpStore_status) {
    }

    public StoreModel(String bank_name, String branch_name, String store_longitute, String store_latitude, String store_status) {
        this.storeID = storeID;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
        this.store_longitute = store_longitute;
        this.store_latitude = store_latitude;
        this.store_status = store_status;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getStore_longitute() {
        return store_longitute;
    }

    public void setStore_longitute(String store_longitute) {
        this.store_longitute = store_longitute;
    }

    public String getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(String store_latitude) {
        this.store_latitude = store_latitude;
    }

    public String getStore_status() {
        return store_status;
    }

    public void setStore_status(String store_status) {
        this.store_status = store_status;
    }
}
