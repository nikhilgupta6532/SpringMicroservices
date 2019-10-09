package com.microservices.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    //first method
    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Nikhil Gupta");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Nikhil","Gupta"));
    }

    //second method
    @GetMapping(value = "/person/param",params = "version=1")
    public PersonV1 paramv1(){
        return new PersonV1("Nikhil Gupta");
    }

    @GetMapping(value = "/person/param",params = "version=2")
    public PersonV2 paramv2(){
        return new PersonV2(new Name("Nikhil","Gupta"));
    }

    //third method
    @GetMapping(value = "/person/header",headers = "X-API-VERSION=1")
    public PersonV1 headerv1(){
        return new PersonV1("Nikhil Gupta");
    }

    @GetMapping(value = "/person/header",headers = "X-API-VERSION=2")
    public PersonV2 headerv2(){
        return new PersonV2(new Name("Nikhil","Gupta"));
    }

    //fourth method
    @GetMapping(value = "/person/produces",produces = "application/vnd.company.app-v1+json") //pass it under accept header
    public PersonV1 producesv1(){
        return new PersonV1("Nikhil Gupta");
    }

    @GetMapping(value = "/person/produces",produces = "application/vnd.company.app-v2+json") //pass it under accept header
    public PersonV2 producesv2(){
        return new PersonV2(new Name("Nikhil","Gupta"));
    }
}
