package com.galvanize.Spring.Assessment;

import com.galvanize.Spring.Assessment.Donuts.Donut;
import com.galvanize.Spring.Assessment.Donuts.DonutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import javax.transaction.Transactional;
import java.util.Calendar;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class DonutControllerTest {
    @Autowired
    DonutRepository repository;
    @Autowired
    MockMvc mvc;

    @Test
    @Transactional
    @Rollback
    public void addNewDonutTest() throws Exception{
        Calendar maple = Calendar.getInstance();
        maple.set(2022, Calendar.APRIL, 8);
        Donut test = new Donut("Maple Bar", "Bacon", maple.getTime());
        Donut record = repository.save(test);

        this.mvc.perform(post("/donuts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Maple Bar\", \"topping\": \"Bacon\", \"expiration\": \"2022-04-08\"}"))
                .andExpect(jsonPath("$.name", is("Maple Bar")))
                .andExpect(jsonPath("$.topping", is("Bacon")))
                .andExpect(jsonPath("$.expiration", is("2022-04-08")))
                .andExpect(jsonPath("$.id").isNumber());

    }

    @Test
    @Transactional
    @Rollback
    public void getSingleDonutTest() throws Exception{
        Calendar maple = Calendar.getInstance();
        maple.set(2022, Calendar.APRIL, 8);
        Donut test = new Donut("Maple Bar", "Bacon", maple.getTime());
        Donut record = repository.save(test);

        this.mvc.perform(get("/donuts/" + record.getId()))
                .andExpect(jsonPath("$.name", is("Maple Bar")))
                .andExpect(jsonPath("$.topping", is("Bacon")))
                .andExpect(jsonPath("$.expiration", is("2022-04-08")))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllDonutsTest() throws Exception{
        Calendar maple = Calendar.getInstance();
        maple.set(2022, Calendar.APRIL, 8);
        Donut test = new Donut("Maple Bar", "Bacon", maple.getTime());
        Donut record = repository.save(test);

        Calendar glaze = Calendar.getInstance();
        glaze.set(2022, Calendar.APRIL, 9);
        Donut test2 = new Donut("Glazed", "Vanilla Glaze", glaze.getTime());
        Donut record2 = repository.save(test2);

        Calendar choccy = Calendar.getInstance();
        choccy.set(2022, Calendar.APRIL, 7);
        Donut test3 = new Donut("Choccy", "Chocolate Glaze", choccy.getTime());
        Donut record3 = repository.save(test3);

        this.mvc.perform(get("/donuts"))
                .andExpect(jsonPath("$[0].name", is("Maple Bar")))
                .andExpect(jsonPath("$[0].topping", is("Bacon")))
                .andExpect(jsonPath("$[0].expiration", is("2022-04-08")))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[1].name", is("Glazed")))
                .andExpect(jsonPath("$[1].topping", is("Vanilla Glaze")))
                .andExpect(jsonPath("$[1].expiration", is("2022-04-09")))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[2].name", is("Choccy")))
                .andExpect(jsonPath("$[2].topping", is("Chocolate Glaze")))
                .andExpect(jsonPath("$[2].expiration", is("2022-04-07")))
                .andExpect(jsonPath("$[2].id").isNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOneDonutTest() throws Exception{
        Calendar maple = Calendar.getInstance();
        maple.set(2022, Calendar.APRIL, 8);
        Donut test = new Donut("Maple Bar", "Bacon", maple.getTime());
        Donut record = repository.save(test);

        this.mvc.perform(patch("/donuts/" + record.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Breakfast\", \"topping\": \"Syrup\", \"expiration\": \"2022-04-08\"}"))
                .andExpect(jsonPath("$.name", is("Breakfast")))
                .andExpect(jsonPath("$.topping", is("Syrup")))
                .andExpect(jsonPath("$.expiration", is("2022-04-08")))
                .andExpect(jsonPath("$.id").isNumber());

    }

    @Test
    @Transactional
    @Rollback
    public void deleteOneDonutTest() throws Exception{
        Calendar maple = Calendar.getInstance();
        maple.set(2022, Calendar.APRIL, 8);
        Donut test = new Donut("Maple Bar", "Bacon", maple.getTime());
        Donut record = repository.save(test);

        this.mvc.perform(delete("/donuts/" + record.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Maple Bar\", \"topping\": \"Bacon\", \"expiration\": \"2022-04-08\"}"))
                .andExpect(jsonPath("$.name").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.topping").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.expiration").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.id").doesNotHaveJsonPath());
    }



}
