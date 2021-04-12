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
        //Class and method for check valid IP
        InetAddress address = InetAddress.getByName(ipAddress);
        //return true if address is valid
        return address.isReachable(5000);
    }

    @Override
    public List<IP> getCheckedIPList() throws IOException {

        List<IP> ipList = getAllIps();
        List<IP> checkedIPList = new ArrayList<>();
        //Bypassing the list of IP's and checking them for validity
        for (IP ip : ipList) {
            if (checkIP(ip.getIp())) {
                checkedIPList.add(ip);
            }
        }
        return checkedIPList;
    }

    @Override
    public IP getSuccessIP() throws IOException{

        List<IP> successfullyIPListAfterCheckedForValid = getCheckedIPList();
        //Checking for some one valid IP, if list = null return to checkIP method
        if (successfullyIPListAfterCheckedForValid.size() == 0){
            registerParseIPs();
            successfullyIPListAfterCheckedForValid = getCheckedIPList();
        }
        return successfullyIPListAfterCheckedForValid.get(0);
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
        //Save IP's in database using forEach
        assert ips != null;
        for (IP ip : ips) {
            saveInDB(ip);
        }
    }
}
