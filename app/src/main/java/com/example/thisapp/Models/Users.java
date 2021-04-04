package com.example.thisapp.Models;

public class Users {
    String uProfilePic,uName,uEmail,uPassword,uId,uLastMessage;

    public Users(String uProfilePic, String uName, String uEmail, String uPassword, String uId, String uLastMessage) {
        this.uProfilePic = uProfilePic;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uId = uId;
        this.uLastMessage = uLastMessage;
    }

    public Users(String uName, String uEmail, String uPassword) {
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
    }
    public Users(){
    }

    public String getuProfilePic() {
        return uProfilePic;
    }

    public void setuProfilePic(String uProfilePic) {
        this.uProfilePic = uProfilePic;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuLastMessage() {
        return uLastMessage;
    }

    public void setuLastMessage(String uLastMessage) {
        this.uLastMessage = uLastMessage;
    }
}
