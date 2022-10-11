package com.project.quantifiedself.controller;

import com.project.quantifiedself.repositories.activityweb.ActivityCaloriesPerYear;
import com.project.quantifiedself.repositories.activityweb.ActivityCountPerYear;
import com.project.quantifiedself.repositories.activityweb.ActivityYear;
import com.project.quantifiedself.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that connects the view and controller.
 */
@Controller
public class ActivityControllerWeb {

    @Autowired
    private EntryService entryService;

    @GetMapping("/activity/count")
    public String showActivityCount(Model model) {
        List<ActivityCountPerYear> activitiesCount = entryService.findActivityCount();
        // returns a list of years
        List<String> uniqueYears =
                activitiesCount
                        .stream()
                        .map(ActivityCountPerYear::get_id)
                        .map(ActivityYear::getYear)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());
        System.out.println(uniqueYears.toString());
        Map<String, String> activityYearMap = new HashMap<>();
        // this will be the keys for the map
        activitiesCount
                .stream()
                .forEach(a->activityYearMap.put(a.get_id().getActivity(), a.get_id().getYear()));
        Map<Object, Map<Object, Map<Integer, List<ActivityCountPerYear>>>> map =
                activitiesCount
                        .stream()
                        .collect(Collectors.groupingBy(e -> e.get_id().getYear(), TreeMap::new,
                                Collectors.groupingBy(x -> x.get_id().getActivity(),
                                        Collectors.groupingBy(ActivityCountPerYear::getCount)))
                        );
        Map<List<Collection<String>>, Double> activityCountMap = new HashMap<>();
        activitiesCount.stream().forEach(a->activityCountMap.put(Arrays.asList(activityYearMap.keySet(),
                activityYearMap.values()),
                a.getCount() + activityCountMap.getOrDefault(a.get_id().getActivity(),
                        0.0)));
        List<String> uniqueActivity =
                activitiesCount.stream().map(ActivityCountPerYear::get_id).map(ActivityYear::getActivity)
                        .distinct().collect(Collectors.toList());
        System.out.println("###########################################################");
        System.out.println("V1" + map.keySet());
        System.out.println("V2" + map.values());
        System.out.println("V3" + activityCountMap.keySet());
        System.out.println("V4" + activityCountMap.values());
        System.out.println("###########################################################");
        model.addAttribute("activitiesCount", activitiesCount);
        model.addAttribute("uniqueYear", map.keySet());
        model.addAttribute("uniqueActivity", uniqueActivity);
        model.addAttribute("activityYear", activityCountMap.keySet());
        model.addAttribute("activityYearCount", activityCountMap.values());
        return "ActivityCount"; // "view"/webpage named "ActivityCount.html"
    }

    @GetMapping("/activity/calories")
    public String showActivityCalories(Model model) {
        List<ActivityCaloriesPerYear> activitiesCalories = entryService.findTotalCaloriesPerYear();
        Map<String, Double> activityCalorieMap = new HashMap<>();
        activitiesCalories.stream().forEach(a->activityCalorieMap.put(a.get_id().getActivity(),
                a.getCalories() + activityCalorieMap.getOrDefault(a.get_id().getActivity(),
                        0.0)));
        List<String> activity = new ArrayList<>();
        List<String> year = new ArrayList<>();
        List<Double> calorie = new ArrayList<>();
        List<String> uniqueActivity = new ArrayList<>();
        List<String> uniqueYear = new ArrayList<>();
        for (ActivityCaloriesPerYear activitiesCalorie : activitiesCalories) {
            activity.add(activitiesCalorie.get_id().getActivity());
            year.add(activitiesCalorie.get_id().getYear());
            calorie.add(activitiesCalorie.getCalories());
            if (!uniqueActivity.contains(activitiesCalorie.get_id().getActivity())){
                uniqueActivity.add(activitiesCalorie.get_id().getActivity());
            }
            if (!uniqueYear.contains(activitiesCalorie.get_id().getYear())){
                uniqueYear.add(activitiesCalorie.get_id().getYear());
            }
        }
        Map<Object, Map<Object, Map<Double, List<ActivityCaloriesPerYear>>>> map =
                activitiesCalories.stream()
                        .collect(Collectors.groupingBy(e -> e.get_id().getYear(), TreeMap::new,
                                Collectors.groupingBy(x -> x.get_id().getActivity(),
                                        Collectors.groupingBy(ActivityCaloriesPerYear::getCalories)))
                        );
        List<String> uniqueYears =
                activitiesCalories.stream().map(ActivityCaloriesPerYear::get_id).map(ActivityYear::getYear)
                        .distinct().collect(Collectors.toList());
        System.out.println("###########################################################");
        System.out.println("V5" + activityCalorieMap.keySet());
        System.out.println("V6" + activityCalorieMap.values());
        System.out.println("###########################################################");
        model.addAttribute("activitiesCalories", activitiesCalories);
        model.addAttribute("activity", activity);
        model.addAttribute("year", year);
        model.addAttribute("calorie", activityCalorieMap.values());
        model.addAttribute("uniqueActivity", activityCalorieMap.keySet());
        model.addAttribute("uniqueYear", map.keySet());
        model.addAttribute("activityCalorieMap", activityCalorieMap);
        return "ActivityCalories"; // "view"/webpage named "ActivityCount.html"
    }

}
