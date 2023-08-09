package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/9/9.
 */
public class TypeModel extends BaseBean {

    public  static  final  String STATUS_YES="1";
    public  static  final  String STATUS_NO="0";



    private String code;
    private String name;
    private String capitalName;
    private String capitalLogo;
    private String caseId;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getCapitalLogo() {
        return capitalLogo;
    }

    public void setCapitalLogo(String capitalLogo) {
        this.capitalLogo = capitalLogo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @Override
    public String toString() {
        return "TypeModel{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", capitalName='" + capitalName + '\'' +
                ", capitalLogo='" + capitalLogo + '\'' +
                ", caseId='" + caseId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
