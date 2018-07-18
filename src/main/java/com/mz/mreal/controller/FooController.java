package com.mz.mreal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class FooController {
    @GetMapping(path = "/foo")
    public String foo() {
        return "hola";
    }
}
