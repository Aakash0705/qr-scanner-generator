package com.example.qr_code_generatorscanner.NormativeData;

public class _10_2_NormativeData {

    public int[] getNormativeData(int age, String testEye){
        int[] B = new int[69];
        int[] NORMATIVE_DATA = new int[69];

        if (testEye.equals("Right")) {
            B = new int[]{0,32,32,33,33,33,33,33,33,33,34,34,34,34,34,34,33,33,34,34,34,34,34,33,32,33,34,34,35,35,34,34,33,32,32,33,34,34,35,35,34,34,33,32,33,34,34,34,34,34,34,33,33,34,34,34,34,34,34,33,33,33,33,33,33,33,32,32,32};
        }
        else if (testEye.equals("Left")){
            B = new int[]{0,32,32,33,33,33,33,33,33,33,34,34,34,34,34,34,33,33,34,34,34,34,34,33,32,33,34,34,35,35,34,34,33,32,32,33,34,34,35,35,34,34,33,32,33,34,34,34,34,34,34,33,33,34,34,34,34,34,34,33,33,33,33,33,33,33,32,32,32};
        }

        for(int i=0;i<69;i++){
            NORMATIVE_DATA[i] =(int) Math.round(B[i] - 0.1 * (age - 20));
        }
        return NORMATIVE_DATA;
    }
}
