package com.example.qr_code_generatorscanner;

import android.content.Context;

public class _Macula_Coordinates {

    private int[] coordinate=new int[2];
    private Context context;

    public _Macula_Coordinates(Context context) {
        this.context=context;
    }

    public int[] getCoordinates (int pointNumber) {

        int loc_1_degree = 75;
        int loc_3_degree = 225;

        switch (pointNumber) {
            case 0:
                coordinate[0] = 2000;
                coordinate[1] = 2000;
                break;
            case 1:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_3_degree;
                break;

            case 2:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 3:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 4:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_3_degree;
                break;

            case 5:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 6:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 7:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 8:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 9:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 10:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 11:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 12:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 13:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 14:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 15:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 16:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_3_degree;
                break;
        }
            return coordinate;
    }
}