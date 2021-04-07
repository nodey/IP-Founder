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
    public List<IP> getSuccessIP() throws IOException {
        List<IP> ipList = getAllIps();
        List<IP> successIPList = new ArrayList<>();
        for (IP ip : ipList) {
            if (checkIP(ip.getIp())) {
                successIPList.add(ip);
                System.out.println(successIPList);
            }
        }
        return successIPList;
        //if (successIPList.size() == 0){
         //   registerParseIPs();
        //    getSuccessIP();
        //}
    //return successIPList.get(0);
    }

    @Override
    public IP getSucIP() throws IOException{
        List<IP> successfully = getSuccessIP();
        if (successfully.size() == 0){
            registerParseIPs();
            successfully = getSuccessIP();
        }
        return successfully.get(0);
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