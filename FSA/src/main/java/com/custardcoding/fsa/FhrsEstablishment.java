package com.custardcoding.fsa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FhrsEstablishment {
    @JsonProperty(value = "EstablishmentCollection")
    private EstablishmentCollection establishmentCollection;

    public FhrsEstablishment() { }

    public EstablishmentCollection getEstablishmentCollection() {
        return establishmentCollection;
    }

    public void setEstablishmentCollection(EstablishmentCollection establishmentCollection) {
        this.establishmentCollection = establishmentCollection;
    }
    
//    public static void main(String[] args) {
//        List<String> postcodes = Arrays.asList("NN2 7BA","PO1 1SL","BL3 4EB","LE67 2LF","SK1 1UA","HD5 9AD","LL13 8HN","BL3 2QS","S41 8JZ","NE4 8BE","SR4 9AS","SR2 0AB","SK7 4AG","NE33 1AZ","NE35 9AR","BN15 8AG","LE10 1SS","DL1 3RB","BT80 8TL","L19 1SJ","L15 9EB","LA1 5JR","HD2 2LQ","IP2 9TH","WF12 9AE","DE21 7LW","NG17 2AH","BT34 4AF","WS1 1RS","DN11 0LP","NN4 8ER","L20 6HX","TN2 3EY","B78 3HB","L13 6RH","PE37 7AU","FY4 4QH","WA2 7NG","WF17 5DR","BL1 8QG","DN15 6UD","TN24 0SE","DN4 9DL","BT52 1QN","NN16 9HU","LL57 1LJ","BN1 8AS","NE5 2LD","NG5 7ED","CM20 1AN","YO32 9LF","SP8 4QA","PR7 1EX","NG22 9PL","NG24 1XG","NE15 8SJ","NE38 7NF","NE3 3HQ","NE4 6QA","ME10 2PB","CM8 2HJ","TW13 4BB","DN4 7PD","CH41 7BG","WS1 1RS","NE10 0DJ","DN12 4XX","BT40 1FD","CO12 3HJ","DL1 2BJ","WA10 2EF","LL31 9LU","DY2 9RF","B10 0TZ","B76 1XL","TA9 3BX","B33 8LF","LU2 9TA","WA8 6AF","N9 0AL","DA17 6DF","DA11 0DQ","HP2 4AA","NG2 7JA","DY5 1QL","HU3 1TY","CH6 5GB","BD4 7SR","CH41 6ED","HU8 0TX","S70 5AU","S71 4BP","S63 0JZ","S71 1LN","S63 9NQ","BB3 1BE","TS6 6AB","BT78 1QZ","BD12 9JQ","BT82 8EQ","DL1 4PF","CM9 4NW","TQ2 7AN","NR30 1SF","DN6 8DN","WV1 4DE","TA21 8RQ","BB4 8EL","WS11 1LH","DL16 6QB","SS6 9JR","TS3 0DY","SR8 5HA","CH4 8RH","CF83 3SX","null","null","TS10 4NY","CV6 3EX","ST5 0AY","ST5 0EN","TF9 1HY","SE1 5AS","CA28 9DR","WN2 2QA","SR7 7DR","BS14 0ST","BS3 4JY","CA14 1NQ","null","FY7 6NU","B37 5EX","ST13 6EN","ST10 1HF","DL14 7NA","PO12 1SH","null","TF2 7RX","BT11 9AP","BT15 3PR","S81 7AZ","DE24 3DS","null","LS11 8AG","NE11 9YA","WS11 1LH","WA3 3RW","LS9 6DT","LS16 7RY","LS14 6UF","LS4 2EZ","TS24 0XR","LS6 4JP",
//                "LS27 0BN","LS28 6AR","B68 0PH","SN5 7DL","WS11 1LH","B61 8DA","PO7 7XR","LS12 4BR","WA1 2QA","CM20 1AN","LN6 8JY","RG6 5TT","CM20 1AN","LE3 2LL","WN7 5RZ","WA5 8UH","E14 3BT","TS17 9EN","WS3 3JR","null","DE14 3TN","LA14 5UG","BL9 8XX","L20 4BB","RM18 7HJ","SK14 1BD","CV21 4DU","TS17 9EN","TN37 7AA","L24 9WA","PL31 2LW","BN23 6JH","L10 3LN","SR2 9TT","RM9 6SJ","NE3 5BU","CH41 6ED","SW15 3DT","CT20 1AU","OL6 7PF","NE33 1AZ","SS14 1AE","WD24 7RT","null","NP12 0NT","BS34 7JL","PL25 4AD","LE3 2LL","CF31 3AG","CA14 1NQ","PO14 1TT","LA9 7JA",
//                "BT15 1WA","SR8 5HA","OL2 8QP","null","EX39 3QU","BS20 7XN","CF23 8NL","ST6 4HG","FY4 4QH","null","LA1 5JR","BT82 8EQ","NN16 8HU","SA4 4NZ","BB5 1QR","TS18 2PB","TS24 0XR","CF83 3AD","DN32 9DL","DE24 3DS","TS17 9EN","WF6 1TN","BS11 8AS","BT78 1QZ","null","DA9 9BT","E10 5NH","BB5 1QR","BD5 0PX","PE4 6ZR","WA14 5GR","LS10 1ET","NE11 0BD","null","CV3 2TA","LS20 9NE","CH4 0DP","HU10 6RJ","LS20 9NE","null","OL2 5HX","N15 4QD","BT15 1WA","BS30 7DY","TN37 7AA","BS34 5TL","M9 4DH","PE30 3UG","NG31 6NZ","NE38 8NW","TR10 9LY","SL1 9LA","M33 3NA","M41 7ZA","null","SR8 1AR","DA2 6QR","DA2 6QR","null","OL6 7PF","BS30 7DY","LL13 8HN","BB5 1QR","TS24 0XR","DN32 9DL","BB5 1QR","CA3 0JQ","HP12 4NT","M7 4UF","N17 9JF","LL18 5EQ","PR25 2TE","NE12 9SR","SR2 9TT","RM18 7HJ","L20 4BB","SG1 1LA","BD18 3RY","WD24 7RT","SS14 3AS","OL9 0JE","SA4 4BZ","LE4 8GQ","BD21 3ER","CH65 0BZ","WA3 7PG","B66 3PR","DY4 0BP","B69 4PU","M27 4ET","WF8 1NL","NE24 4LZ","PR6 7TL","CM3 5SY","S5 8NH","WF1 1QQ","WF2 7EQ","TS18 2PB","DN6 8DN","WF10 5EL","null","PE13 1PE","BB8 8LU","SY3 7ET","HG1 5DE","CT10 2AL","CT11 7PR","TQ12 1TG","DN31 1UF","NG10 1NY","null","CM2 6RF","null","BD1 4JB","S2 4DR","NG18 5LG","S6 1TA","L10 3LN","PL6 8TB","PR9 0TQ","AL10 0JW","WA7 2PY","BT16 1RN",
//                "BL6 6JA","CV21 3EB","M41 7ZA","RM1 3EE","CB1 3ET","HR2 7JE","L5 6SP","S35 2UW","S8 0SQ","S12 2AB","CV21 2DN","L24 9WA","BH8 9UN","L4 9XU","M33 3NA","TD15 2DS","LE2 4AH","CF40 2JQ","NE11 9YA","CV31 1YD","DT4 8JQ","NE3 5BU","BT63 5AQ","HX1 4PG","L15 3JR","LL53 5SB","NE63 9XG","S13 9BM","OX33 1YZ","NG7 5FP","B43 7HA","B43 7HA","B42 1AA","WN8 6LH","CH5 1TP","CF23 8NL","SN5 7DL","SP10 2SJ","B42 1AB","B42 1AA","LL77 7DW","B76 1XL","TA6 3TA","BR8 7UN","BB2 3AG","CT16 2QH","SS3 8DA","TS12 2LQ","NP23 4SL","OL2 8QP","CV2 2BL","CW1 2PT","LE3 2LL","B10 0TZ","B42 1AA","LL30 1PJ","B10 0HH","B43 7HA","PO14 1TT","M30 7JA","NN10 0PA","RM9 6SJ","PR2 9NP","NE23 6QW","BA14 8AT","CO4 5TU","BT30 6AJ","TA1 2AN","SA15 1SH","DN14 5EZ","HU17 9EY","CA14 5AB","BL4 9DT","BS34 7JL","DN22 6EN","YO17 9RD","EN3 4EF","CB8 7SX","M18 7JH","M15 5AS","M22 4QN","ST6 4HG","TS17 9EN","BN27 1DP","SK4 1JG","DN2 4PE","NN2 6LS","DN12 1DL","GU22 8BD","NE24 5TS","LL55 1AT","DA6 8DZ","LE12 9QE","MK12 5JQ","CA1 2EA","SG4 9TS","SK8 5BB","M9 4DH","WV14 8QQ","LL65 2HY","SE15 4NB","E17 7JR","E10 5NH","BT20 4AS","CW7 1BG","WV10 0QV","EN11 8EP","OL8 4JZ","OL4 2RB","CV3 4AR","WS11 8UF","TS18 2DS","TS23 2LF","LN2 4SX","GL2 2SN","M22 5HZ","BD10 9AP","E5 9AG","S80 2BW","DL6 1DT","YO31 7UZ","NE31 1AA","LA4 5QW","B63 4AB","CH45 4NZ","NG16 4LH","SK14 1BD","ST16 3TA","PE1 1ET","BB12 0EQ","OL6 8ER","DY5 3BJ","CF31 3AG","CH49 5PD","OL6 7PF","RM8 1AT","NN17 5DT","RG30 4EL","NN17 5DT","HU4 6SH","HU7 3HS","CO5 0LA","PO9 3QW","EX39 3QU","L36 7TX","LA14 5UG","Westbrook Centre","DN6 7FB","NE38 8QA","BT74 6JG","SG1 1LA","RM20 3LP","CV37 0HZ",
//                "CH4 0DP","BT63 5AQ","WV1 4DE","BN1 8AS","N9 0AL","GL2 2SN","HU7 5US","BT63 5AQ","BT23 4EU","WF6 1TN","TQ12 1TQ","BH8 9UN","EN1 1TW","SO53 3YJ","MK43 9AB","BN2 5UT","DY1 1QS","CF11 0JR","TW7 7DY","CT20 1AU","DN17 2XF","GL1 1DS","CH62 3QP","TW3 1JT","CV11 4FL","BT39 9BB","IP33 3SP","SW11 1JG","BA11 5LA","S72 7LT","null","NP12 0NT","PE21 8EQ","BN12 6PN","WN7 1QP","WN3 6XA","RG22 4DH","DH9 0NB","NR6 5DT","LA9 7JA","TF3 4AE","WS3 3JR","RG6 5TT","RG6 5TT","NG19 0HA","LE10 1SS","DA9 9BT","HU9 2AH","null","BN12 6PN","BB12 0EQ","NW10 7LW",
//                "BD21 3ER","BT74 6JG","BR8 7UN","WN5 0XA","WS2 8LR","BT23 4EU","BT52 1QN","DY5 1SY","BT80 8TL","TA1 2AN","LE2 4AH","BN23 6JH","CF31 3AG","LA14 5UG","SK14 1BD","ST5 0AP","BR8 7UN","WS10 8UZ","CF11 8EG","IP14 1DE","PE30 3UG","SS14 1JH","SA9 2DE","DY4 7HW","DY4 7HW","CT1 1DG","SG18 0JS","S6 3TD","MK1 1QB","BH8 8DL","SA72 6DA","SN25 4BG","CF44 0AH","LU5 4JD","BA20 2HB","null","YO43 3AH","OL16 5AF","IP1 5PD","CF34 9UN","NG19 0HA","ST4 2HE","ME19 4SZ","GU14 7LT","BT41 4LL","DN32 9DL","HU9 2AH","HU11 4AL","SN25 4BG","CF14 7EW","CF72 8RP","CW1 2PT","TF1 2DE","LL13 7LW","N14 5PW","BT16 1RN","SW15 3DT","E14 3BT","SO30 3DQ","null","WN5 0UW","HU4 6SH","TW13 4BB","WN3 6BB","WA14 5GR","B63 4AB","DY4 7HW","HU9 2AH","HU4 6SH","CF48 2YF","SO53 3YJ","BS23 3UZ","IG11 8DJ","OL12 6XT","BN23 6JH","SO40 3ZA","NR33 0PX","SA6 8PS","KT20 5NZ","NP10 8XL","HA2 8JN","NG31 6NZ","N14 5PW","DA6 7BN","RH11 7AH","CR4 3HG","null","SO53 3YJ","DY5 1SY","NP44 1YH","NP19 4QQ","DA17 6DF","E14 3BT","NN17 5DT","NE38 7NF","DD2 4WB","EH42 1BF","null","G33 1AD","G23 5PZ","G51 3HR","G82 1RD","G81 2RZ","KA8 9BF","G77 6EY","null","ML2 9QS","KA12 8AU","null","AB22 8WQ","AB21 7NG","AB10 7QA","DD3 0SZ","AB24 5EZ","KY11 4LP","DD4 7XE","KY7 5QA","KA18 1LG","KY1 3NG","KA1 3XB","KY11 9YX","G31 4EB","IV2 6BZ","KA12 8EH","G67 1JW","KY7 5QA","G42 0AE","G81 2RZ","G33 1AD","FK10 1LB","KY11 4LR","ML1 1YP","KA22 8BZ","PA1 2AB","KA26 9HF","IV19 1GZ","FK1 1JT","FK5 3BF","DD11 2NQ","FK10 3SD","IV2 6BZ","null","G67 1JW","TD1 2AG","ML5 3BU","KY11 3AY","AB54 8SX","AB42 3JY","AB12 4XP","G61 2TX","G42 0AE","FK3 8TY","IV30 6YQ","PH1 5AP","FK3 8ZG","AB21 7NG","AB10 7QA","AB10 7QA");
//        
//        int listSize = postcodes.size();
//        
//        int mapSize = new HashSet(postcodes).size() - 1;
//        
//        for (String postcode : postcodes) {
//            if (postcode.equals("null")) {
//                mapSize++;
//            }
//        }
//        
//        System.out.println("Old list: " + listSize);
//        System.out.println("Old map: " + mapSize);
//        System.out.println("Old difference: " + (listSize - mapSize));
//        System.out.println();
//        
//        //////////////
//        
//        List<String> postcodes2 = Arrays.asList("DN12 1DL","S63 9NQ","WD24 7RT","LL53 5SB","CO4 5TU","B33 8LF","ST13 6EN","DE14 3TN","NG7 5FP","DY5 3BJ","HG1 5DE","SK7 4AG","TN24 0SE","S8 0SQ","NN16 8HU","null","TF9 1HY","LS6 4JP","DN14 5EZ","DN7 5QB","CV21 2DN","TN2 3EY","TS10 4NY","L15 3JR","CF83 3SX","HX1 4PG","WS1 1RS","HP12 4NT","null","BL4 9DT","LL65 2HY","NG10 1NY","NE33 1AZ","S41 8JZ","PE4 6ZR","LL13 8HN","PO9 3QW","SK8 5BB","NN16 9HU","CF40 2JQ","PE13 1PE","WF10 5EL","TF1 2DE","BB8 8LU","LL55 1AT","CV3 2TA","LE67 2LF","BL1 8QG","PR6 7TL","CW7 1BG","ST10 1HF","NN10 0PA","HU7 5US","EN3 4EF","B78 3HB","CO5 0LA","S71 1LN","DA1 1DY","OL6 7PF","DE21 7LW","CV6 3EX","L4 9XU","NG17 2AH","ST6 4HG","L20 4BB","CH41 7BG","BA20 2HB","B61 8DA","LS12 4BR","CM3 5SY","DY4 0BP","BT34 4AF","LL30 1PJ","TS17 9EN","DA6 8DZ","B9 5RP","LS9 6DT","SR7 7DR","M9 4DH","LA1 5JR","N9 0AL","ST5 0EN","LS10 1ET","WV1 4DE","FY7 6NU","LE3 2LL","NE11 0BD","SK4 1JG","CB8 7SX","CH45 4NZ","BL9 8XX","EN11 8EP","NE10 0DJ","CO12 3HJ","WA2 7NG","BS14 0ST","DL1 3RB","BA14 8AT","MK12 5JQ","WV14 8QQ","NE3 3HQ","S5 8NH","L20 6HX","null","null","WF6 1TN","RM8 1AT","SR8 5HA","PO12 1SH","PO7 7XR","null","WF8 1NL","LL13 7LW","LS21 1HE","N17 9JF","L5 6SP","DN11 0LP","NE12 9SJ","L19 1SJ","CA14 5AB","LU2 9TA","WF12 9AE","NE3 5BU","SL1 9LA","OL2 8QP","BD10 9AP","OL6 8ER","SK1 1UA","CH5 1TP","M41 7ZA","BB5 1QR","CT11 7PR","BL3 2QS","NN2 6LS","HP2 4AA","SR8 1AR","WA3 7PG","CB1 3ET","WN8 6LH","TQ2 7AN","L24 9WA","DA2 6QR","TS6 6AB","B37 5EX","LS28 6AR","PL6 8TB","CA1 2EA","TN37 7AA","YO31 7UZ","CA28 9DR","L13 6RH","B10 0HH","DN31 1UF","NE11 9YA","DN4 9DL","HR2 7JE","TS18 2PB","M22 5HZ","ME10 2PB","PR9 0TQ","CV21 4DU","BS11 8AS","E17 7JR","DY2 9RF","CH65 0BZ","null","NE23 6QW","DL6 1DT","RM18 7HJ","WA1 2QA","WN7 5RZ","WF2 7EQ","S62 6EE","IP2 9TH","WF17 5DR","HD2 2LQ","BT82 8EQ","B66 3PR","TS24 0XR","SN5 7DL","SG18 0JS","CV21 3EB","BT15 1WA","CM2 6RF","WA8 6AF","BD5 0PX","NE24 4LZ","null","DL16 6QB","BN27 1DP","L36 7TX","CV31 1YD","M7 4UF","BL6 6JA","E5 9AG","NE38 8NW","HU8 0TX","DN6 7FB","NG2 7JA","L15 9EB","WA3 3RW","NG22 9PL","S80 2BW","SG1 1LA","SP10 2SJ","SG4 9TS","EX39 3QU","DL1 2BJ","M22 4QN","WA10 2EF","PO14 1TT","HU17 9EW","PR25 2TE","ME5 9SE","LL77 7DW","BT11 9AP","NN4 8ER","SA4 4NZ","SS14 3AS","PR2 9NP","DN15 6UD","null","B69 4PU","SA6 8PS","NE38 8QA","DT4 8JQ","S70 5AU","DN12 4XX","PL31 2LW","CT10 2AL","CA14 1NQ","RM9 6SJ","BN15 8AG","null","NE5 2LD","S63 0JZ","LN6 8JY","RM1 3EE","PL25 4AD","RG30 4EL","ST6 6AT","N15 4QD","CT16 2QH","S12 2AB","HU10 6RJ","LL57 1LJ","YO32 9LF","CF83 3AD","CF23 8NL","DA11 0DQ","PE1 1ET","NG5 7ED","OL2 5HX","CM9 4NW","NE24 5TS","NE15 8SJ","CV37 0HZ","S6 3TD","S71 4BP","WN2 2QA","LE4 8GQ","OL9 0JE","DN6 8DN","DL14 7NA","SO14 7EG","BS30 7DY","L10 3LN","M33 3NA","NE4 8BE","LL18 5EQ","BD18 3RY","BT40 1FD","DN22 6EN","DY5 1QL","BT30 6AJ","SS3 8DA","TA6 3TA","LL31 9LU","RH11 7AH","WA5 8UH","CV3 4AR","SS14 1AE",
//                "null","TS18 2DS","LS27 0BN","SR4 9AS","NG24 1XG","GL2 2SN","BB2 3AG","BB4 8EL","LN2 4SX","B76 1XL","S40 1TB","BS3 4JY","LA4 5QW","TS3 0DY","TD15 2DS","S73 0TB","CV2 2BL","BS34 5TL","M15 5AS","SS6 9JR","M30 7JA","LS4 2EZ","CH41 6ED","PE37 7AU","TS12 2LQ","BN2 5UT","WS10 8UZ","B10 0TZ","BT63 5AQ","NE4 6QA","CM20 1AN","OX33 1YZ","TF2 7RX","NE35 9AR","FY4 4QH","SR2 0AB","null","CH4 0DP","M18 7JH","GU22 8BD","NG16 4LH","S2 4DR","LS11 8AG","LS14 6UF","S6 1TA","TA21 8RQ","S35 2UW","BS34 7JL","S63 7PX","WS11 8UF","TS23 2LF","S13 9BM","B68 0PH","WF1 1QQ","SE1 5AS","BL3 4EB","DL1 4PF","ST5 0AY","CH4 8RH","BT15 3PR","SP8 4QA","WA7 2PY","PR7 1EX","HU7 3HS","LS16 7RY","CH6 5GB","SR2 9TT","WV10 0QV","E6 5JP","BN1 8AS","SY3 7ET","CH49 5PD","BS20 7XN","DN4 7PD","SA4 4BZ","DN2 4PE","S60 1TG","CA3 0JQ","B42 1AB","B42 1AA","TQ12 1TQ","SE15 4NB","PO1 1SL","SA15 1SH","NG18 5LG","BH8 9UN","NE31 1AA","BD4 7SR","BT78 1QZ","TQ12 1TG","BT20 4AS","null","TA9 3BX","DE24 3DS","LS20 9NE","RM20 3LP","E10 5NH","NP23 4SL","HD5 9AD","BB3 1BE","NR30 1SF","OL8 4JZ","NE63 9XG","AL10 0JW","YO17 9RD","M27 4ET","CM8 2HJ","BD12 9JQ","S81 7AZ","HU3 1TY","ST16 3TA","null","WF6 2AL","WS11 1LH","OL4 2RB","DN17 2XF","CF31 3AG","BT23 4EU","BT52 1QN","BT16 1RN","LE12 9QE","CV11 4FL","BN12 6PN","PE21 8EQ","GL1 1DS","BT80 8TL","WN7 1QP","TR10 9LY","SA72 6DA","BB12 0EQ","SA9 2DE","BT41 4LL","BA11 5LA","SN25 4BG","MK1 1QB","CF11 0JR","GL51 6PN","RG22 4DH","NR6 5DT","KT2 6QL","CF44 0AH","LA14 5UG","RG6 5TT","DA9 9BT","CH62 3QP","NW10 7LW","LE10 1SS","TW3 1JT","BH8 8DL","CW1 2PT","IP1 5PD","GU14 7LT","S72 7LT","CF34 9UN","DN32 9DL","OL16 5AF","DH9 0NB","WS3 3JR","CT1 1DG","BT39 9BB","CF14 7EW","PE30 3UG","WS2 8LR","YO43 3AH","SW11 1JG","LA9 7JA","CT20 1AU","NG19 0HA","TA1 2AN","WN3 6XA","IP33 3SP","NN2 7BA","ME19 4SZ","IP14 1DE","NP44 1YH","CF72 8RP","LE2 4AH","SK14 1BD","WN5 0XA","DY1 1QS","ST5 0AP","ST4 2HE","NP12 0NT","HU11 4AL","BR8 7UN","SS14 1JH","MK43 9AB","BT74 6JG","CF11 8EG","BD21 3ER","LU5 4JD","WN3 6BB","SO30 3DQ","HA2 8JN","NP20 2BE","NR33 0PX","TW7 7DY","WN5 0UW","KT20 5NZ","WA14 5GR","CF48 2YF","DY4 7HW","BS23 3UZ","SO40 3ZA","B63 4AB","SW15 3DT","E14 3BT","IG11 8DJ","N14 5PW","NG31 6NZ","OL12 6XT","HU4 6SH","NP10 8XL","TW13 4BB","HU9 2AH","BN23 6JH","HP21 8BD","DA6 7BN","S65 3SW","DY5 1SY","NP19 4QQ","SO53 3YJ","EN1 1TW","B43 7HA","CR4 3HG","NN17 5DT","NE38 7NF","DA17 6DF","G67 1JW","null","KA22 8BZ","FK10 1LB","KY11 4LR","KY11 4LP","KY1 3NG","FK10 3SD","ML2 9QS","DD4 7XE","KA12 8EH","DD2 4WB","KA8 9BF","DD8 2AE","EH42 1BF","DD11 2NQ","IV2 6BZ","G23 5PZ","ML5 3BU","KA12 8AU","KY11 3AY","FK1 1JT","AB12 4XP","FK5 3BF","FK3 8TY","EH54 6NB","G31 4EB","KA26 9HF","G82 1RD","AB22 8WQ","IV19 1GZ","DD3 0SZ","G77 6EY","EH48 3LP","KY7 5QA","FK3 8ZG","G51 3HR","G81 2RZ","KA18 1LG","KA1 3XB","G33 1AD","KY11 9YX","G61 2TX","null","AB21 7NG","ML1 1YP","AB54 8SX","AB42 3JY","PH1 5AP","G42 0AE","AB10 7QA","IV30 6YQ","AB24 5EZ","TD1 2AG","PA1 2AB");
//        
//        int listSize2 = postcodes2.size();
//        
//        int mapSize2 = new HashSet(postcodes2).size() - 1;
//        
//        for (String postcode : postcodes2) {
//            if (postcode.equals("null")) {
//                mapSize2++;
//            }
//        }
//        
//        System.out.println("New list: " + listSize2);
//        System.out.println("New map: " + mapSize2);
//        System.out.println("New difference: " + (listSize2 - mapSize2));
//        System.out.println();
//        
//        System.out.println("Additions:");
//        for (String postcode : new HashSet<String>(postcodes2)) {
//            if (!postcodes.contains(postcode)) {
//                System.out.println(postcode);
//            }
//        }
//        
//        System.out.println();
//        System.out.println("Deletions:");
//        for (String postcode : new HashSet<String>(postcodes)) {
//            if (!postcodes2.contains(postcode)) {
//                System.out.println(postcode);
//            }
//        }
//    }
}
