package ec.edu.ups.icc.labevaluation.supplies.mappers;
import ec.edu.ups.icc.labevaluation.supplies.dtos.*;
import ec.edu.ups.icc.labevaluation.supplies.entities.SupplyEntity;
import org.springframework.stereotype.Component;
@Component
public class SupplyMapper {

    public SupplyEntity toEntity(CreateSupplyDto dto) {
        SupplyEntity entity = new SupplyEntity();
        entity.setName(dto.name()); entity.setDescription(dto.description()); entity.setQuantity(dto.quantity());
        entity.setMinimumStock(dto.minimumStock()); entity.setUnitPrice(dto.unitPrice()); entity.setActive(true);
        return entity;
    }
    
    public SupplyResponseDto toDto(SupplyEntity entity) {
        return new SupplyResponseDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getQuantity(), entity.getMinimumStock(), entity.getUnitPrice(), entity.isActive());
    }
}