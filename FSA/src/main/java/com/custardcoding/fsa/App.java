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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Kris Bloe
 */
public class App {
//    private static final List<String> businessList = Collections.singletonList("Tesco");
    private static final List<String> businessList = Arrays.asList("Waitrose", "Aldi", "Lidl", "Asda", "Morrisons", "Sainsburys", "Tesco");

//    private static final int quantity = 20;
    private static final int quantity = 10000;

    private static final String PASS = "Pass";
    private static final String IMPROVEMENT_REQUIRED = "Improvement Required";

    private static final String FIVES = "fives";
    private static final String FOURS = "fours";
    private static final String THREES = "threes";
    private static final String TWOS = "twos";
    private static final String ONES = "ones";
    private static final String ZEROS = "zeros";
    private static final String PASSES = "passes";
    private static final String IMPROVEMENT_REQUIREDS = "improvementRequireds";

    private static RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static File parentFile;

    public static void main(String[] args) throws Exception {
        headers.add("x-api-version", "2");
        parentFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
        
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

        System.out.println("Starting...");
        Long time = System.currentTimeMillis();

        businessList.parallelStream().forEach((name) -> {
            makeCall(name);
        });
        
        zipIt();

        System.out.println("...call took " + (System.currentTimeMillis() - time) / 1000 + "s");
    }

    private static String getContent(Map<String, List<Establishment>> establishmentsMap) {
        int maxSize = establishmentsMap.values()
                                       .parallelStream()
                                       .mapToInt(establishments -> establishments.size())
                                       .max()
                                       .getAsInt();

        StringBuilder builder = new StringBuilder()
                .append("\"5 (").append(establishmentsMap.get(FIVES).size()).append(")\",\"\",\"\",")
                .append("\"4 (").append(establishmentsMap.get(FOURS).size()).append(")\",\"\",\"\",")
                .append("\"3 (").append(establishmentsMap.get(THREES).size()).append(")\",\"\",\"\",")
                .append("\"2 (").append(establishmentsMap.get(TWOS).size()).append(")\",\"\",\"\",")
                .append("\"1 (").append(establishmentsMap.get(ONES).size()).append(")\",\"\",\"\",")
                .append("\"0 (").append(establishmentsMap.get(ZEROS).size()).append(")\",\"\",\"\",")
                .append("\"Pass (").append(establishmentsMap.get(PASSES).size()).append(")\",\"\",\"\",")
                .append("\"Improvement Required (").append(establishmentsMap.get(IMPROVEMENT_REQUIREDS).size()).append(")\",\"\",\"\"");

        for (int i = 0; i < maxSize; i++) {
            builder.append('\n');
            addEstablishment(builder, establishmentsMap.get(FIVES), i, true);
            addEstablishment(builder, establishmentsMap.get(FOURS), i, true);
            addEstablishment(builder, establishmentsMap.get(THREES), i, true);
            addEstablishment(builder, establishmentsMap.get(TWOS), i, true);
            addEstablishment(builder, establishmentsMap.get(ONES), i, true);
            addEstablishment(builder, establishmentsMap.get(ZEROS), i, true);
            addEstablishment(builder, establishmentsMap.get(PASSES), i, true);
            addEstablishment(builder, establishmentsMap.get(IMPROVEMENT_REQUIREDS), i, false);
        }

        return builder.toString();
    }

    private static void addEstablishment(StringBuilder builder, List<Establishment> establishments, int i, boolean addComma) {
        try {
            Establishment establishment = establishments.get(i);

            builder.append('\"')
                   .append(establishment.getBusinessName())
                   .append("\",\"")
                   .append(getAddress(establishment))
                   .append("\",\"")
                   .append(establishment.getPostCode())
                   .append('\"');

            if (addComma) {
                builder.append(',');
            }
        } catch (IndexOutOfBoundsException e) {
            builder.append("\"\",\"\",\"\",");
        }
    }

    private static boolean isRelevantEstablishment(Establishment establishment) {
        String businessName = establishment.getBusinessName();

        return !businessName.contains("Avenance") &&
               !businessName.contains("avenance") &&
               !businessName.contains("Compass") &&
               !businessName.contains("compass") &&
               !businessName.contains("Costa") &&
               !businessName.contains("costa") &&
               !businessName.contains("Distribution") &&
               !businessName.contains("distribution") &&
               !businessName.contains("Eurest") &&
               !businessName.contains("eurest") &&
               !businessName.contains("Greggs") &&
               !businessName.contains("greggs") &&
               !businessName.contains("Krispy") &&
               !businessName.contains("krispy") &&
               !businessName.contains("Petrol") &&
               !businessName.contains("petrol") &&
               !businessName.contains("Subway") &&
               !businessName.contains("subway");
    }

    private static void makeCall(String name) {
        Map<String, List<Establishment>> establishmentsMap = new HashMap<String, List<Establishment>>() {{
            put(FIVES, new ArrayList<>());
            put(FOURS, new ArrayList<>());
            put(THREES, new ArrayList<>());
            put(TWOS, new ArrayList<>());
            put(ONES, new ArrayList<>());
            put(ZEROS, new ArrayList<>());
            put(PASSES, new ArrayList<>());
            put(IMPROVEMENT_REQUIREDS, new ArrayList<>());
        }};

        String url = "http://api.ratings.food.gov.uk/Establishments?name=" + name + "%&pageSize=" + quantity;

        List<Establishment> establishments = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Result.class).getBody().getEstablishments();
        establishments = removeDuplicates(establishments);

        establishments.parallelStream().forEach(establishment -> {
            if (isRelevantEstablishment(establishment)) {
                switch (establishment.getRatingValue()) {
                    case "5":
                        establishmentsMap.get(FIVES).add(establishment);
                        break;
                    case "4":
                        establishmentsMap.get(FOURS).add(establishment);
                        break;
                    case "3":
                        establishmentsMap.get(THREES).add(establishment);
                        break;
                    case "2":
                        establishmentsMap.get(TWOS).add(establishment);
                        break;
                    case "1":
                        establishmentsMap.get(ONES).add(establishment);
                        break;
                    case "0":
                        establishmentsMap.get(ZEROS).add(establishment);
                        break;
                    case PASS:
                        establishmentsMap.get(PASSES).add(establishment);
                        break;
                    case IMPROVEMENT_REQUIRED:
                        establishmentsMap.get(IMPROVEMENT_REQUIREDS).add(establishment);
                        break;
                    default:
                        System.out.println("Bad value: " + establishment.getRatingValue());
                }
            }
        });

        createFile(name, establishmentsMap);
    }

    private static void createFile(String name, Map<String, List<Establishment>> establishmentsMap) {
        FileOutputStream fop = null;
        try {
            File file = new File(parentFile, name + ".csv");

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            fop = new FileOutputStream(file);
            fop.write(getContent(establishmentsMap).getBytes());
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

    private static StringBuilder getAddress(Establishment establishment) {
        StringBuilder builder = new StringBuilder();

        if (establishment.getAddressLine1() != null) {
            builder.append(establishment.getAddressLine1());
        }

        if (establishment.getAddressLine2() != null) {
            builder.append(", ").append(establishment.getAddressLine2());
        }

        if (establishment.getAddressLine3() != null) {
            builder.append(", ").append(establishment.getAddressLine3());
        }

        if (establishment.getAddressLine4() != null) {
            builder.append(", ").append(establishment.getAddressLine4());
        }

        return builder;
    }

    private static List<Establishment> removeDuplicates(List<Establishment> establishments) {
        Map<String, Establishment> map = new HashMap<>();

        establishments.stream().forEach((establishment) -> {
            String postCode = establishment.getPostCode();
            if (null == postCode || postCode.isEmpty() || postCode.equals("null")) {
                map.put(UUID.randomUUID().toString(), establishment);
            } else {
                String formattedPostcode = getFormattedPostcode(establishment);
                if (map.containsKey(formattedPostcode)) {
                    try {
                        if (Integer.valueOf(map.get(formattedPostcode).getRatingValue()) > Integer.valueOf(establishment.getRatingValue())) {
                            map.put(formattedPostcode, establishment);
                        }
                    } catch (NumberFormatException ex) {
                        if (PASS.equals(map.get(formattedPostcode).getRatingValue())) {
                            map.put(formattedPostcode, establishment);
                        }
                    }
                } else {
                    map.put(formattedPostcode, establishment);
                }
            }
        });

        return new ArrayList<>(map.values());
    }

    private static String getFormattedPostcode(Establishment establishment) {
        return establishment.getPostCode().trim().replace(" ", "").toLowerCase();
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
                
                for (String fileName : businessList) {
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
                businessList.parallelStream().forEach((fileName) -> {
                    new File(parentFile, fileName + ".csv").delete();
                });
            }
        } catch (IOException ex) {
            System.out.println("Oh dear: " + ex.getMessage());
        }
    }
}
