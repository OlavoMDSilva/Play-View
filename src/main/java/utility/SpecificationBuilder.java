package utility;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> add(Specification<T> spec, boolean condition) {
        if (condition) {
            specifications.add(spec);
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));
    }
}
