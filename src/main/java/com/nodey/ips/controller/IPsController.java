package com.nodey.ips.controller;

import com.nodey.ips.model.IP;
import com.nodey.ips.service.IPsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/ip")
public class IPsController {

    private final IPsService ipsService;

    //Controller which is used Scheduled for parse IP's once in day
    @Scheduled(fixedRate = 8400000)
    @GetMapping("/parse")
    public void registerParseIPs() {

        ipsService.registerParseIPs();
    }

    @GetMapping(value = "/all")
    public List<IP> getAllIps() {

        return ipsService.getAllIps();
    }

    //Return 1 success IP
    @GetMapping("/get")
    public IP getIP() throws Exception{

        return ipsService.getSuccessIP();
    }


}
