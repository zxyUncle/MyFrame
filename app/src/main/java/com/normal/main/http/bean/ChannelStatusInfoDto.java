package com.normal.main.http.bean;

import com.normal.zbase.http.bean.ABaseResponse;

import java.io.Serializable;
import java.util.List;

public class ChannelStatusInfoDto extends ABaseResponse implements Serializable {

    private Boolean success;
    private List<ChannelTaskListBean> channelTaskList;


    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ChannelTaskListBean> getChannelTaskList() {
        return channelTaskList;
    }

    public void setChannelTaskList(List<ChannelTaskListBean> channelTaskList) {
        this.channelTaskList = channelTaskList;
    }

    public static class ChannelTaskListBean implements Serializable {
        private String ext;
        private Integer intervalTime;
        private String nextExeTime;
        private String orderChannel;
        private String taskStatus;
        private String taskType;

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public Integer getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(Integer intervalTime) {
            this.intervalTime = intervalTime;
        }

        public String getNextExeTime() {
            return nextExeTime;
        }

        public void setNextExeTime(String nextExeTime) {
            this.nextExeTime = nextExeTime;
        }

        public String getOrderChannel() {
            return orderChannel;
        }

        public void setOrderChannel(String orderChannel) {
            this.orderChannel = orderChannel;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }
    }
}
