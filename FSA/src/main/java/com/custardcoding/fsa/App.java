package com.custardcoding.fsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
public class App {
    private static final String PASS = "Pass";
    private static final String IMPROVEMENT_REQUIRED = "Improvement Required";
    private static final List<String> validRatings = Arrays.asList("5", "4", "3", "2", "1", "0", PASS, IMPROVEMENT_REQUIRED);
    
    private static final ArrayList<EstablishmentDetail> fives = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> fours = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> threes = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> twos = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> ones = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> zeros = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> passes = new ArrayList<EstablishmentDetail>();
    private static final ArrayList<EstablishmentDetail> improvementRequireds = new ArrayList<EstablishmentDetail>();
    
    private static final List<ArrayList<EstablishmentDetail>> results = new ArrayList<ArrayList<EstablishmentDetail>>() {{
        add(fives);
        add(fours);
        add(threes);
        add(twos);
        add(ones);
        add(zeros);
        add(passes);
        add(improvementRequireds);
    }};
    
    private static RestTemplate restTemplate = new RestTemplate();
    
    public static void main(String[] args) throws IOException {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setProxy(new Proxy(Type.HTTP, new InetSocketAddress("proxy.costcutterhq.com", 8080)));
//        restTemplate = new RestTemplate(requestFactory);
        
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();

        converter.setSupportedMediaTypes(new ArrayList<MediaType>(){{
            add(new MediaType("text", "html"));
        }});

        List<HttpMessageConverter<?>> mc = restTemplate.getMessageConverters();
        mc.add(converter);
        restTemplate.setMessageConverters(mc);
        
//        for (String name : Collections.singletonList("Asda")) {
        for (String name : Arrays.asList("Waitrose", "Aldi", "Lidl", "Asda", "Morrisons", "Sainsburys", "Tesco")) {
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
        String businessName = detail.getBusinessName().toLowerCase();
        
        return !businessName.contains("avenance") &&
               !businessName.contains("compass") &&
               !businessName.contains("costa") &&
               !businessName.contains("eurest") &&
               !businessName.contains("greggs") &&
               !businessName.contains("krispy") &&
               !businessName.contains("subway") &&
               !businessName.contains("avenance") &&
               !businessName.contains("distribution") &&
               validRatings.contains(detail.getRatingValue());
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
        
        List<EstablishmentDetail> establishmentDetails = result.getFhrsEstablishment().getEstablishmentCollection().getEstablishmentDetails();
        establishmentDetails = removeBadEntriesAndDuplicates(establishmentDetails);
        
        for (EstablishmentDetail detail : establishmentDetails) {
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
            } else if (detail.getRatingValue().equalsIgnoreCase(PASS)) {
                passes.add(detail);
            } else if (detail.getRatingValue().equalsIgnoreCase(IMPROVEMENT_REQUIRED)) {
                improvementRequireds.add(detail);
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

    private static List<EstablishmentDetail> removeBadEntriesAndDuplicates(List<EstablishmentDetail> establishmentDetails) {
        Map<String, EstablishmentDetail> map = new HashMap<String, EstablishmentDetail>();
        
        for (EstablishmentDetail detail : establishmentDetails) {
            if (goodDetail(detail)) {
                if (null == detail.getPostCode() || detail.getPostCode().isEmpty() || detail.getPostCode().equalsIgnoreCase("null")) {
                    map.put(UUID.randomUUID().toString(), detail);
                } else {
                    String formattedPostcode = getFormattedPostcode(detail);
                    if (map.containsKey(formattedPostcode)) {
                        try {
                            if (Integer.valueOf(map.get(formattedPostcode).getRatingValue()) > Integer.valueOf(detail.getRatingValue())) {
                                map.put(formattedPostcode, detail);
                            }
                        } catch (NumberFormatException ex) {
                            if (PASS.equals(map.get(formattedPostcode).getRatingValue())) {
                                map.put(formattedPostcode, detail);
                            }
                        }
                    } else {
                        map.put(formattedPostcode, detail);
                    }
                }
            }
        }
        
        return new ArrayList<EstablishmentDetail>(map.values());
    }

    private static String getFormattedPostcode(EstablishmentDetail detail) {
        return detail.getPostCode().trim().replace(" ", "").toLowerCase();
    }
}
