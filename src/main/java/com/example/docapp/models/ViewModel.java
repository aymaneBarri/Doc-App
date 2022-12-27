package com.example.docapp.models;

import com.example.docapp.ViewFactory;

public class ViewModel {
    private static ViewModel viewModel;
    private  final ViewFactory viewFactory;

    private ViewModel(){
        this.viewFactory = new ViewFactory();
    }

    public static synchronized ViewModel getInstance( ){
        if(viewModel == null){
            viewModel = new ViewModel();
        }
        return viewModel;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
