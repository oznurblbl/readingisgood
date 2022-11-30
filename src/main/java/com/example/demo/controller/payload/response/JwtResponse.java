package com.example.demo.controller.payload.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -3456129853690234589L;

    private final String jwtToken;

}
