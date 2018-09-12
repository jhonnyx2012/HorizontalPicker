package com.github.jhonnyx2012.horizontalpicker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by jhonn on 28/02/2017.
 */
public class Day {
  private LocalDate date;
  private boolean selected;
  private String monthPattern = "MMMM YYYY";

  public Day(LocalDate date) {
    this.date = date;
  }

  public String getDay() {
    return String.valueOf(date.getDayOfMonth());
  }

  public String getWeekDay() {
    return DateTimeFormatter.ofPattern("EEE").format(date).toUpperCase();
  }

  public String getMonth() {
    return getMonth("");
  }

  public String getMonth(String pattern) {
    if (!pattern.isEmpty())
      this.monthPattern = pattern;
    return DateTimeFormatter.ofPattern(monthPattern).format(date);
  }

  public LocalDate getDate() {
    return date;
  }

  public boolean isToday() {
    LocalDate today = LocalDate.now();
    return getDate().isEqual(today);
  }

  public static class Placeholder extends Day {

    public Placeholder() {
      super(null);
    }
  }

}
