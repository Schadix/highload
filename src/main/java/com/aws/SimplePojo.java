/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aws;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schadem
 */
@XmlRootElement
public class SimplePojo {
    private String name;

    public SimplePojo(){}
    
    public SimplePojo(String name){
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}
