package com.melrock.proyecto_web.repository;

import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
// (borra TestEntityManager si no lo usas)
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;   // <-- ESTE es el correcto ✅

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = "com.melrock.proyecto_web.model")         // <— ESCANEA ENTIDADES
@EnableJpaRepositories(basePackages = "com.melrock.proyecto_web.repository") // <— ESCANEA REPOS
@AutoConfigureTestDatabase(replace = Replace.ANY)                   // usa H2 en memoria
class UsuarioRepositoryTest {

    @Autowired UsuarioRepository usuarioRepository;
    @Autowired EmpresaRepository  empresaRepository;

    @Test
    void puedoGuardarYLeerConFindAll() {
        Empresa emp = Empresa.builder()
                .nombre("Acme")
                .nit("123")
                .correoContacto("c@c.com")
                .build();
        emp = empresaRepository.save(emp);

        Usuario u = Usuario.builder()
                .nombre("César")
                .correo("cesar@test.com")
                .contraseña("secreta")
                .rolSistema("ADMIN")
                .empresa(emp)
                .build();

        usuarioRepository.save(u);

        assertThat(usuarioRepository.findAll()).isNotEmpty();
    }
}
