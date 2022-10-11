package com.example.teamemeraldbackendtravelhub.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trips" )
public class Trip {

  @Id
  private String id;
  @Field(value = "trip_name")
  private String tripName;
  @Field(value = "destination")
  private String destination;
  @Field(value = "start_date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private String startDate;
  @Field(value = "end_date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private String endDate;
  @Field(value = "collaborators")
  private ArrayList<String> collaborators = new ArrayList<>();
  @Field(value = "group_budget")
  private Float budget = 0.0f;
  @Field(value = "user_id")
  private String userId;
  @Field(value = "activities_booked")
  private ArrayList<Activity> activitiesBooked = new ArrayList<>();
  @Field(value = "other_bookings")
  private ArrayList<String> otherBookings = new ArrayList<>();

  public Trip() {
  }

  public Trip(@JsonProperty("trip_name") String tripName, @JsonProperty("destination")String destination,
      @JsonProperty("start_date") String startDate, @JsonProperty("end_date") String endDate,
      @JsonProperty("collaborators") ArrayList<String> collaborators, @JsonProperty("group_budget") Float budget,
      @JsonProperty("user_id") String userId, @JsonProperty("activities_booked")ArrayList<Activity> activitiesBooked,
      @JsonProperty("other_bookings")ArrayList<String> otherBookings) {
    this.tripName = tripName;
    this.destination = destination;
    this.startDate = startDate;
    this.endDate = endDate;
    this.collaborators = collaborators;
    this.budget = budget;
    this.userId = userId;
    this.activitiesBooked = activitiesBooked;
    this.otherBookings = otherBookings;
  }


  private LocalDate stringToLocalDateConverter(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    return LocalDate.parse(date, formatter);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTripName() {
    return tripName;
  }

  public void setTripName(String tripName) {
    this.tripName = tripName;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public ArrayList<String> getCollaborators() {
    return collaborators;
  }

  public void setCollaborators(ArrayList<String> collaborators) {
    this.collaborators = collaborators;
  }

  public Float getBudget() {
    return budget;
  }

  public void setBudget(Float budget) {
    this.budget = budget;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ArrayList<Activity> getActivitiesBooked() {
    return activitiesBooked;
  }

  public void setActivitiesBooked(
      ArrayList<Activity> activitiesBooked) {
    this.activitiesBooked = activitiesBooked;
  }

  public ArrayList<String> getOtherBookings() {
    return otherBookings;
  }

  public void setOtherBookings(ArrayList<String> otherBookings) {
    this.otherBookings = otherBookings;
  }

  public void addActivity(Activity activity) {
    this.activitiesBooked.add(activity);
  }

}
