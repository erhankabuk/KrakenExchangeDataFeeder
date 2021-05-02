package com.erhankabuk.KrakenExchangeDataFeeder.ServiceLayer;

import com.erhankabuk.KrakenExchangeDataFeeder.Model.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ServiceLayer {

    public ObjectMapper om = new ObjectMapper();

    public Map<Object, List<Object>> getDataFromAPIToHashMap() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        Map<Object,List<Object>> dataListFromAPI = new HashMap<>();
        String dailyURL = "https://api.kraken.com/0/public/OHLC?pair=XBTUSD&interval=1440";
        Root root =  om.readValue((restTemplate.getForObject(dailyURL, String.class)), Root.class);
        root.result.XXBTZUSD.stream().forEach(data->dataListFromAPI.put(data.get(0),data));
        return dataListFromAPI;
    }

    public Map<Object, List<Object>> getLastestDataFromAPIToHashMap() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Map<Object,List<Object>> dataListFromAPI = new HashMap<>();
        Date date = new Date();
        String dailyURL = "https://api.kraken.com/0/public/OHLC?pair=XBTUSD&interval=1440&since=" + (Long)date.getTime();
        Root root =  om.readValue((restTemplate.getForObject(dailyURL, String.class)), Root.class);
        root.result.XXBTZUSD.stream().forEach(data->dataListFromAPI.put(data.get(0),data));
        return dataListFromAPI;
    }

    public Map<Object, List<Object>> getSinceDataFromAPIToHashMap() throws JsonProcessingException, ParseException {
        RestTemplate restTemplate = new RestTemplate();
        Map<Object,List<Object>> dataListFromAPI = new HashMap<>();
        String dailyURL = "https://api.kraken.com/0/public/OHLC?pair=XBTUSD&interval=1440&since=" + convertDateToMilisecond();
        Root root =  om.readValue((restTemplate.getForObject(dailyURL, String.class)), Root.class);
        root.result.XXBTZUSD.stream().forEach(data->dataListFromAPI.put(data.get(0),data));
        return dataListFromAPI;
    }

    public String getLastestDateFromAPI() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Date date = new Date();
        String dailyURL = "https://api.kraken.com/0/public/OHLC?pair=XBTUSD&interval=1440&since=" + (Long)date.getTime();
        Root root =  om.readValue((restTemplate.getForObject(dailyURL, String.class)), Root.class);
        return new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(root.result.XXBTZUSD.get(0).get(0)+"000"));
    }

    public void setDataToJSONFileFromHashMap() throws IOException {
        getDataFromAPIToHashMap();
        for (List<Object> key: getDataFromAPIToHashMap().values()) {
            om.writeValue(new File(createJSONFileName((Integer)key.get(0))), key);}
    }

    public void setLastestDataToJSONFileFromHashMap() throws IOException {
        for ( List<Object> key: getLastestDataFromAPIToHashMap().values()) {
            om.writeValue(new File(createJSONFileName((Integer)key.get(0))), key);}
    }

    public void setSinceDataToJSONFileFromHashMap() throws IOException, ParseException {
        for ( List<Object> key: getSinceDataFromAPIToHashMap().values()) {
            om.writeValue(new File(createJSONFileName((Integer)key.get(0))), key);}
    }

    public String createJSONFileName(int miliSecond){
        //2021-03-01 BTCUSDT Daily AggTrades.json
        Long editedmilisecond = new Long(miliSecond)*new Long(1000);
        String fileName ="BTCUSDT "+ new SimpleDateFormat("yyyy-MM-dd").format(editedmilisecond) + " Daily AggTrades.json";
        return fileName;
    }

    public String getLastDateOfJSONFilesName(){
        try {
            //Check or change pathway before run...
            File dataFilesInPathName = new File("C:\\Users\\IdeaProjects\\KrakenExchangeDataFeeder");
            FilenameFilter filteredFileNames = new FilenameFilter() {
                @Override
                public boolean accept(File dataFiles, String name) {
                    return name.endsWith(".json");
                }
            };
            File[] files = dataFilesInPathName.listFiles(filteredFileNames);
            return files[files.length - 1].getName().split(" ")[1];
        }
        catch (Exception e) {
            return null;
        }
    }

    public long convertDateToMilisecond() throws ParseException {
        return (new SimpleDateFormat("yyyy-MM-dd").parse(getLastDateOfJSONFilesName()).getTime())/1000;
    }

    public void checkDatabaseIsUpToDate() throws IOException, ParseException {
        if(Objects.isNull(getLastDateOfJSONFilesName())){
            System.out.println("Database is empty...");
            setDataToJSONFileFromHashMap();
        }else if(!getLastestDateFromAPI().equals(getLastDateOfJSONFilesName())){
            System.out.println("DataBase is not up to dated...");
            setSinceDataToJSONFileFromHashMap();
        }else {
            setLastestDataToJSONFileFromHashMap();
            System.out.println("Database is up to dated...");
        }
    }

}
