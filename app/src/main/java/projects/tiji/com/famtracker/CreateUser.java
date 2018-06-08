package projects.tiji.com.famtracker;

/**
 * Created by Tg on 10-05-2018.
 */

public class CreateUser {
    public String name;
    public String email;
    public String password;
    public String code;
    public String isSharing;
    public Double lat;
    public Double lng;
    public String imageUri;

    public CreateUser(String name, String email, String password, String code, String userid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String userid;

    public CreateUser() {
    }

    {


    }

    public CreateUser(String name, String email, String password, String code, String isSharing, Double lng, Double lat, String imageUri,String userid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.isSharing = isSharing;
        this.lng = lng;
        this.lat = lat;
        this.imageUri = imageUri;
        this.userid=userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getIsSharing() {
        return isSharing;
    }

    public void setIsSharing(String isSharing) {
        this.isSharing = isSharing;
    }
}
