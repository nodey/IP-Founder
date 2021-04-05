package com.nodey.ips.service;

import com.nodey.ips.model.IP;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IpsService {
    List<IP> getAllIps();
    boolean commands(String ipAddress) throws  IOException;
    String getAndCheckIP() throws IOException;
}
