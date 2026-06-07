package pi.Senai.Senai.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.entity.dto.AmbulanciaRequestDTO;
import pi.Senai.Senai.entity.dto.AmbulanciaResponseDTO;
import pi.Senai.Senai.repository.AmbulanciaRepository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AmbulanciaService {

    private final AmbulanciaRepository repository;

    public AmbulanciaService(AmbulanciaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AmbulanciaResponseDTO create(AmbulanciaRequestDTO dto) {
        if (dto.getPlaca() == null || !Pattern.matches("^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", dto.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de placa inválido.");
        }

        if (repository.existsByPlacaAndAtivoTrue(dto.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa já cadastrada no sistema.");
        }

        Ambulancia ambulancia = new Ambulancia();
        ambulancia.setDescricao(dto.getDescricao());
        ambulancia.setPlaca(dto.getPlaca().toUpperCase());
        ambulancia.setObservacoes(dto.getObservacoes());
        ambulancia.setAtivo(true);

        return AmbulanciaResponseDTO.fromEntity(repository.save(ambulancia));
    }

    @Transactional(readOnly = true)
    public List<AmbulanciaResponseDTO> findAll() {
        return repository.findByAtivoTrue().stream()
                .map(AmbulanciaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public AmbulanciaResponseDTO findById(UUID id) {
        Ambulancia ambulancia = getAmbulanciaById(id);
        return AmbulanciaResponseDTO.fromEntity(ambulancia);
    }

    @Transactional
    public AmbulanciaResponseDTO update(UUID id, AmbulanciaRequestDTO dto) {
        if (dto.getPlaca() == null || !Pattern.matches("^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", dto.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de placa inválido.");
        }

        Ambulancia ambulancia = getAmbulanciaById(id);

        if (repository.existsByPlacaAndAtivoTrueAndIdNot(dto.getPlaca(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa já vinculada a outro veículo.");
        }

        ambulancia.setDescricao(dto.getDescricao());
        ambulancia.setPlaca(dto.getPlaca().toUpperCase());
        ambulancia.setObservacoes(dto.getObservacoes());

        return AmbulanciaResponseDTO.fromEntity(repository.save(ambulancia));
    }

    @Transactional
    public void delete(UUID id) {
        Ambulancia ambulancia = getAmbulanciaById(id);
        ambulancia.setAtivo(false);
        repository.save(ambulancia);
    }

    private Ambulancia getAmbulanciaById(UUID id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambulância não localizada."));
    }
}