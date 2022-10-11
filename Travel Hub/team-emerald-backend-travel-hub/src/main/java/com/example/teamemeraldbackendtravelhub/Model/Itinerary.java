package com.example.teamemeraldbackendtravelhub.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "itinerary" )
public class Itinerary {

  @Id
  private String id;
  @Field(value = "itinerary_date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime itineraryDate;
  @Field(value = "hotel")
  private String hotel;
  @Field(value = "activities_booked")
  private ArrayList<Activity> activities_booked = new ArrayList<>();
  @Field(value = "other_bookings")
  private ArrayList<String> other_bookings = new ArrayList<>();
  @Field(value = "user_id")
  private String userId;

  public Itinerary(@JsonProperty("itinerary_date") LocalDateTime itineraryDate, @JsonProperty("hotel") String hotel,
      @JsonProperty("activities_booked") ArrayList<Activity> activities_booked,
      @JsonProperty("other_bookings") ArrayList<String> other_bookings, @JsonProperty("user_id")
      String userId) {
    this.itineraryDate = itineraryDate;
    this.hotel = hotel;
    this.activities_booked = activities_booked;
    this.other_bookings = other_bookings;
    this.userId = userId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LocalDateTime getDate() {
    return this.itineraryDate;
  }

  public void setDate(LocalDateTime date) {
    this.itineraryDate = date;
  }

  public String getHotel() {
    return hotel;
  }

  public void setHotel(String hotel) {
    this.hotel = hotel;
  }

  public ArrayList<Activity> getActivities_booked() {
    return activities_booked;
  }

  public void setActivities_booked(ArrayList<Activity> activities_booked) {
    this.activities_booked = activities_booked;
  }

  public ArrayList<String> getOther_bookings() {
    return other_bookings;
  }

  public void setOther_bookings(ArrayList<String> other_bookings) {
    this.other_bookings = other_bookings;
  }
}