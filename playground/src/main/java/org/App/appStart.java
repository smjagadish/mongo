package org.App;

import com.mongodb.client.MongoClient;
import org.DataCreator.legacyRadioProcessor;
import org.DataImpl.newRadio;
import org.DataImpl.nrCategory;
import org.DataImpl.radioEss;
import org.DataImpl.radioSuMimo;
import org.DataModifier.radioUpdater;
import org.DataCreator.newRadioProcessor;
import org.DataPurger.radioPhaseOut;
import org.DataRepo.Client;
import org.DataRetriever.legacySites;
import org.DataRetriever.trialSites;

import java.util.Arrays;

public class appStart {
    public static void main(String[] args) {

        MongoClient client = Client.getClientHelper().getClient();
        newRadioProcessor npr = new newRadioProcessor(client);
        npr.createCollection();
        npr.addRadio("BLM",100,60, nrCategory.highband);
        npr.addRadioPojo("Omaha",60,20,nrCategory.midband);
        npr.addMultipleRadios(Arrays.asList(new newRadio("DesMoines",20,10,nrCategory.lowband)
                ,new newRadio("DesMoines",60,20,nrCategory.midband)
                ,new newRadio("Omaha",100,60,nrCategory.highband)
                ,new newRadio("Omaha",20,10,nrCategory.lowband)));
        // client.close();
        trialSites ts = new trialSites(client);
        ts.fetchFirstDoc();
        ts.fetchFirstDocAsPojo();
        ts.fetchInfoAsPojo();
        ts.fetchWithCondition("Omaha");
        radioUpdater updater = new radioUpdater(client);
        updater.change_MaxBw_Single("660c40e5d64ca11ace7e2bfc",40);
        updater.change_MinBw_Multi(15);
        ts.getDocumentCount();
        ts.getSiteDocumentCount("BLM");
        radioPhaseOut rp = new radioPhaseOut(client);
        rp.decom_by_id("660c78ba144b0e280e57d4e1");
        legacyRadioProcessor lpr = new legacyRadioProcessor(client);
        lpr.createCollection();
        lpr.createSite("100","Roy",4,10, radioEss.builder().mode("ess").min_bw(5).max_bw(25).threshold(35.0).build());
        lpr.createSite("200","Merry",5,10, radioSuMimo.builder().mode("su-mimo").min_bw(10).max_bw(60).threshold(12.5).build());
        legacySites ls = new legacySites(client);
        ls.fetchAllLegacySites();
        System.out.println("Application terminated");

    }
}