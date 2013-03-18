package org.puder.trs80;

import android.app.Application;
import android.content.Context;

public class TRS80Application extends Application {

    private static Context  context;
    private static Hardware hardware;
    private static Keyboard keyboard;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static void setHardware(Hardware theHardware) {
        hardware = theHardware;
    }

    public static Hardware getHardware() {
        return hardware;
    }

    public static void setKeyboard(Keyboard theKeyboard) {
        keyboard = theKeyboard;
    }

    public static Keyboard getKeyboard() {
        return keyboard;
    }
}
