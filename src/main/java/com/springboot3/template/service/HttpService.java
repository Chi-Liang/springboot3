package com.springboot3.template.service;

import com.springboot3.template.model.to.Todo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://jsonplaceholder.typicode.com")
public interface HttpService {

    @GetExchange("/todos/{id}")
    Todo getTodoById(@PathVariable("id") Integer id);

}
