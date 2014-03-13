/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aws.entity.ddb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schadem
 */
@DynamoDBTable(tableName = "Users")
@XmlRootElement
public class UserEntity {

    private String account;
    private String bets;

    @DynamoDBHashKey
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    @DynamoDBAttribute
    public String getBets() {
        return bets;
    }

    public void setBets(String bets) {
        this.bets = bets;
    }
    
    @Override
    public String toString(){
        return this.account+ ","+this.bets;
    }
}
