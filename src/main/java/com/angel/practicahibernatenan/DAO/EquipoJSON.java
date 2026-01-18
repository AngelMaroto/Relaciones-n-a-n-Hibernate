package com.angel.practicahibernatenan.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.angel.practicahibernatenan.model.Equipo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipoJSON {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private static class EquipoDTO {

        @JsonProperty("nombreEquipo")
        private String nombreEquipo;

        @JsonProperty("localidad")
        private String localidad;

        public String getNombreEquipo() {
            return nombreEquipo;
        }

        public String getLocalidad() {
            return localidad;
        }
    }

    public static List<Equipo> importar(File archivo) throws IOException {
        List<EquipoDTO> dtos = Arrays.asList(JSON_MAPPER.readValue(archivo, EquipoDTO[].class));
        List<Equipo> equipos = new ArrayList<>();

        for (EquipoDTO dto : dtos) {
            equipos.add(new Equipo(dto.getNombreEquipo(), dto.getLocalidad()));
        }
        return equipos;
    }
}