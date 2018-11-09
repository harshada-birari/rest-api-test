package com.rest;

import com.model.Response;
import com.model.RestResponse;
import com.model.Result;

import org.springframework.web.client.RestTemplate;

import java.util.Scanner;


public class Program {

    private static String API_URI = "http://services.groupkt.com/state/get/USA/all";

    public static void main(String args[]){

        System.out.println("Starting REST Service App");
        RestTemplate restTemplate = new RestTemplate();

        try{
            Response response = restTemplate.getForObject(API_URI, Response.class);

            System.out.println("Enter the state name or state abbreviation: ");

            String choice = null;

            do
                {
                    Scanner sc = new Scanner(System.in);
                    choice = sc.next();
                    String name = null;
                    String abbr = null;
                    Result result = null;

                    if(choice == null || choice.equals("")){
                        System.out.println("State value is empty : ");
                    }else if(choice.length() == 2){
                        abbr = choice;
                        result = getLocationObjectByStateAbbr(response, abbr);

                        if(result != null)
                            System.out.println("Largest City: " + result.getLargest_city() + " Capital: " + result.getCapital());
                        else
                            System.out.println("Could not get Largest city and Capital for the state you entered.");

                    }else if(choice.length() > 2){
                        name = choice;
                        result = getLocationObjectByStateName(response, name);

                        if(result != null)
                            System.out.println("Largest City: " + result.getLargest_city() + " Capital: " + result.getCapital());
                        else
                            System.out.println("Could not get Largest city and Capital for the state you entered.");
                    }

            }
            while (true);


        }catch (Exception ex){
            System.out.println("Error occured while starting REST Service App");
            ex.printStackTrace();
        }
    }

    public static Result getLocationObjectByStateName(Response response, String name){
        RestResponse restResponse = response.getRestResponse();
        Result result = null;
        for(Result r: restResponse.getResult())
        {
            if(r.getName().equals(name)){
                result = r;
            }
        }
        return result;
    }

    public static Result getLocationObjectByStateAbbr(Response response, String abbr){
        RestResponse restResponse = response.getRestResponse();
        Result result = null;
        for(Result r: restResponse.getResult())
        {
            if(r.getAbbr().equals(abbr)){
                result = r;
            }
        }
        return result;
    }
}
