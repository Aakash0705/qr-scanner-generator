package com.example.qr_code_generatorscanner.NormativeData;

public class _30_2_NormativeData {

    public int[] getNormativeData(int age, String testEye){
        int[] B = new int[77];
        int[] NORMATIVE_DATA = new int[77];

        if (testEye.equals("Right")) {
            B = new int[]{0,28,28,28,28,30,30,30,30,30,30,30,31,31,31,31,31,31,30,28,30,31,32,32,32,32,31,30,28,28,29,31,32,34,34,32,31,29,28,27,29,30,32,34,34,32,30,29,27,27,28,30,32,32,32,32,30,28,27,28,30,30,30,30,30,30,28,28,28,28,28,28,28,27,27,27,27};
        }
        else if (testEye.equals("Left")){
            B = new int[]{0,28,28,28,28,30,30,30,30,30,30,30,31,31,31,31,31,31,30,28,30,31,32,32,32,32,31,30,28,28,29,31,32,34,34,32,31,29,28,27,29,30,32,34,34,32,30,29,27,27,28,30,32,32,32,32,30,28,27,28,30,30,30,30,30,30,28,28,28,28,28,28,28,27,27,27,27};
        }

        for(int i=0;i<77;i++){
            NORMATIVE_DATA[i] =(int) Math.round(B[i] - 0.1 * (age - 20));
        }
        return NORMATIVE_DATA;
    }
}
