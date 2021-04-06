package com.nodey.ips.util;

import com.nodey.ips.model.IP;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Parser {

    public List<IP> parseNewIP() throws FileNotFoundException {
        List<IP> ipResultList = new ArrayList<>();

        InputStream inputStream = new FileInputStream("src/main/resources/config.yml");
        Yaml yaml = new Yaml();
        Map<String, String> data = yaml.load(inputStream);

        String url = data.get("url");

        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select(data.get("table")).get(0);
            for (Element row : table.select("tr:lt(11):gt(0)")){
                    Elements ips = row.select("td:eq(0)");
                    Elements ports = row.select("td:eq(1)");
                    String ip = ips.text();
                    String port = ports.text();
                    IP obj = new IP();
                    obj.setIp(ip);
                    obj.setPort(port);

                    ipResultList.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipResultList;
    }
}
