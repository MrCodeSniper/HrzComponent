package com.mujirenben.android.mine.mvp.model.bean;

public class AppUpgradeInfo {

    /**
     * app名称
     */
    private String name;

    /**
     * app版本相关
     */
    private int versionCode;
    private String versionName;

    /**
     * 升级提示框的标题
     */
    private String upgradeTitle;

    /**
     * 升级提示
     */
    private String upgradeNotes;

    private float apkSize;
    private String downloadUri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpgradeTitle() {
        return upgradeTitle;
    }

    public void setUpgradeTitle(String upgradeTitle) {
        this.upgradeTitle = upgradeTitle;
    }

    public String getUpgradeNotes() {
        return upgradeNotes;
    }

    public void setUpgradeNotes(String upgradeNotes) {
        this.upgradeNotes = upgradeNotes;
    }

    public float getApkSize() {
        return apkSize;
    }

    public void setApkSize(float apkSize) {
        this.apkSize = apkSize;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    @Override
    public String toString() {
        return "AppUpgradeInfo{" +
                "name='" + name + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", upgradeTitle='" + upgradeTitle + '\'' +
                ", upgradeNotes='" + upgradeNotes + '\'' +
                ", apkSize=" + apkSize +
                ", downloadUri='" + downloadUri + '\'' +
                '}';
    }
}
