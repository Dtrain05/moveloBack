package com.movelo.moveloapp.gestores;

import java.util.List;

import com.movelo.moveloapp.models.Arbol;
import com.movelo.moveloapp.models.Biciusuario;
import com.movelo.moveloapp.models.db.ArbolBiciusuario;
import com.movelo.moveloapp.services.ArbolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GestorArboles {
    @Autowired
    private ArbolService service;

    public Arbol getArbol(Long id) {
        return null;
    }

    public Arbol getArbol(Double precio) {
        return service.getArbol(precio);
    }

    public List<Arbol> getArbolesPorUsuario(Biciusuario usuario) {
        return service.getArbolesUsuario(usuario);
    }

    public List<Arbol> getArbolesTodos() {
        return service.getTodosArboles();
    }

    public int getCantiArboles(Biciusuario usuario) {
        return service.getArbolesUsuario(usuario).size();
    }

    public boolean anadirArbol(Biciusuario usuario, Double precio) {
        Arbol arbol = getArbol(precio);
        ArbolBiciusuario aGuardar = new ArbolBiciusuario(arbol, usuario);
        ArbolBiciusuario guardado = service.anadirArbol(aGuardar);
        if (guardado != null) {
            return true;
        }
        return false;
    }
}
