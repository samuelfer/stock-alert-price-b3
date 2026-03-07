package com.example.stockalert.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrapiResponse {

    private List<BrapiStock> results;

}
