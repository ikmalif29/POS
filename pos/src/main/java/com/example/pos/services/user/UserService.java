package com.example.pos.services.user;

import com.example.pos.dto.RequestLoginDto;
import com.example.pos.dto.ResponseLoginDto;

public interface UserService {
    ResponseLoginDto login(RequestLoginDto requestLoginDto);
}
