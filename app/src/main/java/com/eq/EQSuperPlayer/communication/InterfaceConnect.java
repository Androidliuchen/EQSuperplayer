package com.eq.EQSuperPlayer.communication;

/**
 * Created by Administrator on 2017/1/21.
 */
public interface InterfaceConnect {
    void  success(byte[] result);
    void  failure(int  stateCode);
    void dataSuccess(String str);
}
