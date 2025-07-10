package com.example.pos.services;

import com.example.pos.dto.RequestLoginDto;
import com.example.pos.dto.RequestRegistDto;
import com.example.pos.dto.ResponseLoginDto;

public interface UserService {
    ResponseLoginDto login(RequestLoginDto requestLoginDto);
    String register(RequestRegistDto requestRegistDto);
}
