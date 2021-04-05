package com.nodey.ips.controller;

import com.nodey.ips.job.Parser;
import com.nodey.ips.model.IP;
import com.nodey.ips.service.IpsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IpsController {

    final
    IpsService ipsService;

    public IpsController(IpsService ipsService, Parser parser) {
        this.ipsService = ipsService;
    }

    @GetMapping(value = "/ips")
    public List<IP> getAllIps() {
        return ipsService.getAllIps();
    }

    @GetMapping(value = "/get")
    public String getIP() throws Exception {
        return ipsService.getAndCheckIP();
    }
}
