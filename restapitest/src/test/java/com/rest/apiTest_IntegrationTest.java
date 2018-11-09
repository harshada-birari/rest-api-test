package com.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import com.model.RestResponse;
import com.model.Result;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class apiTest_IntegrationTest {
    private static String API_ENDPOINT = "http://services.groupkt.com/state/get/USA/all";

    public static Response response;

    @Before
    public void setup(){
        response = doGetRequest(API_ENDPOINT);
    }

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }

    @Test
    public void testAPIStatusCode(){
        given().when().get(API_ENDPOINT).then().statusCode(200).and().
                contentType(ContentType.JSON);
    }

    @Test
    public void testAPIContainsCorrectData(){

        String name = "Alabama";

        given().when().get(API_ENDPOINT).then()
        .assertThat().body("RestResponse.result[0].name", equalTo(name));
    }

    @Test
    public void testAPIDataItemCount(){
        List<Result> jsonResponse = response.jsonPath().getList("RestResponse.result");

        assertEquals(55,jsonResponse.size());
    }


}
