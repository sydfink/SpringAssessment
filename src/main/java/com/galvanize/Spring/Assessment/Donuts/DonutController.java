package com.galvanize.Spring.Assessment.Donuts;

import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

@RestController
public class DonutController {
    DonutRepository dr;

    public DonutController(DonutRepository dr) {
        this.dr = dr;
    }

    @PostMapping("/donuts")
    public Donut addOneDonut(@RequestBody Donut donut){
        return this.dr.save(donut);
    }

    @GetMapping("/donuts/{id}")
    public Optional<Donut> getOneDonut(@PathVariable Long id){
        return this.dr.findById(id);
    }

    @GetMapping("/donuts")
    public Iterable<Donut> getAllDonuts(){
        return this.dr.findAll();
    }

    @PatchMapping("/donuts/{id}")
    public Donut updateOneDonut(@PathVariable Long id, @RequestBody Map<String, String> update) {
        Donut rss = this.dr.findById(id).get();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        update.forEach((key, value) -> {
            switch (key) {
                case "name":
                    rss.setName(value);
                    break;
                case "topping":
                    rss.setTopping(value);
                    break;
                case "expiration":
                    try {
                        rss.setExpiration(df.parse(value));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
        return this.dr.save(rss);
    }

    @DeleteMapping("/donuts/{id}")
    public void deleteOneDonut(@PathVariable Long id){
        this.dr.deleteById(id);
    }

}