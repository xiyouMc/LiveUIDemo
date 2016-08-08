package top.codemc.liveappuidemo.model;

import android.graphics.Bitmap;

/**
 * Created by xiyoumc on 16/8/6.
 */
public class UsersContainerModel {

    public Bitmap userPortrait;
    public Bitmap userType;

    public UsersContainerModel(Builder builder) {
        this.userPortrait = builder.userPortrait;
        this.userType = builder.userType;
    }

    public static class Builder {

        private Bitmap userPortrait;
        private Bitmap userType;

        public Builder userProtrait(Bitmap userPortrait) {
            this.userPortrait = userPortrait;
            return this;
        }

        public Builder userType(Bitmap userType) {
            this.userType = userType;
            return this;
        }

        public UsersContainerModel build() {
            return new UsersContainerModel(this);
        }
    }
}
