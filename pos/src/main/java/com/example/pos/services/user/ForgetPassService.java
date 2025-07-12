package com.example.pos.services.user;

import com.example.pos.dto.RequestForgetPassDto;
import com.example.pos.dto.RequestResetPassDto;

public interface ForgetPassService{
    void sendEmailForgetPass(RequestForgetPassDto requestForgetPassDto);
        void resetPass(RequestResetPassDto requestResetPassDto);
}
