package com.example.qr_code_generatorscanner;

import android.graphics.Color;

//import com.example.qr_code_generatorscanner.ConnectionToDevice.ChatController;

import java.util.ArrayList;

public class TemporaryVariables {
    private static String softwareVersion="4.4.6.0";
    private static String hardwareVersion="-";

    private static String reportColor = "Color";
    private static String eyeTracking = "On";
    private static String testMessage = "";
    //private static ChatController chatController;

    public static String patientAge= "";
    public static String lookedAway = "";
    public static String fixationLoss="";
    public static String falsePositive="";
    public static String falseNegative ="";
    public static String testDuration="";

    public static String selectedMrn = "";

    public static Boolean isNewPatient=false;

    public static String ivaMessage = "";


    static ArrayList<ArrayList<Integer> > contrastShown = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer> > contrastClicked = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer> > contrastNotClicked = new ArrayList<ArrayList<Integer>>();

    public static String getFalseNegative() {
        return falseNegative;
    }

    public static void setFalseNegative(String falseNegative) {
        TemporaryVariables.falseNegative = falseNegative;
    }

    public static ArrayList<ArrayList<Integer>> getContrastShown() {
        return contrastShown;
    }

    public static void setContrastShown(ArrayList<ArrayList<Integer>> contrastShown) {
        TemporaryVariables.contrastShown = contrastShown;
    }

    public static ArrayList<ArrayList<Integer>> getContrastClicked() {
        return contrastClicked;
    }

    public static void setContrastClicked(ArrayList<ArrayList<Integer>> contrastClicked) {
        TemporaryVariables.contrastClicked = contrastClicked;
    }

    public static ArrayList<ArrayList<Integer>> getContrastNotClicked() {
        return contrastNotClicked;
    }

    public static void setContrastNotClicked(ArrayList<ArrayList<Integer>> contrastNotClicked) {
        TemporaryVariables.contrastNotClicked = contrastNotClicked;
    }

    public static Boolean getIsNewPatient() {
        return isNewPatient;
    }

    public static String getPatientAge() {
        return patientAge;
    }

    public static void setPatientAge(String patientAge) {
        TemporaryVariables.patientAge = patientAge;
    }

    public static void setIsNewPatient(Boolean isNewPatient) {
        TemporaryVariables.isNewPatient = isNewPatient;
    }

    public static String getIvaMessage() {
        return ivaMessage;
    }

    public static void setIvaMessage(String ivaMessage) {
        TemporaryVariables.ivaMessage = ivaMessage;
    }
//
//    public static ChatController getChatController() {
//        return chatController;
//    }
//    public static void setChatController(ChatController chatController) {
//        TemporaryVariables.chatController = chatController;
//    }

    public static String getSoftwareVersion() {return softwareVersion;
    }

    public static void setSoftwareVersion(String softwareVersion) {
        TemporaryVariables.softwareVersion = softwareVersion;
    }

    public static String getHardwareVersion() {
        return hardwareVersion;
    }

    public static void setHardwareVersion(String hardwareVersion) {
        TemporaryVariables.hardwareVersion = hardwareVersion;
    }
    public static String getReportColor() {
        return reportColor;
    }

    public static void setReportColor(String reportColor) {
        TemporaryVariables.reportColor = reportColor;
    }

    public static String getEyeTracking() {
        return eyeTracking;
    }

    public static void setEyeTracking(String eyeTracking) {
        TemporaryVariables.eyeTracking = eyeTracking;
    }

    public static String getTestMessage() {
        return testMessage;
    }

    public static void setTestMessage(String testMessage) {
        TemporaryVariables.testMessage = testMessage;
    }

    public static String getLookedAway() {
        return lookedAway;
    }

    public static void setLookedAway(String lookedAway) {
        TemporaryVariables.lookedAway = lookedAway;
    }

    public static String getFixationLoss() {
        return fixationLoss;
    }

    public static void setFixationLoss(String fixationLoss) {
        TemporaryVariables.fixationLoss = fixationLoss;
    }

    public static String getFalsePositive() {
        return falsePositive;
    }

    public static void setFalsePositive(String falsePositive) {
        TemporaryVariables.falsePositive = falsePositive;
    }

    public static String getTestDuration() {
        return testDuration;
    }

    public static void setTestDuration(String testDuration) {
        TemporaryVariables.testDuration = testDuration;
    }

    public static String getSelectedMrn() {
        return selectedMrn;
    }

    public static void setSelectedMrn(String selectedMrn) {
        TemporaryVariables.selectedMrn = selectedMrn;
    }

    public static int convertContrastToDb(int intensity_contrast){
        int intensity_db = 0;

        // TODO 20-02-2021  LOGIC NEEDS TO BE CHANGES BASED ON THE CONVERSION FORMULA


        if (intensity_contrast == 130 || intensity_contrast == 131 || intensity_contrast == 132)
            intensity_db = 36;

        else if (intensity_contrast == 133 || intensity_contrast == 134)
            intensity_db = 35;

        else if(intensity_contrast >= 135 && intensity_contrast <= 137)
            intensity_db = 34;

        else if(intensity_contrast == 138)
            intensity_db = 33;

        else if(intensity_contrast >= 139 && intensity_contrast <= 140)
            intensity_db = 32;

        else if(intensity_contrast >= 141 && intensity_contrast <= 143)
            intensity_db = 31;

        else if(intensity_contrast >= 144 && intensity_contrast <= 149)
            intensity_db = 30;

        else if(intensity_contrast >= 150 && intensity_contrast <= 153)
            intensity_db = 29;

        else if(intensity_contrast >= 154 && intensity_contrast <= 155)
            intensity_db = 28;

        else if(intensity_contrast >= 156 && intensity_contrast <= 158)
            intensity_db = 27;

        else if(intensity_contrast >= 159 && intensity_contrast <= 161)
            intensity_db = 26;

        else if(intensity_contrast >= 162 && intensity_contrast <= 164)
            intensity_db = 25;

        else if(intensity_contrast >= 165 && intensity_contrast <= 169)
            intensity_db = 24;

        else if(intensity_contrast >= 170 && intensity_contrast <= 172)
            intensity_db = 23;

        else if(intensity_contrast >= 173 && intensity_contrast <= 176)
            intensity_db = 22;

        else if(intensity_contrast >= 177 && intensity_contrast <= 188)
            intensity_db = 21;

        else if(intensity_contrast >= 189 && intensity_contrast <= 191)
            intensity_db = 20;

        else if(intensity_contrast >= 192 && intensity_contrast <= 194)
            intensity_db = 19;

        else if(intensity_contrast >= 195 && intensity_contrast <= 201)
            intensity_db = 18;

        else if(intensity_contrast >= 202 && intensity_contrast <= 206)
            intensity_db = 17;

        else if(intensity_contrast >= 207 && intensity_contrast <= 229)
            intensity_db = 16;

        else if(intensity_contrast >= 230 && intensity_contrast <= 244)
            intensity_db = 15;

        else if(intensity_contrast == 245)
            intensity_db = 14;

        else if(intensity_contrast == 246)
            intensity_db = 13;

        else if(intensity_contrast == 247)
            intensity_db = 11;

        else if(intensity_contrast == 248)
            intensity_db = 10;

        else if(intensity_contrast == 249)
            intensity_db = 9;

        else if(intensity_contrast == 250)
            intensity_db = 7;

        else if(intensity_contrast == 251)
            intensity_db = 6;

        else if(intensity_contrast == 252)
            intensity_db = 4;

        else if(intensity_contrast == 253)
            intensity_db = 3;

        else if(intensity_contrast == 254)
            intensity_db = 1;

        else if(intensity_contrast == 255)
            intensity_db = 0;

        return intensity_db;
    }

    public static int[] convertContrastToDb(int[] intensity_contrast, int n){
        int[] intensity_db = new int[n];

        // TODO 20-02-2021  LOGIC NEEDS TO BE CHANGES BASED ON THE CONVERSION FORMULA

        for(int i=0;i<n;i++){

            if (intensity_contrast[i] == 130 || intensity_contrast[i] == 131 || intensity_contrast[i] == 132)
                intensity_db[i] = 36;

            else if (intensity_contrast[i] == 133 || intensity_contrast[i] == 134)
                intensity_db[i] = 35;

            else if(intensity_contrast[i] >= 135 && intensity_contrast[i] <= 137)
                intensity_db[i] = 34;

            else if(intensity_contrast[i] == 138)
                intensity_db[i] = 33;

            else if(intensity_contrast[i] >= 139 && intensity_contrast[i] <= 140)
                intensity_db[i] = 32;

            else if(intensity_contrast[i] >= 141 && intensity_contrast[i] <= 143)
                intensity_db[i] = 31;

            else if(intensity_contrast[i] >= 144 && intensity_contrast[i] <= 149)
                intensity_db[i] = 30;

            else if(intensity_contrast[i] >= 150 && intensity_contrast[i] <= 153)
                intensity_db[i] = 29;

            else if(intensity_contrast[i] >= 154 && intensity_contrast[i] <= 155)
                intensity_db[i] = 28;

            else if(intensity_contrast[i] >= 156 && intensity_contrast[i] <= 158)
                intensity_db[i] = 27;

            else if(intensity_contrast[i] >= 159 && intensity_contrast[i] <= 161)
                intensity_db[i] = 26;

            else if(intensity_contrast[i] >= 162 && intensity_contrast[i] <= 164)
                intensity_db[i] = 25;

            else if(intensity_contrast[i] >= 165 && intensity_contrast[i] <= 169)
                intensity_db[i] = 24;

            else if(intensity_contrast[i] >= 170 && intensity_contrast[i] <= 172)
                intensity_db[i] = 23;

            else if(intensity_contrast[i] >= 173 && intensity_contrast[i] <= 176)
                intensity_db[i] = 22;

            else if(intensity_contrast[i] >= 177 && intensity_contrast[i] <= 188)
                intensity_db[i] = 21;

            else if(intensity_contrast[i] >= 189 && intensity_contrast[i] <= 191)
                intensity_db[i] = 20;

            else if(intensity_contrast[i] >= 192 && intensity_contrast[i] <= 194)
                intensity_db[i] = 19;

            else if(intensity_contrast[i] >= 195 && intensity_contrast[i] <= 201)
                intensity_db[i] = 18;

            else if(intensity_contrast[i] >= 202 && intensity_contrast[i] <= 206)
                intensity_db[i] = 17;

            else if(intensity_contrast[i] >= 207 && intensity_contrast[i] <= 229)
                intensity_db[i] = 16;

            else if(intensity_contrast[i] >= 230 && intensity_contrast[i] <= 244)
                intensity_db[i] = 15;

            else if(intensity_contrast[i] == 245)
                intensity_db[i] = 14;

            else if(intensity_contrast[i] == 246)
                intensity_db[i] = 13;

            else if(intensity_contrast[i] == 247)
                intensity_db[i] = 11;

            else if(intensity_contrast[i] == 248)
                intensity_db[i] = 10;

            else if(intensity_contrast[i] == 249)
                intensity_db[i] = 9;

            else if(intensity_contrast[i] == 250)
                intensity_db[i] = 7;

            else if(intensity_contrast[i] == 251)
                intensity_db[i] = 6;

            else if(intensity_contrast[i] == 252)
                intensity_db[i] = 4;

            else if(intensity_contrast[i] == 253)
                intensity_db[i] = 3;

            else if(intensity_contrast[i] == 254)
                intensity_db[i] = 1;

            else if(intensity_contrast[i] == 255)
                intensity_db[i] = 0;

        }
        return intensity_db;
    }
    public static int getColorCode(long db_value){
        int color= Color.rgb(250,250,255);

        if(db_value>=36 && db_value<=40){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(255,251,239);
            else
                color = Color.rgb(248,248,248);
        }

        else if (db_value>=31 && db_value<=35){
            if (TemporaryVariables.getReportColor().equals("Color")) {
                color = Color.rgb(253, 246, 217);     //color
            }
            else {
                color = Color.rgb(239, 239, 239);
            }

        }

        else if (db_value>=26 && db_value<=30){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(248,235,178);     //color
            else
                color = Color.rgb(220,220,220);

        }

        else if (db_value>=21 && db_value<=25){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(244,228,152);     //color
            else
                color = Color.rgb(208,208,208);
        }

        else if (db_value>=16&& db_value<=20){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(141,195,97);     //color
            else
                color = Color.rgb(144,144,144);
        }

        else if (db_value>=11 && db_value<=15){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(180,75,59);     //color
            else
                color = Color.rgb(105,105,105);
        }

        else if (db_value>=6 && db_value<=10){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(140,31,53); //color
            else
                color = Color.rgb(76,76,76);
        }

        else if (db_value>=1 && db_value<=5){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(26,19,49);     //color
            else
                color = Color.rgb(31,31,31);
        }

        else if (db_value==0){
            if (TemporaryVariables.getReportColor().equals("Color"))
                color = Color.rgb(25,25,25);     //color
            else
                color = Color.rgb(25,25,25);
        }


        return color;
    }
    public static double calculatePSD(int numArray[], int n)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = n;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/n;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/n);
    }

}
