package utility;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class SpecificationHelper<T> {

    public Predicate createLikeSpecification(List<String> values, String joinName, String fieldName, Root<T> root, CriteriaBuilder cb) {
        return cb.or(values.stream()
                .map(value -> cb.like(root.join(joinName).get(fieldName), "%" + value + "%"))
                .toArray(Predicate[]::new));
    }

}
