package com.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.aws.entity.ddb.UserEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

@Path("myresource")
public class MyResource {

    Logger logger = Logger.getLogger(MyResource.class.getName());

    static AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient();
    static String TABLENAME = "Users";

    static TimeZone tz = TimeZone.getTimeZone("UTC");
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s.SZ");
    static void init(){
        df.setTimeZone(tz);
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SimplePojo getIt() {
        logger.debug("getIt");
        return new SimplePojo("test");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postUser(UserEntity userEntity) {
        Map<String, AttributeValue> item = null;
        PutItemResult putItemResult = null;
        try {
            logger.debug("postUser: "+userEntity);
            item = newItem(userEntity.getAccount(), df.format(new Date()), userEntity.getBets());
            PutItemRequest putItemRequest = new PutItemRequest(TABLENAME, item);
            putItemRequest.setReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
            putItemResult = dynamoDB.putItem(putItemRequest);
        } catch (AmazonClientException ace) {
            logger.error("Error putting item in table: " + ace.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (putItemResult == null){
            logger.error("putItemResult==null indicates some problem.");
            throw new WebApplicationException(Response.Status.EXPECTATION_FAILED);
        }
        else return putItemResult.toString();
    }

    private static Map<String, AttributeValue> newItem(String account, String insertDate, String bets) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("account", new AttributeValue(account));
        item.put("insertDate", new AttributeValue(insertDate));
        item.put("bets", new AttributeValue(bets));
        return item;
    }
}
