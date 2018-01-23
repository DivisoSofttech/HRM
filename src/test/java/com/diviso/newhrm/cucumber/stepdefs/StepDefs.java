package com.diviso.newhrm.cucumber.stepdefs;

import com.diviso.newhrm.NewhrmApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = NewhrmApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
