package com.metalsack.retrobus.network.response.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android2 on 21/4/17.
 */

public class ExpertInfo implements Parcelable {

    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("chat")
    @Expose
    private String chat;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("quickblox_id")
    @Expose
    private String quickbloxId;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mob_no")
    @Expose
    private String mobNo;

    private int unreadMsgCount;

    protected ExpertInfo(Parcel in) {
        audio = in.readString();
        video = in.readString();
        chat = in.readString();
        message = in.readString();
        quickbloxId = in.readString();
        ownerId = in.readString();
        login = in.readString();
        email = in.readString();
        userId = in.readString();
        fullName = in.readString();
        profilePic = in.readString();
        age = in.readString();
        gender = in.readString();
        city = in.readString();
        state = in.readString();
        mobNo = in.readString();
        unreadMsgCount = in.readInt();
    }

    public static final Creator<ExpertInfo> CREATOR = new Creator<ExpertInfo>() {
        @Override
        public ExpertInfo createFromParcel(Parcel in) {
            return new ExpertInfo(in);
        }

        @Override
        public ExpertInfo[] newArray(int size) {
            return new ExpertInfo[size];
        }
    };

    public String getQuickbloxId() {
        return quickbloxId;
    }

    public void setQuickbloxId(String quickbloxId) {
        this.quickbloxId = quickbloxId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public ExpertInfo() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(audio);
        dest.writeString(video);
        dest.writeString(chat);
        dest.writeString(message);
        dest.writeString(quickbloxId);
        dest.writeString(ownerId);
        dest.writeString(login);
        dest.writeString(email);
        dest.writeString(userId);
        dest.writeString(fullName);
        dest.writeString(profilePic);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(mobNo);
        dest.writeInt(unreadMsgCount);
    }
}
