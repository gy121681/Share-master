package com.share.app.entity.request;

import com.share.app.entity.BaseRequest;

/**
 * Created by Snow on 2017/7/26.
 */

public class LoginPwdUpdateRequest extends BaseRequest {

    public String userId;
    public String oldPassword;
    public String newPassword;

}
