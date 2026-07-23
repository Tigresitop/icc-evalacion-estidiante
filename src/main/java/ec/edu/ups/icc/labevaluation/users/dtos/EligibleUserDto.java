package ec.edu.ups.icc.labevaluation.users.dtos;
import java.util.Set;
public record EligibleUserDto(Long id, String fullName, String email, Integer age, Boolean active, Set<String> roles) {
    
}