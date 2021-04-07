package com.nodey.ips.service;

import com.nodey.ips.model.IP;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

//Service with methods for interaction with database and parser
@Service
public interface IPsService {

    //Return List ip from database
    List<IP> getAllIps();

    //Checking IP for success ip in the internet
    boolean checkIP(String ipAddress) throws  IOException;

    //Return List with valid IP's
    List<IP> getCheckedIPList() throws IOException;

    //Return 1 valid IP for proxy settings
    IP getSuccessIP() throws IOException;

    //Clear database
    void clearDB();

    //Put new data in database
    void saveInDB(IP ip);

    //Parse IP's of the selected internet resource and HTML tag of table
    void registerParseIPs();
}
