package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Comment extends BaseEntity {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("ComicId")
    private int comicId;

    @SerializedName("Content")
    private String content;

    @SerializedName("ParentCommentId")
    private Integer parentCommentId;

    @SerializedName("Comic")
    private Comic comic;

    @SerializedName("User")
    private User user;

    @SerializedName("ParentComment")
    private Comment parentComment;

    @SerializedName("Replies")
    private List<Comment> replies;

    public Comment() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
