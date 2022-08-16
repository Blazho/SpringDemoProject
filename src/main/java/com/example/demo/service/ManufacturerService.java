package com.example.demo.service;

import com.example.demo.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    public List<Manufacturer> findAll();

    public Optional<Manufacturer> add(String name, String address);

    public Optional<Manufacturer> findById(Long id);

    public void deleteById(Long id);
}
