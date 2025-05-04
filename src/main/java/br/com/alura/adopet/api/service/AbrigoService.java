package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    public List<Abrigo> listar() {
        return repository.findAll();
    }


    public void cadastrarAbrigo(Abrigo abrigo) {
        boolean nomeJaCadastrado = repository.existsByNome(abrigo.getNome());
        boolean telefoneJaCadastrado = repository.existsByTelefone(abrigo.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(abrigo.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            repository.save(abrigo);
        }

    }

    public List<Pet> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.findById(id)
                    .orElseThrow(() -> new ValidacaoException("Abrigo não encontrado!"));
            return abrigo.getPets();
        } catch (NumberFormatException e) {
            Abrigo abrigo = repository.findByNome(idOuNome);
            if (abrigo == null) {
                throw new ValidacaoException("Abrigo não encontrado!");
            }
            return abrigo.getPets();
        }
    }


    public void cadastrarPet(){

    }
}
