package com.nodey.ips.service;

import com.nodey.ips.util.Parser;
import com.nodey.ips.model.IP;
import com.nodey.ips.repository.IpsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IpsServiceImpl implements IpsService {

    private final IpsRepository ipsRepository;

    private final Parser parser;

    @Override
    public List<IP> getAllIps() {
        return ipsRepository.findAll();
    }

    @Override
    public boolean checkIP(String ipAddress) throws IOException {
        InetAddress address = InetAddress.getByName(ipAddress);
        if (address.isReachable(15000)) {
            return true;
        }
        return false;
    }

    @Override
    public IP getSuccessIP() throws IOException {
        List<IP> ipList = getAllIps();
        List<IP> successIPList = new ArrayList<>();

        for (IP ip : ipList){
            if (checkIP(ip.getIp())){
                successIPList.add(ip);
            }
            if (successIPList.size() == 0){
                parser.parseNewIP();
            }
        }
        return successIPList.get(0);
    }

    @Override
    public void clearDB() {
        ipsRepository.deleteAll();
    }

    @Override
    public void saveInDB(IP ip) {
        ipsRepository.save(ip);
    }

    @Override
    public void registerParseIPs() {
        clearDB();

        List<IP> ips = null;

        try {
            ips = this.parser.parseNewIP();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (IP ip : ips) {
            saveInDB(ip);
        }
    }
}