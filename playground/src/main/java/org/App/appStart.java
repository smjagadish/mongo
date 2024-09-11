package org.App;

import com.mongodb.client.MongoClient;
import org.DataAggregator.*;
import org.DataCreator.*;
import org.DataImpl.*;
import org.DataModifier.locationUpdator;
import org.DataModifier.parksUpdater;
import org.DataModifier.radioUpdater;
import org.DataModifier.sampleCollUpdater;
import org.DataPurger.radioPhaseOut;
import org.DatabaseClient.Client;
import org.DataRetriever.*;
import org.DataStreamer.streamWatcher;
import org.DataUtils.dataSort;
import org.IndexCreator.createIndices;
import org.bson.BsonBinarySubType;
import org.bson.BsonDateTime;
import org.bson.types.Binary;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class appStart {
    public static void main(String[] args) {

        MongoClient client = Client.getClientHelper().getClient();
        doNprJob(client);
        // client.close();
        updateRadio(client);
        purgeRadio(client);
        doTrialSitesJob(client);
        doLegacyRadioJob(client);
        searchLegacySite(client);
        invokeSimpleAgg(client);
        streamWatcher sw = new streamWatcher(client);
        // sw.openStream(); // supported only on replicaset deploys , so commenting for now.
        doDatasort(client);
        addStoreLocationRecords(client);
        atomicUpdateStoreType(client);
        searchStoreLocationRecords(client);
        doParksJob(client);
        dolookupAggregationJob(client);
        doGroupAggregationJob(client);
        doOutAggregationJob(client);
        doMergeAggregationJob(client);
        doUnwindJob(client);
        doGraphLookupJob(client);
        doUserTrackerJob(client);
        doCreateIndex(client);
        doSampleCollectionJob(client);
        doArrayOpsCollectionJob(client);
        System.out.println("Application terminated");

    }

    private static void doArrayOpsCollectionJob(MongoClient client) {
        arrayOpsCollCreator creator = new arrayOpsCollCreator(client);
        arrayOpsColl.arrayOpsElement element = arrayOpsColl.arrayOpsElement.builder()
                .stringArray(List.of("asd","ghjk","lko"))
                .dateTimeField( new BsonDateTime(Instant.now().toEpochMilli()))
                .integerField(25)
                .build();
        arrayOpsColl coll_obj = arrayOpsColl.builder().elements(List.of(element))
                .build();
        // check this - the final doc being added has empty value for the 'arrayField' array element.
        creator.createCollectionElement(coll_obj);

    }

    private static void doSampleCollectionJob(MongoClient client) {
        sampleRetriever sampleRetriever = new sampleRetriever(client);
        sampleCollCreator creator = new sampleCollCreator(client);
        sampleCollUpdater updater = new sampleCollUpdater(client);
        creator.addRecord(sampleColl.builder()
                .age(14)
                .occupation("designer")
                .name("Jonah Scott")
                .createdAt(new BsonDateTime(Instant.now().toEpochMilli()))
                .build());
        updater.updateCreationDate("Writer");
        sampleRetriever.listRecords();
    }

    private static void doCreateIndex(MongoClient client) {
        createIndices idxCreator = new createIndices (client);
        idxCreator.addIndex();
        idxCreator.addTextIndex();
        idxCreator.showIndex();
    }

    private static void doUserTrackerJob(MongoClient client) {
        userTracker[] users = new userTracker[3];
        userTrackerCreator creator = new userTrackerCreator(client);
        for (int idx=0;idx<3;idx++)
        {
            byte[] bin_data = new byte[]{0b1000};
            userTracker user = userTracker.builder()
                    .active(true)
                    .username("user"+":"+idx)
                    .permissions(new Binary(BsonBinarySubType.BINARY, bin_data)).build();
            users[idx] = user;
        }
        creator.insertDoc(users);
        userTrackerGet uget = new userTrackerGet(client);
        uget.getRecords();
    }

    private static void doGraphLookupJob(MongoClient client) {
        graphLookupAggregator gAgg = new graphLookupAggregator(client);
        gAgg.doGraphLookup();
    }

    private static void doUnwindJob(MongoClient client) {
        unwindAggregator uAgg = new unwindAggregator(client);
        uAgg.doUnwind();
    }

    private static void doMergeAggregationJob(MongoClient client) {
        mergeAggregator magg = new mergeAggregator(client);
        magg.doMerge("stores_repl");
    }

    private static void doOutAggregationJob(MongoClient client) {
        outAggregator oagg = new outAggregator(client);
        oagg.doAggregateToDiffCollection("dup_stores");
    }

    private static void doGroupAggregationJob(MongoClient client) {
        groupAggregator gr = new groupAggregator(client);
        gr.groupByBand("highband");
        gr.groupByMaxn("BLM");
        gr.groupByTopN("Omaha");
        gr.simpleGrouping("highband");
    }

    private static void dolookupAggregationJob(MongoClient client) {
        lookupAggCreator cr = new lookupAggCreator(client);
        List<orders> ordersList = List.of(orders.builder().id(100).cust_id("cust-001").ord_num("ord-001").build(),
                orders.builder().id(101).cust_id("cust-002").ord_num("ord-002").build(),
                orders.builder().id(102).cust_id("cust-003").ord_num("ord-003").build(),
                orders.builder().id(103).cust_id("cust-004").ord_num("ord-004").build());
        cr.createOrders(ordersList);

        List<customers> customersList = List.of(customers.builder().id("cust-001").email("a@a.com").name("customer1").build(),
                customers.builder().id("cust-002").email("b@b.com").name("customer2").build(),
                customers.builder().id("cust-003").email("c@c.com").name("customer3").build(),
                customers.builder().id("cust-004").email("d@d.com").name("customer4").build());
        cr.createCustomers(customersList);

        lookupAggregator lAgg = new lookupAggregator(client);
        lAgg.doLookup(100);
    }

    private static void doParksJob(MongoClient client)
    {
        parksUpdater pud = new parksUpdater(client);
        pud.printParks();
        pud.updateLoc("Barcelona",2022,2023,2024);
        pud.addLoc("Barcelona",2345);
        pud.setSingleLoc("Barcelona",2456);
        pud.getMulticities(1802,1803);
    }
    private static void purgeRadio(MongoClient client)
    {
        radioPhaseOut rp = new radioPhaseOut(client);
        rp.decom_by_id("660c78ba144b0e280e57d4e1");
    }
    private static void updateRadio(MongoClient client)
    {
        radioUpdater updater = new radioUpdater(client);
        updater.change_MaxBw_Single("660c40e5d64ca11ace7e2bfc",40);
        updater.change_MinBw_Multi(15);
    }
    private static void doNprJob(MongoClient client)
    {
        newRadioProcessor npr = new newRadioProcessor(client);
        npr.createCollection();
        npr.addRadio("BLM",100,60, nrCategory.highband);
        npr.addRadioPojo("Omaha",60,20,nrCategory.midband);
        npr.addMultipleRadios(Arrays.asList(new newRadio("DesMoines",20,10,nrCategory.lowband)
                ,new newRadio("DesMoines",60,20,nrCategory.midband)
                ,new newRadio("Omaha",100,60,nrCategory.highband)
                ,new newRadio("Omaha",20,10,nrCategory.lowband)));
    }
    private static void invokeSimpleAgg(MongoClient client)
    {
        simpleAggregator sAgg = new simpleAggregator(client);
        sAgg.doAggregate();
        sAgg.filteredAggregate();
        sAgg.sortedAggregate();
        sAgg.aggregateWithComputedFields();
    }
    private static void doTrialSitesJob(MongoClient client)
    {
        trialSites ts = new trialSites(client);
        ts.fetchFirstDoc();
        ts.fetchFirstDocAsPojo();
        ts.fetchInfoAsPojo();
        ts.fetchWithCondition("Omaha");
        ts.getDocumentCount();
        ts.getSiteDocumentCount("BLM");
        ts.fetchSortedDoc();
    }
    private static void doLegacyRadioJob(MongoClient client)
    {
        legacyRadioProcessor lpr = new legacyRadioProcessor(client);
        lpr.createCollection();
        lpr.createSite("100","Roy",4,10, radioEss.builder().mode("ess").min_bw(5).max_bw(25).threshold(35.0).build());
        lpr.createSite("200","Merry",5,10, radioSuMimo.builder().mode("su-mimo").min_bw(10).max_bw(60).threshold(12.5).build());
    }
    private static void searchLegacySite(MongoClient client)
    {
        legacySites ls = new legacySites(client);
        ls.fetchAllLegacySites();
    }
    private static void addStoreLocationRecords(MongoClient client) {
        locationCreator lcreator = new locationCreator(client);
        lcreator.createCollection();
        lcreator.populateCollection("nyc",-73.9667,40.78,"kiosk");
        lcreator.populateCollection("mnh",-73.9855,40.7580,"full_service");
        lcreator.populateCollection("bronx",-73.9683,40.7851,"kiosk");
        lcreator.populateCollection("jersey city",-74.0517,40.7081,"full_service");
        lcreator.populateCollection("atlantic place",-74.0330,40.7161,"kiosk");

    }
    private static void atomicUpdateStoreType(MongoClient client)
    {
        locationUpdator lupdator = new locationUpdator(client);
        lupdator.findAndUpdate("jersey city","full_service");
    }
    private static void doDatasort(MongoClient client)
    {
        dataSort ds = new dataSort(client);
        ds.addStore("lfc_store","football memorabilia",new Random().nextInt(2500));
        ds.textSearch("coffee shop");
        ds.limitResults(2);
        ds.skipResults(3);
        ds.addDynamicDataToQuery();
    }
   private static void searchStoreLocationRecords(MongoClient client)
   {
       geoProximity gp = new geoProximity(client);
       gp.searchStore(10000,5000);
   }

}