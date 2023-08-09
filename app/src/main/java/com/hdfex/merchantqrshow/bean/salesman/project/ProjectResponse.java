package com.hdfex.merchantqrshow.bean.salesman.project;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.order.Project;

import java.util.List;

/**
 * Created by harrishuang on 16/7/11.
 */
public class ProjectResponse  extends Response{


    private List<Project> result;

    public List<Project> getResult() {
        return result;
    }

    public void setResult(List<Project> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProjectResponse{" +
                "result=" + result +
                '}';
    }
}
