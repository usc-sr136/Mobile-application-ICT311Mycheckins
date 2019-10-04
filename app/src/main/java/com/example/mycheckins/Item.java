package com.example.mycheckins;
public class Item {

    // private variables
    int _id;
    String _name;
    String _title = "";
    String _place = "";
    String _details = "";
    String _loc = "";
    String _date = "";
    byte[] _image = null;

    // Empty constructor
    public Item() {

    }

    // constructor
    public Item(int keyId, String name, String title, String place, String details, String date, String loc, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._image = image;
        this._title = title;
        this._place = place;
        this._details = details;
        this._date = date;
        this._loc = loc;
    }
    public Item(int keyId) {
        this._id = keyId;
    }

    // get Id
    public int getID() {
        return this._id;
    }

    // set Id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // get Name
    public String getName() {
        return this._name;
    }

    // set Name
    public void setName(String name) {
        this._name = name;
    }

    //get Title
    public String getTitle(){
        return this._title;
    }

    //set Title
    public void setTitle(String title){
        this._title = title;
    }

    //get Place
    public String getPlace(){
        return this._place;
    }

    //set Place
    public void setPlace(String place){
        this._place = place;
    }

    //get Details
    public String getDetails(){
        return this._details;
    }

    //set Details
    public void setDetails(String details){
        this._details = details;
    }

    //get Date
    public String getDate(){
        return this._date;
    }

    //set Date
    public void setDate(String date){
        this._date = date;
    }

    // get Location
    public String getLoc(){
        return this._loc;
    }

    //set Location
    public void setLoc(String loc){
        this._loc = loc;
    }

    // get Image
    public byte[] getImage() {
        return this._image;
    }

    // set Image
    public void setImage(byte[] image) {
        this._image = image;
    }
}