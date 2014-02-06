package com.custardcoding.fsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
public class App {
    private static final List<ArrayList<EstablishmentDetail>> results = new ArrayList<ArrayList<EstablishmentDetail>>();
    private static final ArrayList<EstablishmentDetail> fives = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> fours = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> threes = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> twos = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> ones = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> zeros = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> passes = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> improvementRequireds = new ArrayList<EstablishmentDetail>();
    
    private static final RestTemplate restTemplate = new RestTemplate();
    
    public static void main(String[] args) throws IOException {
        List<String> names = Arrays.asList("Waitrose");
//        List<String> names = Arrays.asList("Waitrose", "Aldi", "Lidl", "Asda", "Morrisons", "Sainsburys", "Tesco");
        
        results.add(fives);
        results.add(fours);
        results.add(threes);
        results.add(twos);
        results.add(ones);
        results.add(zeros);
        results.add(passes);
        results.add(improvementRequireds);
        
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();

        converter.setSupportedMediaTypes(new ArrayList<MediaType>(){{
            add(new MediaType("text", "html"));
        }});

        List<HttpMessageConverter<?>> mc = restTemplate.getMessageConverters();
        mc.add(converter);
        restTemplate.setMessageConverters(mc);
        
        for (String name : names) {
            makeCall(name);
        }
    }
    
    private static String getContent() {
        int maxSize = 0;
        
        for (ArrayList<EstablishmentDetail> result : results) {
            maxSize = result.size() > maxSize
                    ? result.size()
                    : maxSize;
        }
        
        StringBuilder builder = new StringBuilder()
                .append("\"5 (").append(fives.size()).append(")\",\"\",\"\",")
                .append("\"4 (").append(fours.size()).append(")\",\"\",\"\",")
                .append("\"3 (").append(threes.size()).append(")\",\"\",\"\",")
                .append("\"2 (").append(twos.size()).append(")\",\"\",\"\",")
                .append("\"1 (").append(ones.size()).append(")\",\"\",\"\",")
                .append("\"0 (").append(zeros.size()).append(")\",\"\",\"\",")
                .append("\"Pass (").append(passes.size()).append(")\",\"\",\"\",")
                .append("\"Improvement Required (").append(improvementRequireds.size()).append(")\",\"\",\"\"");
        
        for (int i = 0; i < maxSize; i++) {
            builder.append('\n');
            addResult(builder, fives, i, true);
            addResult(builder, fours, i, true);
            addResult(builder, threes, i, true);
            addResult(builder, twos, i, true);
            addResult(builder, ones, i, true);
            addResult(builder, zeros, i, true);
            addResult(builder, passes, i, true);
            addResult(builder, improvementRequireds, i, false);
        }
        
        return builder.toString();
    }
    
    private static void addResult(StringBuilder builder, ArrayList<EstablishmentDetail> result, int i, boolean addComma) {
        try {
            EstablishmentDetail detail = result.get(i);
            
            builder.append('\"')
                   .append(detail.getBusinessName())
                   .append("\",\"")
                   .append(getAddress(detail))
                   .append("\",\"")
                   .append(detail.getPostCode())
                   .append('\"');
            
            if (addComma) {
                builder.append(',');
            }
        } catch (IndexOutOfBoundsException e) {
            builder.append("\"\",\"\",\"\",");
        }
    }

    private static boolean goodDetail(EstablishmentDetail detail) {
        String businessName = detail.getBusinessName();
        
        return !businessName.contains("Avenance") &&
               !businessName.contains("Compass") &&
               !businessName.contains("Costa") &&
               !businessName.contains("Eurest") &&
               !businessName.contains("Greggs") &&
               !businessName.contains("Krispy ") &&
               !businessName.contains("Subway") &&
               !businessName.contains("Avenance") &&
               !businessName.contains("distribution");
    }

    private static void makeCall(String name) {
        fives.clear();
        fours.clear();
        threes.clear();
        twos.clear();
        ones.clear();
        zeros.clear();
        passes.clear();
        improvementRequireds.clear();

//        String url = "http://ratings.food.gov.uk/search/en-GB/" + name + "/^/1/20/json";
        String url = "http://ratings.food.gov.uk/search/en-GB/" + name + "/^/1/10000/json";
        
        Result result = restTemplate.getForObject(url, Result.class);
        
        for (EstablishmentDetail detail : result.getFhrsEstablishment().getEstablishmentCollection().getEstablishmentDetails()) {
            if (goodDetail(detail)) {
                if (detail.getRatingValue().equals("5")) {
                    fives.add(detail);
                } else if (detail.getRatingValue().equals("4")) {
                    fours.add(detail);
                } else if (detail.getRatingValue().equals("3")) {
                    threes.add(detail);
                } else if (detail.getRatingValue().equals("2")) {
                    twos.add(detail);
                } else if (detail.getRatingValue().equals("1")) {
                    ones.add(detail);
                } else if (detail.getRatingValue().equals("0")) {
                    zeros.add(detail);
                } else if (detail.getRatingValue().equals("Pass")) {
                    passes.add(detail);
                } else if (detail.getRatingValue().equals("Improvement Required")) {
                    improvementRequireds.add(detail);
                }
            }
        } 
        
        createFile(name);
    }
    
    private static void createFile(String name) {
        FileOutputStream fop = null;
        try {
            File file = new File(new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile(), name + ".csv");
            
            if (file.exists()) {
                file.delete();
            }
            
            file.createNewFile();
            
            fop = new FileOutputStream(file);
            fop.write(getContent().getBytes());
            fop.flush();
            fop.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fop.close();
            } catch (IOException ex) { }
        }
    }

    private static StringBuilder getAddress(EstablishmentDetail establishmentDetail) {
        StringBuilder builder = new StringBuilder();
        
        if (establishmentDetail.getAddressLine1() != null) {
            builder.append(establishmentDetail.getAddressLine1());
        }
        
        if (establishmentDetail.getAddressLine2() != null) {
            builder.append(", ").append(establishmentDetail.getAddressLine2());
        }
        
        if (establishmentDetail.getAddressLine3() != null) {
            builder.append(", ").append(establishmentDetail.getAddressLine3());
        }
        
        if (establishmentDetail.getAddressLine4() != null) {
            builder.append(", ").append(establishmentDetail.getAddressLine4());
        }
        
        return builder;
    }
}
