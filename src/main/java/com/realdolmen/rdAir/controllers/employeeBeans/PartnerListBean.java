package com.realdolmen.rdAir.controllers.employeeBeans;

import com.realdolmen.rdAir.domain.Airline;
import com.realdolmen.rdAir.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
@ViewScoped
public class PartnerListBean {
    @Inject
    UserRepository ur;

    private Airline selected;

    private List<Airline> partners;

    private String airlineName, website, email, address, contactFName, contactLName, telephone, password;

    @PostConstruct
    public void init(){
        partners = ur.getAllUsers(Airline.class);
    }

    public List<Airline> getPartners() {
        return partners;
    }

    public void addPartner(){
        System.out.println("Adding partner");
        ur.save(new Airline(contactFName, contactLName, address, telephone, email, airlineName, website, password));
        partners = ur.getAllUsers(Airline.class);
    }

    public Airline getSelected() {
        return selected;
    }

    public void setSelected(Airline selected) {
        this.selected = selected;
    }

    public String getDetailsUrl(Integer id){
        return "/account/admin/partnerdetails.xhtml?faces-redirect=true?id=" + id.toString();
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactFName() {
        return contactFName;
    }

    public void setContactFName(String contactFName) {
        this.contactFName = contactFName;
    }

    public String getContactLName() {
        return contactLName;
    }

    public void setContactLName(String contactLName) {
        this.contactLName = contactLName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
