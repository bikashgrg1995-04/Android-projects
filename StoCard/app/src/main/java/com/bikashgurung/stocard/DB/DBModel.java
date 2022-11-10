package com.bikashgurung.stocard.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DBModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "card_no")
    public String cardNo;

    @ColumnInfo(name = "card_name")
    public String cardName;

    @ColumnInfo(name = "card_image")
    public String cardImage;

    @ColumnInfo(name = "card_lat")
    public String cardLat;

    @ColumnInfo(name = "card_long")
    public String cardLong;


    public DBModel(int uid, String cardNo, String cardName, String cardImage) {
        this.uid = uid;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.cardImage = cardImage;
    }

    @Ignore
    public DBModel() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }
}
