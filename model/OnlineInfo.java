package vn.edu.topica.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ZtOg on 4/19/2017.
 */

public class OnlineInfo {
    ImageView avatarOnline;
    String name;
    Bitmap image;

    public OnlineInfo() {
    }

    public OnlineInfo(ImageView avatarOnline, String name, Bitmap image) {
        this.avatarOnline = avatarOnline;
        this.name = name;
        this.image = image;
    }

    public ImageView getAvatarOnline() {
        return avatarOnline;
    }

    public void setAvatarOnline(ImageView avatarOnline) {
        this.avatarOnline = avatarOnline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
