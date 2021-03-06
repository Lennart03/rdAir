package com.realdolmen.rdAir.controllers;

import com.realdolmen.rdAir.domain.*;
import com.realdolmen.rdAir.repositories.*;
import com.realdolmen.rdAir.util.PriceCalculator;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@javax.enterprise.context.SessionScoped
public class SearchFlightBean implements Serializable {

//    @Inject
//    private CriteriaSearchRepository criteriaSearchRepository;
    @Inject
    private SearchRepository searchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private RegionRepository regionRepository;

    private List<Airline> airlines;

    private List<Flight> results;

    private Integer id;

    @NotNull(message="{Search.error.desiredNrOfSeats.null}")
    @Min(value=1,message="{Search.error.desiredNrOfSeats}")
    @Max(value=853, message="{Search.error.desiredNrOfSeats}")
    private Integer desiredNrOfSeats;

    @NotNull(message="{Search.error.flightClass.null}")
    private String flightClass;
    @NotNull(message="{Search.error.airlineCompany.null}")
    private Airline preferredAirline;


    private String locationOption;
    private boolean clickedLocationOptionOne=false;
    private boolean clickedLocationOptionTwo=false;
    @NotNull(message="{Search.error.departureLocation.null}")
    private String departureLocation;
    @NotNull(message="{Search.error.destinationLocation.null}")
    private String destinationLocation;
    // Can be left open @NotNull(message="{Search.error.globalRegion.null}")
    private String globalRegion;

    @NotNull(message="{Search.error.dateOfDeparture.null}")
    @Future(message="{Search.error.dateOfDeparture.future}")
    private Date dateOfDeparture;

    @Future(message="{Search.error.dateOfReturn.future}")
    private Date dateOfReturn;

    private String flightWay;
    private boolean clickedReturn=false;

    @PostConstruct
    public void init() {
        flightWay = "One way";
        locationOption = "Departure location - Destination";
        airlines = userRepository.getAllUsers(Airline.class);
        if (airlines == null) {
            airlines = new ArrayList<>();
        }}

    public Integer getDesiredNrOfSeats() {
        return desiredNrOfSeats;
    }

    public void setDesiredNrOfSeats(Integer desiredNrOfSeats) {
        this.desiredNrOfSeats = desiredNrOfSeats;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public Airline getPreferredAirline() {
        return preferredAirline;
    }

    public void setPreferredAirline(Airline preferredAirline) {
        this.preferredAirline = preferredAirline;
    }

    public String getLocationOption() {
        return locationOption;
    }

    public void setLocationOption(String locationOption) {
        this.locationOption = locationOption;
    }

    public void listener() {
        System.err.println("LOCATION LISTENER CALLED");
        clickedLocationOptionOne = locationOption.equals("Departure Location - Destination");
        clickedLocationOptionTwo = locationOption.equals("World region");

    }

    public void listener1() {
        System.err.println("DATE LISTENER CALLED");
        clickedReturn = flightWay.equals("Return");
    }

    public boolean getClickedReturn() {
        return clickedReturn;
    }

    public boolean getClickedLocationOptionOne() {
        return clickedLocationOptionOne;
    }

    public boolean getClickedLocationOptionTwo() { return  clickedLocationOptionTwo; }


    public void setClickedLocationOptionOne(boolean clickedLocationOptionOne) {
        this.clickedLocationOptionOne = clickedLocationOptionOne;
    }

    public void setClickedLocationOptionTwo(boolean clickedLocationOptionTwo) {
        this.clickedLocationOptionTwo = clickedLocationOptionTwo;
    }

    public void setClickedReturn(boolean clickedReturn) {
        this.clickedReturn = clickedReturn;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getGlobalRegion() {
        return globalRegion;
    }

    public void setGlobalRegion(String globalRegion) {
        this.globalRegion = globalRegion;
    }

    public Date getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(Date dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getFlightWay() {
        return flightWay;
    }

    public void setFlightWay(String flightWay) {
        this.flightWay = flightWay;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public List<Location> getLocations(){
        return locationRepository.getAllLocations();
    }

    public List<Airline> getAirlines(){
        return airlines;
    }

    public List<Region> getRegions(){
        return regionRepository.getAllRegions();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public LocationRepository getLocationRepository() {
        return locationRepository;
    }

    public List<Flight> getResults() {
        return results;
    }

    public String search(){

        if(searchRepository==null){System.err.println("Searchrepo is null");return "";}
        if(flightClass==null){System.err.println("flightclass is null " + flightClass);return "";}
        if(preferredAirline==null){System.err.println("preferedairline is null");return "";}
        if(departureLocation==null){System.err.println("departureloc is null");return "";}
        if(destinationLocation==null){System.err.println("destloc is null");return "";}
        if(globalRegion==null){System.err.println("globalregion is null");return "";}
        if(dateOfDeparture==null){System.err.println("datedepart is null");return "";}
        System.out.println(flightClass);
        System.out.println(desiredNrOfSeats);
        System.out.println(preferredAirline.getAirlineName());
        System.out.println(departureLocation.split(" ")[1]);
        System.out.println(destinationLocation.split(" ")[1]);
        System.out.println(globalRegion);
        System.out.println(dateOfDeparture.toString());

        results = searchRepository.searchForFlights(desiredNrOfSeats,flightClass,preferredAirline.getAirlineName(),
                departureLocation.split(" ")[1].trim(),destinationLocation.split(" ")[1].trim(),globalRegion,null);
        //(int seats, String fClass, String airComp, String dep, String dest, String region, Date departureDate)
        System.err.println("Pressed search button");
        return "/searchresults.xhtml";
    }


    public double calculateFlightPrice(Flight f, String fClass) {
        FlightClass toCalc = null;
        for(FlightClass fc :f.getAvailableClasses()){
            if (fc.getName().equals(fClass)){
                toCalc = fc;
                break;
            }
        }
        if (toCalc != null) {
            return PriceCalculator.calculatePrice(toCalc);
        }
        return 0;

    }

    public double calculateDiscount(Flight f, String fClass) {
        FlightClass toCalc = null;
        for(FlightClass fc :f.getAvailableClasses()){
            if (fc.getName().equals(fClass)){
                toCalc = fc;
                break;
            }
        }
        if (toCalc != null) {
            return PriceCalculator.getDiscountAmount(toCalc);
        }
        return 0;
    }
}
