package com.nodey.ips.service;

import com.nodey.ips.job.Parser;
import com.nodey.ips.model.IP;
import com.nodey.ips.repository.IpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Service
public class IpsServiceImpl implements IpsService {

    @Autowired
    IpsRepository ipsRepository;

    @Autowired
    Parser parser;

    @Override
    public List<IP> getAllIps() {
        return ipsRepository.findAll();
    }

    @Override
    public boolean commands(String ipAddress)
            throws IOException {
        InetAddress geek = InetAddress.getByName(ipAddress);
        if (geek.isReachable(15000)) {
            return true;
        }
        return false;
    }

    @Override
    public String getAndCheckIP() throws IOException {
            List<IP> ipList = getAllIps();
            for (IP ip : ipList) {
                if (commands(ip.getIp())) {
                    return "Достуный IP адресс: " + ip.getIp() + ":" + ip.getPort();
                } else {
                    parser.parseNewIP();
                    continue;
                }
            }
            return "Нет доступных IP";
    }
}