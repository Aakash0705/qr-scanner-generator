package com.example.qr_code_generatorscanner;

import android.content.Context;

public class _10_2_Coordinates {

    private int loc_1_degree = 55;
    private int loc_3_degree = 165;
    private int loc_5_degree = 275;
    private int loc_7_degree = 385;
    private int loc_9_degree = 495;


    private int[] coordinate = new int[2];
    private Context context;

    public _10_2_Coordinates(Context context) {
        this.context = context;
    }


    public int[] getCoordinates(int pointNumber) {

        switch (pointNumber) {
            case 0:
                coordinate[0] = 2000;
                coordinate[1] = 2000;
                break;

            case 1:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_9_degree;
                break;
            case 2:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_9_degree;
                break;


            case 3:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 4:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_7_degree;
                break;

            case 5:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 6:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 7:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 8:
                coordinate[0] = loc_5_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 9:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 10:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = -loc_5_degree;
                break;

            case 11:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 12:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 13:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 14:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 15:
                coordinate[0] = loc_5_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 16:
                coordinate[0] = loc_7_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 17:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 18:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = -loc_3_degree;
                break;

            case 19:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 20:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 21:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 22:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 23:
                coordinate[0] = loc_5_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 24:
                coordinate[0] = loc_7_degree;
                coordinate[1] = -loc_3_degree;
                break;

            case 25:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 26:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = -loc_1_degree;
                break;

            case 27:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 28:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 29:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = -loc_1_degree;
                break;

            case 30:
                coordinate[0] = loc_1_degree;
                coordinate[1] = -loc_1_degree;
                break;

            case 31:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 32:
                coordinate[0] = loc_5_degree;
                coordinate[1] = -loc_1_degree;
                break;
            case 33:
                coordinate[0] = loc_7_degree;
                coordinate[1] = -loc_1_degree;
                break;

            case 34:
                coordinate[0] = loc_9_degree;
                coordinate[1] = -loc_1_degree;
                break;

            case 35:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 36:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 37:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 38:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 39:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 40:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 41:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 42:
                coordinate[0] = loc_5_degree;
                coordinate[1] = loc_1_degree;
                break;
            case 43:
                coordinate[0] = loc_7_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 44:
                coordinate[0] = loc_9_degree;
                coordinate[1] = loc_1_degree;
                break;

            case 45:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 46:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = loc_3_degree;
                break;

            case 47:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 48:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 49:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 50:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 51:
                coordinate[0] = loc_5_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 52:
                coordinate[0] = loc_7_degree;
                coordinate[1] = loc_3_degree;
                break;

            case 53:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 54:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = loc_5_degree;
                break;

            case 55:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 56:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 57:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 58:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 59:
                coordinate[0] = loc_5_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 60:
                coordinate[0] = loc_7_degree;
                coordinate[1] = loc_5_degree;
                break;

            case 61:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 62:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_7_degree;
                break;

            case 63:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 64:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 65:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 66:
                coordinate[0] = loc_5_degree;
                coordinate[1] = loc_7_degree;
                break;

            case 67:
                coordinate[0] = -loc_1_degree;
                coordinate[1] = loc_9_degree;
                break;
            case 68:
                coordinate[0] = loc_1_degree;
                coordinate[1] = loc_9_degree;
                break;

        }
        return coordinate;
    }


    public int[] getCoordinates_smallRectangles (int pointNumber) {
        loc_1_degree = 55;
        loc_3_degree = 165;
        loc_5_degree = 275;
        loc_7_degree = 385;
        loc_9_degree = 495;

        switch (pointNumber) {
            case 0:
                coordinate[0] = 2000;
                coordinate[1] = 2000;
                break;

            case 1:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = -loc_9_degree;
                break;
            case 2:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = -loc_9_degree;
                break;
            case 3:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = -loc_7_degree;
                break;
            case 4:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = -loc_5_degree;
                break;

            case 5:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 6:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 7:
                coordinate[0] = -loc_9_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 8:
                coordinate[0] = -loc_7_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 9:
                coordinate[0] = -loc_5_degree;
                coordinate[1] = loc_9_degree;
                break;
            case 10:
                coordinate[0] = -loc_3_degree;
                coordinate[1] = loc_9_degree;
                break;

            case 11:
                coordinate[0] = loc_3_degree;
                coordinate[1] = loc_9_degree;
                break;
            case 12:
                coordinate[0] = loc_5_degree;
                coordinate[1] = loc_9_degree;
                break;
            case 13:
                coordinate[0] = loc_7_degree;
                coordinate[1] = loc_7_degree;
                break;
            case 14:
                coordinate[0] = loc_9_degree;
                coordinate[1] = loc_5_degree;
                break;
            case 15:
                coordinate[0] = loc_9_degree;
                coordinate[1] = loc_3_degree;
                break;
            case 16:
                coordinate[0] = loc_9_degree;
                coordinate[1] = -loc_3_degree;
                break;
            case 17:
                coordinate[0] = loc_9_degree;
                coordinate[1] = -loc_5_degree;
                break;
            case 18:
                coordinate[0] = loc_7_degree;
                coordinate[1] = -loc_7_degree;
                break;

            case 19:
                coordinate[0] = loc_5_degree;
                coordinate[1] = -loc_9_degree;
                break;
            case 20:
                coordinate[0] = loc_3_degree;
                coordinate[1] = -loc_9_degree;
                break;
        }
        return coordinate;
    }
}