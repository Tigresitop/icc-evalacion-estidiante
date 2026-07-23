package ec.edu.ups.icc.labevaluation.users.services;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.labevaluation.users.dtos.EligibleUserDto;
import ec.edu.ups.icc.labevaluation.users.repositories.UserRepository;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) { this.repository = repository; }
    @Override 
    public List<EligibleUserDto> findEligible() {
        return repository.findByAgeGreaterThanEqualAndActiveTrueAndDeletedFalseOrderByFullNameAsc(18)
                .stream().map(user -> new EligibleUserDto(user.getId(), user.getFullName(), user.getEmail(), user.getAge(), user.isActive(),
                        user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet())
                )).collect(Collectors.toList());
    }
}