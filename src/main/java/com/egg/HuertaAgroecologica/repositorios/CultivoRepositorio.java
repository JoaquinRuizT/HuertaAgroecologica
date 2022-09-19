package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivoRepositorio extends JpaRepository<Cultivo, String> {

    @Query("SELECT c FROM Cultivo c WHERE c.nombre = :nombre")
    public Cultivo buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Cultivo c WHERE c.tipoCultivo = :tipoCultivo")
    public List<Cultivo> buscarPorTipoCultivo(@Param("tipoCultivo") String tipoCultivo);

    @Query("SELECT c FROM Cultivo c WHERE c.estacion = :estacion")
    public List<Cultivo> buscarPorTipoEstacion(@Param("estacion") String estacion);

    @Query("SELECT c FROM Cultivo c WHERE c.usuario.id = :id")
    public List<Cultivo> buscarCultivoPorUsuario(@Param("id") String usuarioId);

}
