/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey_generator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Asif
 */
public class User {
    public String name;
    public Map<String, String> attributes;
    public Map<String, Double> factors;
    
    public User(Map<String, String> attributes, Map<String, Double> factors) {
        this.attributes = new HashMap<>();
        this.factors = new HashMap<>();
        this.attributes.putAll(attributes);
        this.factors.putAll(factors);
    }
}
