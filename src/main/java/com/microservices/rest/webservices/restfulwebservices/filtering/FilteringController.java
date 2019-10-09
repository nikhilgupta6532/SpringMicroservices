package com.microservices.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

  @GetMapping("/filtering")
  public MappingJacksonValue retreiveSomeBean() {
    SomeBean someBean = new SomeBean("value1", "value2", "value3");

    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
    FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);

    MappingJacksonValue mapping = new MappingJacksonValue(someBean);

    mapping.setFilters(filterProvider);

    return mapping;
  }

  @GetMapping("/filtering-list")
  public List<SomeBean> retreiveListOfSomeBeans() {
    return Arrays.asList(new SomeBean("val1", "val2", "val3"),
        new SomeBean("val12", "val22", "val32"));
  }

}
