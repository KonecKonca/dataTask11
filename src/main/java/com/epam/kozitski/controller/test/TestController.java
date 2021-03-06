package com.epam.kozitski.controller.test;

import com.epam.kozitski.domain.Crime;
import com.epam.kozitski.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;

@Controller
public class TestController {
    private static final String RESLUT_MESSAGE = "resultMessage";
    private static final String DB_UPDATE_MESSGE = "DB has correctly updated";
    private static final String DB_CLEAR_MESSGE = "DB has correctly cleared";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CrimeService crimeService;

    @GetMapping(value = "/")
    public String test(){

        System.out.println("                    " + jdbcTemplate);
        List<TestEntity> list = jdbcTemplate.query("SELECT * FROM test", new BeanPropertyRowMapper<>(TestEntity.class));
        list.forEach(s -> System.out.println("              " + s));

        System.out.println("                " + new RestTemplate());

        return "index";
    }

    @GetMapping("/crimes")
    public String getCrimes(Model model) throws URISyntaxException {

        List<Crime> allCrimes = crimeService.getAllCrimes("52.268,0.543:52.794,0.238:52.130,0.478", "2017-01");
        model.addAttribute("crimes", allCrimes);

        return "crimes";
    }

    @GetMapping("/addToDbAllCrimes")
    public String addToDbAllCrimes(Model model) throws URISyntaxException {
        List<Crime> allCrimes = crimeService.getAllCrimes("52.268,0.543:52.794,0.238:52.130,0.478", "2017-01");
        crimeService.updateDb(allCrimes);

        model.addAttribute(RESLUT_MESSAGE, DB_UPDATE_MESSGE);

        return "index";
    }

    @GetMapping("/clearDB")
    public String clearDB(Model model) {
        crimeService.clearDb();

        model.addAttribute(RESLUT_MESSAGE, DB_CLEAR_MESSGE);

        return "index";
    }

}
