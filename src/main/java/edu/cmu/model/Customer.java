package edu.cmu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Customer {

    static Set<String> validStateAbbreviations = Set.of(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
            "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
            "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    );

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Email
    @JsonProperty
    private String userId;

    @NotNull
    @JsonProperty
    private String name;

    @NotNull
    @JsonProperty
    private String phone;

    @NotNull
    @JsonProperty
    private String address;

    @JsonProperty
    private String address2;

    @NotNull
    @JsonProperty
    private String city;

    @NotNull
    @JsonProperty
    private String state;

    @NotNull
    @JsonProperty
    private String zipcode;

    @JsonIgnore
    public boolean isStateInvalid() {
        return !validStateAbbreviations.contains(state);
    }
}
