package com.ftp.api.assets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    public static String getDateAndTime(long timestamp) {
        try {
            // Crear un objeto Date con el timestamp
            Date date = new Date(timestamp);

            // Formateador para mostrar la fecha y hora en formato de 12 horas (AM/PM)
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, hh:mm:ss a");
            sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City")); // Opcional: ajustar zona horaria

            // Retornar la fecha formateada
            return sdf.format(date);
        } catch (NumberFormatException e) {
            return "Error: El timestamp no es válido.";
        }
    }
}
