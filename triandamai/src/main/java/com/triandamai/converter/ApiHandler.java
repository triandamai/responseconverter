package com.triandamai.converter;

import android.content.Context;
import android.util.Log;

/*
 * @method Cek(code,tag)
 * */
class ApiHandler {
    private static final String TAG = "PANDA ::";

    public static boolean Cek(int code) {
        try {
            int res = code;
            if (res == 200 || res == 201) {
                return true;
            } else if (res == 400) {

                return false;
            } else if (res == 401) {

                return false;
            } else if (res == 404) {

                return false;
            } else if (res == 500) {

                return false;
            } else {

                return false;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static boolean Cek(String code) {

        try {
            int res = Integer.parseInt(code);
            if (res == 200 || res == 201) {

                return true;
            } else if (res == 400) {

                return false;
            } else if (res == 401) {

                return false;
            } else if (res == 404) {

                return false;
            } else if (res == 500) {

                return false;
            } else {

                return false;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static boolean Cek(int code, String TAG) {
        try {
            int res = code;
            if (res == 200 || res == 201) {
                return true;
            } else if (res == 400) {

                return false;
            } else if (res == 401) {

                return false;
            } else if (res == 404) {

                return false;
            } else if (res == 500) {

                return false;
            } else {

                return false;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static boolean Cek(String code, String TAG) {

        try {
            int res = Integer.parseInt(code);
            if (res == 200 || res == 201) {

                return true;
            } else if (res == 400) {

                return false;
            } else if (res == 401) {

                return false;
            } else if (res == 404) {

                return false;
            } else if (res == 500) {

                return false;
            } else {

                return false;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static boolean cekresponse(int res, Context context, String pesan) {
        try {
            if (res == 200 || res == 201) {

                return true;
            } else if (res == 400) {

                return false;
            } else if (res == 401) {

                return false;
            } else if (res == 404) {

                return false;
            } else if (res == 500) {

                return false;
            } else {

                return false;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
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
