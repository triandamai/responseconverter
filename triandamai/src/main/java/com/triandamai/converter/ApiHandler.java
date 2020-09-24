package com.triandamai.converter;

import android.content.Context;
import android.util.Log;

/*
 * @method Cek(code,tag)
 * */
class ApiHandler {
    private static final String TAG = "PANDA ::";

    public static boolean cek(int code) {
            if (code == 200 || code == 201) {
                return true;
            } else if (code == 400) {

                return false;
            } else if (code == 401) {

                return false;
            } else if (code == 404) {

                return false;
            } else if (code == 500) {

                return false;
            } else {

                return false;
            }
    }


        /*
    * String responseX = "";
    * try {
            responseX = response.errorBody().string();
            longLog(responseX);
          } catch (IOException e) {
            e.printStackTrace();
          }
    * */

    public static void longLog(String str) {
        if (str.length() > 4000) {
            Log.e("BookingPresenter", str.substring(0, 4000));
            longLog(str.substring(4000));
        } else
            Log.e("BookingPresenter", str);
    }
}
