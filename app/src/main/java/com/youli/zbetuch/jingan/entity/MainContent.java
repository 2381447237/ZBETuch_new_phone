package com.youli.zbetuch.jingan.entity;

import java.util.List;

/**
 * Created by liutao on 2017/8/2.
 */

public class MainContent {

    private String title;

    private List<JobsInfo> jobsInfos;//岗位信息

    private List<NewsInfo> newsInfos;//近期热点

    private List<MeetInfo> meetInfos;//会议通知

    private List<WorkNoticeInfo> workNoticeInfos;//工作通知

    public List<JobsInfo> getJobsInfos() {
        return jobsInfos;
    }

    public void setJobsInfos(List<JobsInfo> jobsInfos) {
        this.jobsInfos = jobsInfos;
    }

    public List<WorkNoticeInfo> getWorkNoticeInfos() {
        return workNoticeInfos;
    }

    public void setWorkNoticeInfos(List<WorkNoticeInfo> workNoticeInfos) {
        this.workNoticeInfos = workNoticeInfos;
    }

    public List<MeetInfo> getMeetInfos() {
        return meetInfos;
    }

    public void setMeetInfos(List<MeetInfo> meetInfos) {
        this.meetInfos = meetInfos;
    }

    public List<NewsInfo> getNewsInfos() {
        return newsInfos;
    }

    public void setNewsInfos(List<NewsInfo> newsInfos) {
        this.newsInfos = newsInfos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MainContent(String title, List<MeetInfo> meetInfos,  List<WorkNoticeInfo> workNoticeInfos,List<JobsInfo> jobsInfos, List<NewsInfo> newsInfos) {
        this.jobsInfos = jobsInfos;
        this.meetInfos = meetInfos;
        this.newsInfos = newsInfos;
        this.title = title;
        this.workNoticeInfos = workNoticeInfos;
    }

    @Override
    public String toString() {
        return "MainContent{" +
                "jobsInfos=" + jobsInfos +
                ", title='" + title + '\'' +
                ", newsInfos=" + newsInfos +
                ", meetInfos=" + meetInfos +
                ", workNoticeInfos=" + workNoticeInfos +
                '}';
    }
}
