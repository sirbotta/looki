/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.helper;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author simone
 */
public class DataTool {

    private DataTool() {
        throw new AssertionError();
    }

    public static String verboseTillDate(Date date) {
        Long now = new Date().getTime();
        Long to = date.getTime();
        if (to > now) {
            return verboseAMillis(to - now);
        } else {
            return date.toLocaleString();
        }

    }

    public static String verboseTillDate(Timestamp date) {
        Long now = new Date().getTime();
        Long to = date.getTime();
        if (to > now) {
            return verboseAMillis(to - now);
        } else {
            return date.toLocaleString();
        }

    }

    public static String verboseAMillis(Long milliseconds) {
        String d = "";

        Long seconds = (Long) (milliseconds / 1000) % 60;
        Long minutes = (Long) ((milliseconds / (1000 * 60)) % 60);
        Long hours = (Long) ((milliseconds / (1000 * 60 * 60)) % 24);
        Long days = (Long) (milliseconds / (1000 * 60 * 60 * 24));

        if (days != 0) {
            d += days + "d ";
        }

        if (hours != 0) {
            d += hours + "h ";
        }

        if (minutes != 0) {
            d += minutes + "m";
        }

        if (days == 0 && hours == 0 && minutes < 5) {
            d += " " + seconds + "s";
        }

        return d;
    }
}
