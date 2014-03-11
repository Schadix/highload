package com.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.aws.entity.ddb.UserEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {
    
    static AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient();
    static String TABLENAME="Users";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SimplePojo getIt() {
        return new SimplePojo("test");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity postUser(UserEntity userEntity){
        Map<String, AttributeValue> item = null;
    try {
//        dynamoDB = new AmazonDynamoDBClient();
        item = newItem(userEntity.getAccount(), new Date().toString(), userEntity.getBets());
            PutItemRequest putItemRequest = new PutItemRequest(TABLENAME, item);
            PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
    } catch (AmazonClientException ace) {
        System.out.println("Error describing table: " + ace.getMessage());
    }
        return userEntity;
    }
    
    private static Map<String, AttributeValue> newItem(String account, String insertDate, String bets) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("account", new AttributeValue(account));
        item.put("insertDate", new AttributeValue(insertDate));
        item.put("bets", new AttributeValue(bets));
        return item;
    }   
}
