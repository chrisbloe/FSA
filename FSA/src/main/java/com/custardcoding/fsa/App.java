package com.custardcoding.fsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
public class App {
    private static final String PASS = "Pass";
    private static final String IMPROVEMENT_REQUIRED = "Improvement Required";
    private static final List<String> validRatings = Arrays.asList("5", "4", "3", "2", "1", "0", PASS, IMPROVEMENT_REQUIRED);
    
    private static final String FIVES = "fives";
    private static final String FOURS = "fours";
    private static final String THREES = "threes";
    private static final String TWOS = "twos";
    private static final String ONES = "ones";
    private static final String ZEROS = "zeros";
    private static final String PASSES = "passes";
    private static final String IMPROVEMENT_REQUIREDS = "improvementRequireds";
    
    private static final List<String> establishmentList = Arrays.asList("Waitrose", "Aldi", "Lidl", "Asda", "Morrisons", "Sainsburys", "Tesco");
    
    private static final int limit = 10000;
    
    private static RestTemplate restTemplate = new RestTemplate();
    private static File parentFile;
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        parentFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
        
        sortProxy();
        sortJacksonConverter();
        
        long time = System.currentTimeMillis();
        
        System.out.println("Starting...");
        
        establishmentList.parallelStream().forEach((name) -> {
            makeCall(name);
        });
        
        zipIt();
        
        System.out.println("...finished in " + (System.currentTimeMillis() - time) / 1000 + "s");
    }

    private static void sortProxy() throws URISyntaxException {
        System.setProperty("java.net.useSystemProxies", "true");
        InetSocketAddress addr = (InetSocketAddress) ProxySelector.getDefault().select(new URI("http://www.yahoo.com/")).get(0).address();
        
        if (addr == null) {
            System.out.println("No Proxy");
        } else {
            System.out.println("Using proxy " + addr.getHostName() + ':' + addr.getPort());
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(new Proxy(Type.HTTP, new InetSocketAddress(addr.getHostName(), addr.getPort())));
            restTemplate = new RestTemplate(requestFactory);
        }
    }

    private static void sortJacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(new MediaType("text", "html"));
        }});

        restTemplate.setMessageConverters(Arrays.asList(converter));
    }
    
    private static String getContent(Map<String, List<EstablishmentDetail>> results) {
        int maxSize = results.values()
                             .parallelStream()
                             .mapToInt(result -> result.size())
                             .max()
                             .getAsInt();
        
        StringBuilder builder = new StringBuilder()
                .append("\"5 (").append(results.get(FIVES).size()).append(")\",\"\",\"\",")
                .append("\"4 (").append(results.get(FOURS).size()).append(")\",\"\",\"\",")
                .append("\"3 (").append(results.get(THREES).size()).append(")\",\"\",\"\",")
                .append("\"2 (").append(results.get(TWOS).size()).append(")\",\"\",\"\",")
                .append("\"1 (").append(results.get(ONES).size()).append(")\",\"\",\"\",")
                .append("\"0 (").append(results.get(ZEROS).size()).append(")\",\"\",\"\",")
                .append("\"Pass (").append(results.get(PASSES).size()).append(")\",\"\",\"\",")
                .append("\"Improvement Required (").append(results.get(IMPROVEMENT_REQUIREDS).size()).append(")\",\"\",\"\"");
        
        for (int i = 0; i < maxSize; i++) {
            builder.append('\n');
            addResult(builder, results.get(FIVES), i, true);
            addResult(builder, results.get(FOURS), i, true);
            addResult(builder, results.get(THREES), i, true);
            addResult(builder, results.get(TWOS), i, true);
            addResult(builder, results.get(ONES), i, true);
            addResult(builder, results.get(ZEROS), i, true);
            addResult(builder, results.get(PASSES), i, true);
            addResult(builder, results.get(IMPROVEMENT_REQUIREDS), i, false);
        }
        
        return builder.toString();
    }
    
    private static void addResult(StringBuilder builder, List<EstablishmentDetail> result, int i, boolean addComma) {
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
               !businessName.contains("distribution") &&
               !businessName.contains("eurest") &&
               !businessName.contains("greggs") &&
               !businessName.contains("krispy") &&
               !businessName.contains("petrol") &&
               !businessName.contains("subway") &&
               validRatings.contains(detail.getRatingValue());
    }

    private static void makeCall(String name) {
        Map<String, List<EstablishmentDetail>> results = new HashMap<String, List<EstablishmentDetail>>() {{
            put(FIVES, new ArrayList<>());
            put(FOURS, new ArrayList<>());
            put(THREES, new ArrayList<>());
            put(TWOS, new ArrayList<>());
            put(ONES, new ArrayList<>());
            put(ZEROS, new ArrayList<>());
            put(PASSES, new ArrayList<>());
            put(IMPROVEMENT_REQUIREDS, new ArrayList<>());
        }};

        String url = "http://ratings.food.gov.uk/search/en-GB/" + name + "/^/1/" + limit + "/json";
        
        Result result = restTemplate.getForObject(url, Result.class);
        
        List<EstablishmentDetail> establishmentDetails = result.getFhrsEstablishment().getEstablishmentCollection().getEstablishmentDetails();
        establishmentDetails = removeBadEntriesAndDuplicates(establishmentDetails);
        
        establishmentDetails.parallelStream().forEach(detail -> {
            switch (detail.getRatingValue()) {
                case "5":
                    results.get(FIVES).add(detail);
                    break;
                case "4":
                    results.get(FOURS).add(detail);
                    break;
                case "3":
                    results.get(THREES).add(detail);
                    break;
                case "2":
                    results.get(TWOS).add(detail);
                    break;
                case "1":
                    results.get(ONES).add(detail);
                    break;
                case "0":
                    results.get(ZEROS).add(detail);
                    break;
                case PASS:
                    results.get(PASSES).add(detail);
                    break;
                case IMPROVEMENT_REQUIRED:
                    results.get(IMPROVEMENT_REQUIREDS).add(detail);
                    break;
                default:
                    System.out.println("Bad value: " + detail.getRatingValue());
            }
        });
        
        createFile(name, results);
    }
    
    private static void createFile(String name, Map<String, List<EstablishmentDetail>> results) {
        FileOutputStream fop = null;
        try {
            File file = new File(parentFile, name + ".csv");
            
            if (file.exists()) {
                file.delete();
            }
            
            file.createNewFile();
            
            fop = new FileOutputStream(file);
            fop.write(getContent(results).getBytes());
            fop.flush();
            fop.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
        Map<String, EstablishmentDetail> map = new HashMap<>();
        
        establishmentDetails.stream().filter(detail -> goodDetail(detail)).forEach((detail) -> {
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
        });
        
        return new ArrayList<>(map.values());
    }

    private static String getFormattedPostcode(EstablishmentDetail detail) {
        return detail.getPostCode().trim().replace(" ", "").toLowerCase();
    }
    
    private static void zipIt() {
        try {
            File zipFile = new File(parentFile, "FSA.zip");
            
            if (zipFile.exists()) {
                zipFile.delete();
            }

            zipFile.createNewFile();

            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
                byte[] buffer = new byte[1024];
                
                for (String fileName : establishmentList) {
                    zos.putNextEntry(new ZipEntry(fileName + ".csv"));
                    
                    File file = new File(parentFile, fileName + ".csv");
                    
                    FileInputStream in = new FileInputStream(file);
                    
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                
                zos.closeEntry();
                
                // Now delete the files
                // https://stackoverflow.com/questions/991489/i-cant-delete-a-file-in-java
                // http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4715154
                System.gc();
                establishmentList.parallelStream().forEach((fileName) -> {
                    new File(parentFile, fileName + ".csv").delete();
                });
            }
        } catch (IOException ex) {
            System.out.println("Oh dear: " + ex.getMessage());
        }
    }
}
