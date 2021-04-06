package com.nodey.ips.controller;

import com.nodey.ips.model.IP;
import com.nodey.ips.service.IpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/ip")
public class IpsController {

    private final IpsService ipsService;

    @GetMapping(value = "/all")
    public List<IP> getAllIps() {
        return ipsService.getAllIps();
    }

    @GetMapping(value = "/get")
    public IP getIP() throws Exception {
        return ipsService.getSuccessIP();
    }

    @Scheduled(fixedRate = 8400000)
    @GetMapping("/parse")
    public void registerParseIPs() {
        ipsService.registerParseIPs();
    }
}
