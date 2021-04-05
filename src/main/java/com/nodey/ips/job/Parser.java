package com.nodey.ips.job;

import com.nodey.ips.model.IP;
import com.nodey.ips.repository.IpsRepository;
import com.nodey.ips.service.IpsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Parser {

    @Autowired
    IpsService ipsService;

    @Autowired
    IpsRepository repository;

    @Scheduled(fixedRate = 8400000)
    public void parseNewIP(){
        String url = "https://free-proxy-list.net/";

        try {
            repository.deleteAll();
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table").get(0);
            for (Element row : table.select("tr:lt(11):gt(0)")){
                    Elements ips = row.select("td:eq(0)");
                    Elements ports = row.select("td:eq(1)");
                    String ip = ips.text();
                    String port = ports.text();
                    IP obj = new IP();
                    obj.setIp(ip);
                    obj.setPort(port);
                    repository.save(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
