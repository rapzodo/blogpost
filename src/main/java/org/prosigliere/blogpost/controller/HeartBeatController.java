package org.prosigliere.blogpost.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/heartbeat")
public class HeartBeatController {

    @GetMapping
    public String getHeartBeat() {
        return "OK";
    }
}
