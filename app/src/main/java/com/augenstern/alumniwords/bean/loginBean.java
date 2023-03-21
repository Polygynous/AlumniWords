package com.augenstern.alumniwords.bean;

public class loginBean
{
    /**
     * code : 0
     * data : {"id":416755,"userAccount":"117.28.133.62","userRole":"anonymous","userPassword":"b0dd3697a192885d7c055db46155b26a","createTime":"2023-03-09T02:16:54.000+00:00","updateTime":"2023-03-09T02:16:54.000+00:00","isDelete":0}
     * message : ok
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 416755
         * userAccount : 117.28.133.62
         * userRole : anonymous
         * userPassword : b0dd3697a192885d7c055db46155b26a
         * createTime : 2023-03-09T02:16:54.000+00:00
         * updateTime : 2023-03-09T02:16:54.000+00:00
         * isDelete : 0
         */

        private int id;
        private String userAccount;
        private String userRole;
        private String userPassword;
        private String createTime;
        private String updateTime;
        private int isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }
    }
}
