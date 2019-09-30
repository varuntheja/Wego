package com.wego.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class HomeFragmentViewModelTest {

    private HomeFragmentViewModel homeViewModel;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations
        homeViewModel = mock(HomeFragmentViewModel.class);
    }

    @Test
    public void insert() {
        // Trigger
        homeViewModel.insert();
        // Validation
        assertEquals(2, homeViewModel.getCategories().getValue().size());
    }

    @Test
    public void update() {
    }
}