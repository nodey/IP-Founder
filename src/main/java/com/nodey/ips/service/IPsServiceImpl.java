package com.nodey.ips.service;

import com.nodey.ips.util.Parser;
import com.nodey.ips.model.IP;
import com.nodey.ips.repository.IPsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IPsServiceImpl implements IPsService {

    private final IPsRepository ipsRepository;

    private final Parser parser;

    @Override
    public List<IP> getAllIps() {

        return ipsRepository.findAll();
    }

    @Override
    public boolean checkIP(String ipAddress) throws IOException {

        InetAddress address = InetAddress.getByName(ipAddress);

        return address.isReachable(5000);
    }

    @Override
    public List<IP> getCheckingIPList() throws IOException {

        List<IP> ipList = getAllIps();
        List<IP> checkingIPList = new ArrayList<>();

        for (IP ip : ipList) {
            if (checkIP(ip.getIp())) {
                checkingIPList.add(ip);
                System.out.println(checkingIPList);
            }
        }
        return checkingIPList;
    }

    @Override
    public IP getSuccessIP() throws IOException{

        List<IP> successfullyIPListAfterCheckingForValid = getCheckingIPList();

        if (successfullyIPListAfterCheckingForValid.size() == 0){
            registerParseIPs();
            successfullyIPListAfterCheckingForValid = getCheckingIPList();
        }
        return successfullyIPListAfterCheckingForValid.get(0);
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

        assert ips != null;
        for (IP ip : ips) {
            saveInDB(ip);
        }
    }
}