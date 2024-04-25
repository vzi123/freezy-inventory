package freezy.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @GetMapping(value= "/test")
public void getTest (){
        System.out.println("Hello");
}


}
