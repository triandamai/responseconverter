package com.triandamai.converter;

import android.content.Context;
import android.util.Log;

/*
 * @method Cek(code,tag)
 * */
class ApiHandler {
    private static final String TAG = "PANDA ::";
    public static final int
            RES_OK = 200,
            RES_CREATED = 201,
            RES_ACCEPTED = 202,
            RS_BAD_REQUST = 400,
            RES_UNAUTHORIZED= 401,
            RES_FORBIDDEN = 403,
            RES_NOTFOUND= 404,
            RES_NOTACCEPTABLE = 406,
            RES_METHODNOTALLOWED = 405,
            RES_INTERNALSERVERERROR = 500;

    public static boolean cek(int code) {
            if (code == RES_OK) {
                return true;
            } else if (code == RES_CREATED) {
                return true;
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
