package edu.neu.madcourse.pettin.Classes;

import java.util.ArrayList;

public class GroupChat {

    // the document geneerated id
    private String id;
    // name of the group
    private String group;
    // members of the group
    private ArrayList<User> listOfGroupMembers;
    // list of messages in the group chat
    private ArrayList<GroupMessage> listOfMessages;

    // empty constructor for deserializing data
    public GroupChat() {
    }

    public GroupChat(String id, String group, ArrayList<User> listOfGroupMembers, ArrayList<GroupMessage> listOfMessages) {
        this.id = id;
        this.group = group;
        this.listOfGroupMembers = listOfGroupMembers;
        this.listOfMessages = listOfMessages;
    }

    public GroupChat(String group, ArrayList<User> listOfGroupMembers, ArrayList<GroupMessage> listOfMessages) {
        this.group = group;
        this.listOfGroupMembers = new ArrayList<>();
        this.listOfMessages = new ArrayList<>();
    }

    public GroupChat(String group, ArrayList<User> listOfGroupMembers) {
        this.group = group;
        this.listOfGroupMembers = listOfGroupMembers;
        this.listOfMessages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<User> getListOfGroupMembers() {
        return listOfGroupMembers;
    }

    public void setListOfGroupMembers(ArrayList<User> listOfGroupMembers) {
        this.listOfGroupMembers = listOfGroupMembers;
    }

    public ArrayList<GroupMessage> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(ArrayList<GroupMessage> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }
}