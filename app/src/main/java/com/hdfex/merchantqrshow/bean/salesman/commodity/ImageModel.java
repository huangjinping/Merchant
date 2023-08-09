package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.io.File;

/**
 * Created by harrishuang on 16/9/6.
 */

public class ImageModel extends BaseBean implements Cloneable {


    private String path;
    private String picId;
    private int defaultid;
    private boolean isLoaded;
    private int row;
    private int position;
    private File imageFile;
    private String name;
    private String relativePath;
    private String absolutePath;
    private String type;
    private String title;
    private boolean isShowProgress;
    public ImageModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageModel(int position, int row, String name) {
        this.row = row;
        this.position = position;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public int getDefaultid() {
        return defaultid;
    }

    public void setDefaultid(int defaultid) {
        this.defaultid = defaultid;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isShowProgress() {
        return isShowProgress;
    }

    public void setShowProgress(boolean showProgress) {
        isShowProgress = showProgress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Object clone() {
        ImageModel newModel = null;
        try {
            newModel = (ImageModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return newModel;
    }
}
