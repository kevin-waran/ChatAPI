package com.example.chatresourceapi;

import com.example.util.FileReaderWriter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Path("/history")
public class HistoryResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/{roomID}")
    @Produces("application/json")
    /**
     * GET HTTP METHOD
     * This method will read the content of roomID.json file and send it back to requester
     * **/
    public Response getRoomHistory(@PathParam("roomID") String roomID) {
        /*
         TODO: read contents from the roomID.json file and return it
         loading the resource directory
        */
        URL url = this.getClass().getClassLoader().getResource("/chatHistory");
        String history = "";
        File mainDir = null;

        //loading the resource directory
        try {
            mainDir = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // loading the file content into history
        try {
            history = FileReaderWriter.readHistoryFile(mainDir, roomID+".json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //build the json data for the response
        JSONObject mapper = new JSONObject();
        mapper.put("room", roomID);
        if(history!=null){
            mapper.put("log", history);
        }else{
            mapper.put("log", "");
        }

        // build the Response object sending json data in the entity
        Response myResp = Response.status(200) // success
                .header("Content-Type", "application/json")
                .entity(mapper.toString()) // adding the json data
                .build();
        return myResp;


    }


}