package ec.edu.ups.icc.labevaluation.supplies.controllers;

import ec.edu.ups.icc.labevaluation.supplies.dtos.*;
import ec.edu.ups.icc.labevaluation.supplies.services.SupplyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/supplies")
public class SupplyController {
    private final SupplyService service;

    public SupplyController(SupplyService service) { 
        this.service = service; 
    }

    @PostMapping @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity<SupplyResponseDto> create(@Valid @RequestBody CreateSupplyDto dto) { 
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED); 
    }

    @GetMapping("/low-stock") @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SupplyResponseDto>> getLowStock(@RequestParam Integer maxQuantity) { 
        return ResponseEntity.ok(service.getLowStock(maxQuantity)); 
    }

    @PatchMapping("/{id}/quantity") @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity<SupplyResponseDto> updateQuantity(@PathVariable Long id, @Valid @RequestBody UpdateSupplyQuantityDto dto) { 
        return ResponseEntity.ok(service.updateQuantity(id, dto)); 
    }

    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build(); 
    }
    
}