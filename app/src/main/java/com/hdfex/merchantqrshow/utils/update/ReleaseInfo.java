package com.hdfex.merchantqrshow.utils.update;

import java.io.Serializable;

/**
 * Created by longtaoge on 2016/4/25.
 */
public class ReleaseInfo implements Serializable{


    /**
     * code : 0
     * message : request success!
     * result : {"isForce":1,"releaseNote":"Improvement:1. Add more transmit power options.  2. Remove option to upgrade of firmware.","releaseNoteCN":"改进:1. 添加更多发射功率。2. 删除固件升级功能。","url":"http://123.56.233.192/app-hdrelease-debug.apk","versionCode":18}
     */

    private int code;
    private String message;
    /**
     * isForce : 1
     * releaseNote : Improvement:1. Add more transmit power options.  2. Remove option to upgrade of firmware.
     * releaseNoteCN : 改进:1. 添加更多发射功率。2. 删除固件升级功能。
     * url : http://123.56.233.192/app-hdrelease-debug.apk
     * versionCode : 18
     */

    private ResultEntity result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity implements  Serializable {
        private int isForce;
        private String releaseNote;
        private String releaseNoteCN;
        private String url;
        private int versionCode;

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public String getReleaseNote() {
            return releaseNote;
        }

        public void setReleaseNote(String releaseNote) {
            this.releaseNote = releaseNote;
        }

        public String getReleaseNoteCN() {
            return releaseNoteCN;
        }

        public void setReleaseNoteCN(String releaseNoteCN) {
            this.releaseNoteCN = releaseNoteCN;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "isForce=" + isForce +
                    ", releaseNote='" + releaseNote + '\'' +
                    ", releaseNoteCN='" + releaseNoteCN + '\'' +
                    ", url='" + url + '\'' +
                    ", versionCode=" + versionCode +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReleaseInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result.toString() +
                '}';
    }
}
