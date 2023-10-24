package com.alaerof.rat.header.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = UIController.class)
public class UIControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void main() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/login")
                        .param("state", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("state", equalTo("10")))
                .andExpect(content().string(containsString("Hello, Rat")));

        MvcResult mvcResult = resultActions.andReturn();
        ModelAndView mv = mvcResult.getModelAndView();

        assertThat(mv.getViewName(), equalTo("login"));
        assertThat(mv.getModelMap().get("state"), equalTo("10"));

    }

//    @Test
//    public void hello() throws Exception {
//        mockMvc.perform(get("/hello").param("name", "I Love Kotlin!"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("welcome"))
//                .andExpect(model().attribute("message", equalTo("I Love Kotlin!")))
//                .andExpect(content().string(containsString("Hello, I Love Kotlin!")));
//    }
}
