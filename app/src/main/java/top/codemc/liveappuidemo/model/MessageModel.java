package top.codemc.liveappuidemo.model;

/**
 * Created by xiyoumc on 16/8/8.
 */
public class MessageModel {

    public String userName;
    public String content;

    public MessageModel(Builder builder) {
        this.userName = builder.userName;
        this.content = builder.content;
    }

    public static class Builder {
        private String userName;
        private String content;

        public Builder userName(String username) {
            this.userName = username;
            return this;
        }

        public Builder content(String c) {
            this.content = c;
            return this;
        }

        public MessageModel build() {
            return new MessageModel(this);
        }
    }
}
