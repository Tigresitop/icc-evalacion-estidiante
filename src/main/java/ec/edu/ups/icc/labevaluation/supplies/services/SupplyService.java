package ec.edu.ups.icc.labevaluation.supplies.services;
import ec.edu.ups.icc.labevaluation.supplies.dtos.*;
import java.util.List;
public interface SupplyService {

    SupplyResponseDto create(CreateSupplyDto dto);
    List<SupplyResponseDto> getLowStock(Integer maxQuantity);
    SupplyResponseDto updateQuantity(Long id, UpdateSupplyQuantityDto dto);
    void delete(Long id);
}