package com.bikashgurung.stocard.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DestinationModel {

    @PrimaryKey(autoGenerate = true)
    public int idDestination;

    @ColumnInfo(name = "bankName")
    public String bankName;

    @ColumnInfo(name = "branchName")
    public String branchName;

    @ColumnInfo(name = "destination_full_address")
    public String full_address;

    public DestinationModel() {
    }

    public DestinationModel(String bankName, String branchName, String full_address) {
        this.idDestination = idDestination;
        this.bankName = bankName;
        this.branchName = branchName;
        this.full_address = full_address;
    }

    public int getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(int idDestination) {
        this.idDestination = idDestination;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
