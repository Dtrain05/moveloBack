package com.movelo.moveloapp.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.movelo.moveloapp.models.Biciusuario;
import com.movelo.moveloapp.repositories.BiciusuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiciusuarioServiImpl implements BiciusuarioService {

    @Autowired
    private BiciusuarioRepository repo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        repo.deleteById(email);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Biciusuario> findByEmail(String email) {
        return repo.findById(email);
    }

    @Override
    @Transactional
    public Biciusuario save(Biciusuario rider) {
        int idupNow = getLastId();
        rider.setId(idupNow + 1);
        return repo.save(rider);
    }

    @Override
    @Transactional
    public Boolean checkUser(Biciusuario rider) {
        boolean checked = false;
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("userRegister");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT);

        query.setParameter(1, rider.getCc());
        query.setParameter(2, rider.getNombre());
        query.setParameter(3, rider.getCorreo());
        query.setParameter(4, rider.getDireccion());
        query.setParameter(5, rider.getTelefono());

        query.execute();

        Integer response = (Integer) query.getOutputParameterValue(6);
        if (response == 1)
            checked = true;
        return checked;
    }

    @Transactional
    private Integer getLastId() {

        Integer lastId = 0;
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getNumberUser");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.OUT);
        query.execute();

        Integer response = (Integer) query.getOutputParameterValue(1);
        if (response > 0)
            lastId = response;
        return lastId;
    }

    @Override
    public Double actualizarKm(String correo, Double kmReco) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("actualizarKm");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Double.class, ParameterMode.OUT);

        query.setParameter(1, correo);
        query.setParameter(2, kmReco);

        query.execute();
        Double response = (Double) query.getOutputParameterValue(3);
        return response;
    }

    @Override
    public Double actualizarHuella(String correo, Double huellaNueva) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("actualizarHuella");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Double.class, ParameterMode.OUT);

        query.setParameter(1, correo);
        query.setParameter(2, huellaNueva);

        Double response = (Double) query.getOutputParameterValue(3);
        return response;
    }

    @Override
    public Boolean checkUpdateHuella(Biciusuario rider, double huella) {
        // TODO Auto-generated method stub
        return null;
    }

}
