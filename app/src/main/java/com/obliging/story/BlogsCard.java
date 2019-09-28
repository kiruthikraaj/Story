package com.obliging.story;



public class BlogsCard {

    private String blog;
    private String UserName;
    private String title;
    private String displayPicture;

    public BlogsCard() {

    }
    public BlogsCard(String  userdp, String username, String blogtitle, String blog) {

        this.UserName = username;
        this.title = blogtitle;
        this.displayPicture = userdp;
        this.blog = blog;
    }

    public String getDisplayPicture(){ return displayPicture; }
    public void setDisplayPicture(String userdp){ this.displayPicture = userdp;}

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String blogtitle) {
        this.title = blogtitle;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String username) {
        this.UserName = username;
    }
}
