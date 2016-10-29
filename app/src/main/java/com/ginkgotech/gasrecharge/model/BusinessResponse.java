package com.ginkgotech.gasrecharge.model;

/**
 * Created by Administrator on 2016/10/29.
 */

public interface BusinessResponse {

    public void OnWriteCompleted(Exception ex);

    public void OnDataAvailable(String recv);

    public void OnCloseCompleted(Exception ex);

    public void OnEndCompleted(Exception ex);
}
