package ec.edu.ups.icc.labevaluation.supplies.services;

import ec.edu.ups.icc.labevaluation.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.labevaluation.supplies.dtos.*;
import ec.edu.ups.icc.labevaluation.supplies.entities.SupplyEntity;
import ec.edu.ups.icc.labevaluation.supplies.exceptions.SupplyConflictException;
import ec.edu.ups.icc.labevaluation.supplies.mappers.SupplyMapper;
import ec.edu.ups.icc.labevaluation.supplies.repositories.SupplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository repository;
    private final SupplyMapper mapper;

    public SupplyServiceImpl(SupplyRepository repository, SupplyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override 
    @Transactional
    public SupplyResponseDto create(CreateSupplyDto dto) {
        if (repository.existsByNameIgnoreCaseAndDeletedFalse(dto.name())) {
            throw new SupplyConflictException("Supply name already registered");
        }
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override 
    @Transactional(readOnly = true)
    public List<SupplyResponseDto> getLowStock(Integer maxQuantity) {
        return repository.findByActiveTrueAndDeletedFalseAndQuantityLessThanEqualOrderByQuantityAsc(maxQuantity)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override 
    @Transactional
    public SupplyResponseDto updateQuantity(Long id, UpdateSupplyQuantityDto dto) {
        SupplyEntity entity = repository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new NotFoundException("SUPPLY_NOT_FOUND", "Supply not found"));

        entity.setQuantity(dto.quantity()); 
        return mapper.toDto(repository.save(entity));
    }

    @Override 
    @Transactional
    public void delete(Long id) {
        SupplyEntity entity = repository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new NotFoundException("SUPPLY_NOT_FOUND", "Supply not found"));

        if (entity.getQuantity() > 0) {
            throw new SupplyConflictException("Supply cannot be deleted while quantity is greater than zero");
        }

        entity.setDeleted(true); 
        entity.setActive(false); 
        repository.save(entity);
    }
}