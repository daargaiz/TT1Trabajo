package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.businesslogic.printer.IEntidadColor;
import com.ejemplo.proyecto.domain.Entidad;

import java.util.List;

public interface IEntidadViricaComportamiento extends IEntidadColor {
    public List<Entidad> moverse(Entidad entidad, int dimensionTablero);
}
