package com.example.myfirstapp;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {
    private String dateString;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    Date birthDate = new Date();
    Date todayDate = new Date();

    public int yearBorn;
    public int todayYear;

    GregorianCalendar birthCal = new GregorianCalendar();
    GregorianCalendar todayCal = new GregorianCalendar();

    private GregorianCalendar editedCal;
    private int daysUntil;

    public DateConverter(String dateString) {
       this.dateString = dateString;
       birthCal.setTimeZone(TimeZone.getDefault());
       todayCal.setTimeZone(TimeZone.getDefault());
       convertDateToCalendar();
       daysInBetween();
    }

    public void convertDateToCalendar() {
        try {
            birthDate = dateFormatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        birthCal.setTime(birthDate);
        todayCal.setTime(todayDate);
        yearBorn = birthCal.get(Calendar.YEAR);
        todayYear = todayCal.get(Calendar.YEAR);
    }

    public void daysInBetween() {
        GregorianCalendar editedCal = new GregorianCalendar();
        editedCal.set(1970, 1, 1, 0, 0, 0);
        editedCal.setTimeZone(TimeZone.getDefault());
        try {
            Date savedDate = dateFormatter.parse(birthCal.get(Calendar.MONTH) + "/" + birthCal.get(Calendar.DAY_OF_MONTH) + "/" + todayCal.get(Calendar.YEAR));
            editedCal.setTime(savedDate);
            if(editedCal.before(todayCal)) {
                savedDate = dateFormatter.parse((birthCal.get(Calendar.MONTH)) + "/" + birthCal.get(Calendar.DAY_OF_MONTH) + "/" + (todayCal.get(Calendar.YEAR) + 1));
                editedCal.setTime(savedDate);
            }
            this.editedCal = editedCal;
            LocalDate ELdate = new LocalDate(editedCal.get(Calendar.YEAR), (editedCal.get(Calendar.MONTH) + 1), editedCal.get(Calendar.DAY_OF_MONTH));
            LocalDate TLdate = new LocalDate(todayCal.get(Calendar.YEAR), (todayCal.get(Calendar.MONTH)), todayCal.get(Calendar.DAY_OF_MONTH));
            int daysBetween = Days.daysBetween(TLdate, ELdate).getDays();
            this.daysUntil = daysBetween - 1;

        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

    public int getDaysUntil() {
        if(daysUntil >= 365) {
            daysUntil = daysUntil - 365;
            return daysUntil;
        } else {
            return daysUntil;
        }
    }

    public int getAge() {
        return todayYear - yearBorn;
    }
}
