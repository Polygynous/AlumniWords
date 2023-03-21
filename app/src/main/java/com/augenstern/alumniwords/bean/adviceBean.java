package com.augenstern.alumniwords.bean;

public class adviceBean {
    /**
     * content : 首先要明白自己自身专业的优势在哪里，我们读的这个专业是叫做计算机科学与技术（师范）也就是说我们就从专业来选择可以走两条不同的路（计算机领域或者是计算机教师这种）无论如何都必须有个明确的目标，例如我是选择以后当老师的，因为想着比较稳定。那我们当老师的就必须专研还有学习好有一切关于教师的知识研究，当然同时也不可以落下最基础的信息技术专业知识（不用像走计算机行业那种死磕）。还有就是入职或者是当成了老师，在教师行业中就必须看绩效还有职称（我们就提升这个才有更加高的工资），在近些年最火的就是中小学的人工智能比赛，机器人比赛还有程序设计比赛（很多时候我们带比赛获奖才可以提高自身的这种职称），我们在大学的时候就可以去多多研究这些方面对以后有很大的帮助。记得我们年轻人不要怕吃苦！
     * createTime : 2023-02-15T07:10:33.000+00:00
     * education : 本科生
     * hasThumb : false
     * id : 7655
     * isDelete : 0
     * major : 计算机科学与技术
     * research : 师范
     * reviewStatus : 1
     * school : 韩山师范学院
     * thumbNum : 5
     * updateTime : 2023-03-01T02:24:56.000+00:00
     * userId : 388379
     * viewNum : 0
     * workExp : 无，高年级在读学生
     */

    private String content;
    private String createTime;
    private String education;
    private boolean hasThumb;
    private int id;
    private int isDelete;
    private String major;
    private String research;
    private int reviewStatus;
    private String school;
    private int thumbNum;
    private String updateTime;
    private int userId;
    private int viewNum;
    private String workExp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isHasThumb() {
        return hasThumb;
    }

    public void setHasThumb(boolean hasThumb) {
        this.hasThumb = hasThumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(int thumbNum) {
        this.thumbNum = thumbNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getWorkExp() {
        return workExp;
    }

    public void setWorkExp(String workExp) {
        this.workExp = workExp;
    }
}
