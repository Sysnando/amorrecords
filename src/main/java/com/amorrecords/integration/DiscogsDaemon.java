package com.amorrecords.integration;

import com.amorrecords.integration.discogs.client.DiscogsClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class DiscogsDaemon {

    //@Scheduled(cron = "* * * * * ?")
    private void identify(){
        System.out.println("Entrou no metodo");
        DiscogsClient discogsClient = new DiscogsClient();
        String result = discogsClient.identity();
        System.out.println(result);
    }

}



